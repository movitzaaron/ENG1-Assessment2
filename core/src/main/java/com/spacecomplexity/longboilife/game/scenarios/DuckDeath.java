package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

/**
 * Singleton class to contain the duck death timer and relevant begin and end functions.
 */
public class DuckDeath {
    public static final DuckDeath duckDeath = new DuckDeath();

    private final Timer startTimer;
    private final Timer endTimer;

    private DuckDeath() {
        startTimer = new Timer();
        endTimer = new Timer();

        EventHandler.getEventHandler().createEvent(EventHandler.Event.DUCK_DEATH_BEGIN, DuckDeath::begin);
        EventHandler.getEventHandler().createEvent(EventHandler.Event.DUCK_DEATH_END, DuckDeath::end);

        // Setup timer, example time
        startTimer.setTimer(3 * 1000);
        startTimer.setEvent(() -> {
            System.out.println("Duck timer over");
            EventHandler.getEventHandler().callEvent(EventHandler.Event.DUCK_DEATH_BEGIN);
        });

        // set the end timer to start and have it go on for 5 seconds
        endTimer.setTimer(5 * 1000, true);
        endTimer.setEvent(() -> {
            System.out.println("Duck timer over");
            EventHandler.getEventHandler().callEvent(EventHandler.Event.DUCK_DEATH_END);
        });
    }

    public static Object begin(Object... params){
        // do something
        System.out.println("Duck Death has occurred");
        duckDeath.endTimer.resumeTimer();

        return null;
    }

    public static Object end(Object... params){
        // do something
        System.out.println("Duck Death has ended");
        return null;
    }

    /**
     * Get the singleton instance of the {@link DuckDeath} class.
     *
     * @return The single {@link DuckDeath} class.
     */

    public static DuckDeath getDuckDeath() {return duckDeath;}

    public Timer getStartTimer() {
        return startTimer;
    }

    public Timer getEndTimer() {
        return endTimer;
    }

    public static void poll() {
        if (duckDeath.getStartTimer().poll()) {
            duckDeath.getEndTimer().poll();
        }
    }
}
