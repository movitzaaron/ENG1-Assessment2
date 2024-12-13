package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Game;
import com.spacecomplexity.longboilife.game.globals.GameState;

public class AchievementManager {

    // Give each achievement a boolean value signifying whether the achievement has been unlocked
    public boolean broke = false;
    public boolean satisfied = false;

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
    }

}
