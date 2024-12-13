package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

public class GrantScenario {
    public static final GrantScenario grantScenario = new GrantScenario();

    private final Timer timer;

    private GrantScenario() {
        timer = new Timer();

        timer.setTimer(2 * 60 * 1000);
        timer.setEvent(() -> {
            EventHandler.getEventHandler().callEvent(EventHandler.Event.PAUSE_GAME);
            EventHandler.getEventHandler().callEvent(EventHandler.Event.GRANT_SCENARIO_DIALOG);
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
        if (!grantScenario.getTimer().isPaused()){
            grantScenario.getTimer().pauseTimer();
        }
    }

    /**
     * Resume both timers if they were previously
     */
    public static void resumeTimers() {
        if (grantScenario.getTimer().isPaused()) {
            grantScenario.getTimer().resumeTimer();
        }
    }

    /**
     * Polls the state of the timers. Checks if the start timer has finished,
     * and if so, also polls the end timer.
     */
    public static void poll() {
        grantScenario.getTimer().poll();
        if (GameState.getState().paused){
            GrantScenario.pauseTimers();
        }
        else {
            GrantScenario.resumeTimers();
        }
    }
}
