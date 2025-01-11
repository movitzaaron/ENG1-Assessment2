package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

public class DuckScenario {
    public static DuckScenario duckScenario = new DuckScenario();
    public static int cost = 50000;

    private final Timer timer;

    private DuckScenario() {
        timer = new Timer();
        /*
          Creates a countdown timer until the fixed event
          Calls the events that the scenario uses
         */
        timer.setTimer(3 * 60 * 1000, true);
        timer.setEvent(() -> {
            // only trigger the event if the user can afford the outcome
            if (GameState.getState().money > cost){
                EventHandler.getEventHandler().callEvent(EventHandler.Event.PAUSE_GAME);
                EventHandler.getEventHandler().callEvent(EventHandler.Event.DUCK_SCENARIO_DIALOG);
            }
        });
    }

    /**
     * Retrieve the start timer.
     *
     * @return The timer managing the start of the Duck Death event.
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Pause both timers if they are currently going
     */
    public static void pauseTimers(){
        if (!duckScenario.getTimer().isPaused()){
            duckScenario.getTimer().pauseTimer();
        }
    }

    /**
     * Resume both timers if they were previously paused
     */
    public static void resumeTimers() {
        if (duckScenario.getTimer().isPaused()) {
            duckScenario.getTimer().resumeTimer();
        }
    }

    /**
     * Polls the state of the timers. Checks if the start timer has finished,
     * and if so, also polls the end timer.
     */
    public static void poll() {
        duckScenario.getTimer().poll();
        if (GameState.getState().paused){
            DuckScenario.pauseTimers();
        }
        else {
            DuckScenario.resumeTimers();
        }
    }

    public static void reset() {
        duckScenario = new DuckScenario();
    }
}
