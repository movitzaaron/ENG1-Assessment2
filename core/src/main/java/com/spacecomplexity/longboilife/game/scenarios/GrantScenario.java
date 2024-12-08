package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

public class GrantScenario {
    public static final GrantScenario grant = new GrantScenario();

    private final Timer startTimer;

    private GrantScenario() {
        startTimer = new Timer();

        startTimer.setTimer(1000 * 2);
        startTimer.setEvent(() -> {
            EventHandler.getEventHandler().callEvent(EventHandler.Event.PAUSE_GAME);
            EventHandler.getEventHandler().callEvent(EventHandler.Event.GRANT_SCENARIO_DIALOG);
        });
    }

    /**
     * Retrieve the start timer.
     *
     * @return The timer managing the start of the Duck Death event.
     */
    private Timer getStartTimer() {
        return startTimer;
    }

    /**
     * Pause both timers if they are currently going
     */
    public static void pauseTimers(){
        if (!grant.getStartTimer().isPaused()){
            grant.getStartTimer().pauseTimer();
        }
    }

    /**
     * Resume both timers if they were previously
     */
    public static void resumeTimers() {
        if (grant.getStartTimer().isPaused()) {
            grant.getStartTimer().resumeTimer();
        }
    }

    /**
     * Polls the state of the timers. Checks if the start timer has finished,
     * and if so, also polls the end timer.
     */
    public static void poll() {
        grant.getStartTimer().poll();
        if (GameState.getState().paused){
            GrantScenario.pauseTimers();
        }
        else {
            GrantScenario.resumeTimers();
        }
    }
}
