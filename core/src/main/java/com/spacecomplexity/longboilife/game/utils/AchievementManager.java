package com.spacecomplexity.longboilife.game.utils;

import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;

/**
 * Class to manage achievements
 */
public class AchievementManager {

    // Give each achievement a boolean value signifying whether the achievement has been unlocked
    public boolean broke = false;
    public boolean satisfied = false;
    public boolean fitness = false;
    public boolean academic = false;
    public boolean foodie = false;

    /**
     * Check the game state against the conditions of each achievement and call the event if necessary
     */
    public void poll(){
        GameState gameState = GameState.getState();
        EventHandler eventHandler = EventHandler.getEventHandler();

        if (!broke && gameState.money == 0){
            broke = true;
            eventHandler.callEvent(EventHandler.Event.BROKE_ACHIEVEMENT_DIALOG);
        }
        else if (!satisfied && gameState.satisfactionScore == 1){
            satisfied = true;
            eventHandler.callEvent(EventHandler.Event.SATISFIED_ACHIEVEMENT_DIALOG);
        }
        else if (!fitness && gameState.getBuildingCount(BuildingType.GYM) == 3){
            fitness = true;
            eventHandler.callEvent(EventHandler.Event.FITNESS_ACHIEVEMENT_DIALOG);
        }
        else if (!academic && gameState.getBuildingCount(BuildingType.LIBRARY) == 3){
            academic = true;
            eventHandler.callEvent(EventHandler.Event.ACADEMIC_ACHIEVEMENT_DIALOG);
        }
        else if (!foodie && gameState.getBuildingCount(BuildingType.GREGGS) == 3){
            foodie = true;
            eventHandler.callEvent(EventHandler.Event.FOODIE_ACHIEVEMENT_DIALOG);
        }

    }

}
