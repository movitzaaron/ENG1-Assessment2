package com.spacecomplexity.longboilife.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.Main;
import com.spacecomplexity.longboilife.MainInputManager;
import com.spacecomplexity.longboilife.game.globals.Constants;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.globals.MainCamera;
import com.spacecomplexity.longboilife.game.globals.MainTimer;
import com.spacecomplexity.longboilife.game.scenarios.DuckDeath;
import com.spacecomplexity.longboilife.game.scenarios.GrantScenario;
import com.spacecomplexity.longboilife.game.ui.UIManager;
import com.spacecomplexity.longboilife.game.utils.*;
import com.spacecomplexity.longboilife.game.world.World;


/**
 * Main class to control the game logic.
 */
public class GameScreen implements Screen {
    private final Main game;

    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final BitmapFont font;
    private UIManager ui;
    private InputManager inputManager;

    private Viewport viewport;

    private World world;

    private final GameState gameState = GameState.getState();
    private final GameLogic gameLogic = new GameLogic();

    public GameScreen(Main game) {
        this.game = game;

        // Initialise SpriteBatch and ShapeRender for rendering
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
    }

    /**
     * Responsible for setting up the game initial state.
     * Called when the game is first run.
     */
    @Override
    public void show() {
        // Set up non-GUI logic
        gameLogic.setupLogic();
        world = gameLogic.getWorld();

        // Create an input multiplexer to handle input from all sources
        InputMultiplexer inputMultiplexer = new InputMultiplexer(new MainInputManager());

        // Initialises camera with CameraManager
        CameraManager camera = new CameraManager(world);
        MainCamera.setMainCamera(camera);

        // Initialise viewport for rescaling
        viewport = new ScreenViewport(MainCamera.camera().getCamera());

        // Calculates the scaling factor based initial screen height
        GameUtils.calculateScaling();

        // Initialise UI elements with UIManager
        ui = new UIManager(inputMultiplexer);

        // Position camera in the center of the world map
        MainCamera.camera().position.set(new Vector3(
            world.getWidth() * Constants.TILE_SIZE * gameState.scaleFactor / 2,
            world.getHeight() * Constants.TILE_SIZE * gameState.scaleFactor / 2,
            0
        ));

        // Set up an InputManager to handle user inputs
        inputManager = new InputManager(inputMultiplexer);
        // Set the Gdx input processor to handle all our input processes
        Gdx.input.setInputProcessor(inputMultiplexer);

        // Set up the final event of ending the game, we cannot do this in logic because
        // we cannot switch screens
        EventHandler eventHandler = EventHandler.getEventHandler();

        // Return to the menu
        eventHandler.createEvent(EventHandler.Event.RETURN_MENU, (params) -> {
            game.switchScreen(Main.ScreenType.MENU);

            return null;
        });
    }

    /**
     * Renders the game world, and make calls to handle continuous inputs.
     * Called every frame.
     */
    @Override
    public void render(float delta) {
        // Call to handles any constant input
        inputManager.handleContinuousInput();

        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1f);

        // Applies viewport transformations and updates camera ready for rendering
        viewport.apply();
        MainCamera.camera().update();
        // Update the SpriteBatch and ShapeRenderer to match the updates camera
        batch.setProjectionMatrix(MainCamera.camera().getCombinedMatrix());
        shapeRenderer.setProjectionMatrix(MainCamera.camera().getCombinedMatrix());

        // Darkened the world when paused
        Color worldTint = gameState.paused ? Color.LIGHT_GRAY : Color.WHITE;

        // Draw the world tiles
        RenderUtils.drawWorld(batch, world, worldTint);
        // Draw the worlds buildings
        RenderUtils.drawBuildings(batch, world, worldTint);

        // If there is a building to be placed draw it as a ghost building
        if (gameState.placingBuilding != null) {
            RenderUtils.drawPlacingBuilding(batch, world, gameState.placingBuilding, new Color(1f, 1f, 1f, 0.75f), new Color(1f, 0f, 0f, 0.75f));
        }
        // If we are placing a building or there is a building selected then draw gridlines
        if (gameState.placingBuilding != null || gameState.selectedBuilding != null) {
            RenderUtils.drawWorldGridlines(shapeRenderer, world, Color.BLACK);
        }
        // If there is a building selected then outline it
        if (gameState.selectedBuilding != null) {
            RenderUtils.outlineBuilding(shapeRenderer, gameState.selectedBuilding, Color.RED, 2);
        }
        // If there is a moving selected then outline where it was previously
        if (gameState.movingBuilding != null) {
            RenderUtils.outlineBuilding(shapeRenderer, gameState.movingBuilding, Color.PURPLE, 2);
        }

        // Render the UI
        ui.render();

        // Poll the Duck Death timer to run the event if the timer has expired
        DuckDeath.poll();
        GrantScenario.poll();

        // Poll the timer to run the event if the timer has expired
        // Do not update satisfaction score if the game is paused or has ended
        if (!gameState.paused && !MainTimer.getTimerManager().getTimer().poll()) {
            // Update the satisfaction score
            GameUtils.updateSatisfactionScore(world);
        }
    }

    /**
     * Handles resizing events, to ensure the game can be scaled.
     * Called when the game window is resized.
     *
     * @param width  the new width in pixels.
     * @param height the new height in pixels.
     */
    @Override
    public void resize(int width, int height) {
        // Updates viewport to match new window size
        viewport.update(width, height, false);

        // Recalculate scaling factors with new height
        GameUtils.calculateScaling();

        // Rescale UI
        ui.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        ui.dispose();
    }

    /**
     * Release all resources held by the game.
     * Called when the game is being closed.
     */
    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        ui.dispose();
    }
}
