package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.Constants;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.globals.MainTimer;
import com.spacecomplexity.longboilife.game.tile.InvalidSaveMapException;
import com.spacecomplexity.longboilife.game.tile.Tile;
import com.spacecomplexity.longboilife.game.world.World;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class GameLogic {
    private final GameState gameState = GameState.getState();
    private World world;
    // This will take everything that GameScreen does logically and does it in here instead.
    // This is to make sure that we can test the rest of the code easier by being able to create a headless application.

    public void setupLogic() {
        gameState.reset();

        // Creates a new World object from "map.json" file
        try {
            world = new World(Gdx.files.internal("map.json"));
        } catch (FileNotFoundException | InvalidSaveMapException e) {
            throw new RuntimeException(e);
        }

        // Create a new timer for 5 minutes
        MainTimer.getTimerManager().getTimer().setTimer(5 * 60 * 1000);
        MainTimer.getTimerManager().getTimer().setEvent(() -> {
            EventHandler.getEventHandler().callEvent(EventHandler.Event.GAME_END);
        });

        // Initialise the events performed from this script.
        initialiseEvents();

    }


    /**
     * Initialise events for the event handler.
     */
    private void initialiseEvents() {
        EventHandler eventHandler = EventHandler.getEventHandler();

        // Build the selected building
        eventHandler.createEvent(EventHandler.Event.BUILD, (params) -> {
            BuildingType toBuild = gameState.placingBuilding;

            // If there is no selected building do nothing
            if (toBuild == null) {
                return null;
            }

            // If the building is in an invalid location then don't built
            Vector2Int mouse = GameUtils.getMouseOnGrid(world);
            if (!world.canBuild(toBuild, mouse)) {
                return null;
            }

            // If there is no moving building then this is a new build
            if (gameState.movingBuilding == null) {
                // If the user doesn't have enough money to buy the building then don't build
                float cost = toBuild.getCost();
                if (gameState.money < cost) {
                    return null;
                }

                // Build the building at the mouse location and charge the player accordingly
                world.build(toBuild, mouse);
                gameState.money -= cost;

                // Remove the selected building if it is wanted to do so
                if (Arrays.stream(Constants.dontRemoveSelection).noneMatch(category -> gameState.placingBuilding.getCategory() == category)) {
                    gameState.placingBuilding = null;
                }
            }
            // If there is a moving building then this is a moved building.
            else {
                // If the user doesn't have enough money to buy the building then don't build
                float cost = toBuild.getCost() * Constants.moveCostRecovery;
                if (gameState.money < cost) {
                    return null;
                }

                // Build the building at the mouse location and charge the player accordingly
                world.build(gameState.movingBuilding, mouse);
                gameState.money -= cost;

                // Remove the old moving building and selected building
                gameState.movingBuilding = null;
                gameState.placingBuilding = null;
            }

            return null;
        });

        // Select a previously built building
        eventHandler.createEvent(EventHandler.Event.SELECT_BUILDING, (params) -> {
            // Get the tile at the mouse coordinates
            Tile tile = world.getTile(GameUtils.getMouseOnGrid(world));
            // Get the building on the tile
            Building selectedBuilding = tile.getBuildingRef();

            // If there is no building here then do nothing
            if (selectedBuilding == null) {
                return null;
            }

            // Set the selected building
            gameState.selectedBuilding = selectedBuilding;

            // Open the selected building menu
            eventHandler.callEvent(EventHandler.Event.OPEN_SELECTED_MENU);

            return null;
        });

        // Cancel all events
        eventHandler.createEvent(EventHandler.Event.CANCEL_OPERATIONS, (params) -> {
            // Close menus and deselect any buildings
            eventHandler.callEvent(EventHandler.Event.CLOSE_BUILD_MENU);
            gameState.placingBuilding = null;
            eventHandler.callEvent(EventHandler.Event.CLOSE_SELECTED_MENU);
            gameState.selectedBuilding = null;

            // If there is a building move in progress cancel this
            if (gameState.movingBuilding != null) {
                world.build(gameState.movingBuilding);
                gameState.movingBuilding = null;
            }

            return null;
        });

        // Sell the selected building
        eventHandler.createEvent(EventHandler.Event.SELL_BUILDING, (params) -> {
            // Delete the building
            world.demolish(gameState.selectedBuilding);
            // Refund the specified amount
            gameState.money += gameState.selectedBuilding.getType().getCost() * Constants.sellCostRecovery;
            // Deselect the removed building
            gameState.selectedBuilding = null;

            return null;
        });

        // Start the move of the selected building
        eventHandler.createEvent(EventHandler.Event.MOVE_BUILDING, (params) -> {
            float cost = gameState.selectedBuilding.getType().getCost() * Constants.moveCostRecovery;
            // If we don't have enough money then don't allow the move
            if (gameState.money < cost) {
                return null;
            }

            // Delete the original building
            world.demolish(gameState.selectedBuilding);
            // Select the same type of building to be placed again
            gameState.placingBuilding = gameState.selectedBuilding.getType();
            // Deselect the removed building and set it to the building to be moved
            gameState.movingBuilding = gameState.selectedBuilding;
            gameState.selectedBuilding = null;

            // Close the menu
            eventHandler.callEvent(EventHandler.Event.CLOSE_SELECTED_MENU);

            return null;
        });
    }

    public World getWorld() {
        return world;
    }
}
