package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

public class RosesScenario {
    public static RosesScenario rosesScenario = new RosesScenario();

    private final Timer timer;

    private RosesScenario() {
        timer = new Timer();
        /*
          Creates a countdown timer until the fixed event
          Calls the events that the scenario uses
         */
        timer.setTimer(4 * 60 * 1000);
        timer.setEvent(() -> {
            EventHandler.getEventHandler().callEvent(EventHandler.Event.PAUSE_GAME);
            EventHandler.getEventHandler().callEvent(EventHandler.Event.ROSES_SCENARIO_DIALOG);
        });
    }

    /**
     * Retrieve the start timer.
     *
     * @return The timer managing the start of the Duck Death event.
     */
    private Timer getTimer() {
        return timer;
    }

    /**
     * Pause both timers if they are currently going
     */
    public static void pauseTimers(){
        if (!rosesScenario.getTimer().isPaused()){
            rosesScenario.getTimer().pauseTimer();
        }
    }

    /**
     * Resume both timers if they were previously paused
     */
    public static void resumeTimers() {
        if (rosesScenario.getTimer().isPaused()) {
            rosesScenario.getTimer().resumeTimer();
        }
    }

    /**
     * Polls the state of the timers. Checks if the start timer has finished,
     * and if so, also polls the end timer.
     */
    public static void poll() {
        rosesScenario.getTimer().poll();
        if (GameState.getState().paused){
            RosesScenario.pauseTimers();
        }
        else {
            RosesScenario.resumeTimers();
        }
    }

    public static void reset() {
        rosesScenario = new RosesScenario();
    }
}
