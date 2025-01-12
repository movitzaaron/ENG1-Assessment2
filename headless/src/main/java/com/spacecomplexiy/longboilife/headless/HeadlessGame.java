package com.spacecomplexiy.longboilife.headless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.spacecomplexity.longboilife.MainInputManager;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.globals.MainTimer;
import com.spacecomplexity.longboilife.game.utils.GameLogic;
import com.spacecomplexity.longboilife.game.utils.GameUtils;
import com.spacecomplexity.longboilife.game.utils.InputManager;
import com.spacecomplexity.longboilife.game.world.World;

public class HeadlessGame extends ApplicationAdapter {
    private InputManager inputManager;
    private World world;
    private final GameState gameState = GameState.getState();
    private final GameLogic gameLogic = new GameLogic();

    public HeadlessGame() {

    }

    @Override
    public void create() {
        gameLogic.setupLogic();
        world = gameLogic.getWorld();

        // Create an input multiplexer to handle input from all sources
        InputMultiplexer inputMultiplexer = new InputMultiplexer(new MainInputManager());

        // Set up an InputManager to handle user inputs
        inputManager = new InputManager(inputMultiplexer);
        // Set the Gdx input processor to handle all our input processes
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render() {
        inputManager.handleContinuousInput();

        // Poll the timer to run the event if the timer has expired
        // Do not update satisfaction score if the game is paused or has ended
        if (!gameState.paused && !MainTimer.getTimerManager().getTimer().poll()) {
          // Update the satisfaction score
          GameUtils.updateSatisfactionScore(world);
          GameUtils.updateMoney();
        }
    }
}
