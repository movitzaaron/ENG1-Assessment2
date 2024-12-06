package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

/**
 * Singleton class that manages the Duck Death scenario in the game.
 * Handles a sequence of timed events: beginning and ending the Duck Death state.
 * Utilizes the {@link Timer} class to control timing and {@link EventHandler}
 * to trigger relevant game events.
 */
public class DuckDeath {
    // Singleton instance of the DuckDeath class
    public static final DuckDeath duckDeath = new DuckDeath();

    // Timer to manage the start of the Duck Death event
    private final Timer startTimer;

    // Timer to manage the end of the Duck Death event
    private final Timer endTimer;

    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes the start and end timers and sets up their events.
     */
    private DuckDeath() {
        startTimer = new Timer();
        endTimer = new Timer();

        // Register events for the start and end of the Duck Death sequence
        EventHandler.getEventHandler().createEvent(EventHandler.Event.DUCK_DEATH_BEGIN, DuckDeath::begin);
        EventHandler.getEventHandler().createEvent(EventHandler.Event.DUCK_DEATH_END, DuckDeath::end);

        // Configure the start timer to trigger after 3 seconds
        startTimer.setTimer(3 * 1000);
        startTimer.setEvent(() -> {
            System.out.println("Duck timer over");
            // Trigger the "begin" event for Duck Death
            EventHandler.getEventHandler().callEvent(EventHandler.Event.DUCK_DEATH_BEGIN);
        });

        // Configure the end timer to trigger after 5 seconds (recurring)
        endTimer.setTimer(5 * 1000, true);
        endTimer.setEvent(() -> {
            System.out.println("Duck timer over");
            // Trigger the "end" event for Duck Death
            EventHandler.getEventHandler().callEvent(EventHandler.Event.DUCK_DEATH_END);
        });
    }

    /**
     * Event handler for the start of the Duck Death scenario.
     *
     * @param params Optional parameters (unused in this implementation).
     * @return Always returns null.
     */
    public static Object begin(Object... params) {
        System.out.println("Duck Death has occurred");
        GameState.getState().duckDeathAlert = true;
        EventHandler.getEventHandler().callEvent(EventHandler.Event.CANCEL_OPERATIONS);
        // Resume the end timer to ensure the scenario completes
        duckDeath.endTimer.resumeTimer();
        return null;
    }

    /**
     * Event handler for the end of the Duck Death scenario.
     *
     * @param params Optional parameters (unused in this implementation).
     * @return Always returns null.
     */
    public static Object end(Object... params) {
        System.out.println("Duck Death has ended");
        GameState.getState().duckDeathAlert = false;
        return null;
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
     * Retrieve the end timer.
     *
     * @return The timer managing the end of the Duck Death event.
     */
    private Timer getEndTimer() {
        return endTimer;
    }

    public static void pauseTimers(){
        duckDeath.getStartTimer().pauseTimer();
        if (!duckDeath.getEndTimer().isPaused()) {
            duckDeath.getEndTimer().pauseTimer();
        }
    }

    public static void resumeTimers(){
        duckDeath.getStartTimer().resumeTimer();
        if (duckDeath.getStartTimer().poll()) {
            duckDeath.getEndTimer().resumeTimer();
        }}

    /**
     * Polls the state of the timers. Checks if the start timer has finished,
     * and if so, also polls the end timer.
     */
    public static void poll() {
        if (duckDeath.getStartTimer().poll()) {
            duckDeath.getEndTimer().poll();
        }
    }
}
