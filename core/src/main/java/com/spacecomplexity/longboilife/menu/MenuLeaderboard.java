package com.spacecomplexity.longboilife.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.Main;
import com.spacecomplexity.longboilife.MainInputManager;
import com.spacecomplexity.longboilife.game.utils.SaveScore;

/**
 * Main class to control the leaderboard screen.
 */
public class MenuLeaderboard implements Screen{
    private final Main game;
    private Viewport viewport;
    private Texture backgroundTexture;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;

    public MenuLeaderboard(Main game){
        this.game = game;

        // Initialise viewport and drawing elements
        viewport = new FitViewport(640, 480);
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        // Load UI skin for buttons
        skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Initialise back button
        TextButton backButton = new TextButton("Back", skin, "round");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Exit the application
                game.switchScreen(Main.ScreenType.MENU);
            }
        });

        // Initialize the leaderboard label
        String scores = SaveScore.getTopFive();
        BitmapFont font = skin.getFont("font-title"); // use the title font for the leaderboard as it is big on screen
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label boardLabel = new Label(scores, skin);
        boardLabel.setStyle(labelStyle);
        boardLabel.setAlignment(Align.center);
        boardLabel.setFontScale(2f);

        // Add the back button with padding to the top-left corner
        table.add(backButton).pad(10).top().left().width(100).height(50); // Set width and height for button

        // Add an empty row to separate the back button from the label
        table.row().pad(10);

        // Add the board label, centered in the table
        table.add(boardLabel).center().expand();


        // Allows UI to capture touch events
        InputMultiplexer inputMultiplexer = new InputMultiplexer(new MainInputManager(), stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1f);

        // Draw and apply ui
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    // Disallows input of hidden buttons
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
        batch.dispose();
    }
}
