package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

/**
 * Singleton class that manages the Skeleton Scenario scenario in the game.
 * Handles a sequence of timed events: beginning and ending the Skeleton Scenario state.
 * Utilizes the {@link Timer} class to control timing and {@link EventHandler}
 * to trigger relevant game events.
 */
public class SkeletonScenario {
    // Singleton instance of the SkeletonScenario class
    public static final SkeletonScenario skeletonScenario = new SkeletonScenario();

    // Timer to manage the start of the Skeleton Scenario event
    private final Timer startTimer;

    // Timer to manage the end of the Skeleton Scenario event
    private final Timer endTimer;

    private final int startTime = 1; // this is 1 millisecond
    private final int eventDuration = 10 * 1000; // this would be 10 seconds (since its 10,000 milliseconds)

    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes the start and end timers and sets up their events.
     */
    private SkeletonScenario() {
        startTimer = new Timer();
        endTimer = new Timer();

        // Register events for the start and end of the Skeleton Scenario sequence
//        EventHandler.getEventHandler().createEvent(EventHandler.Event.SKELETON_SCENARIO_START, SkeletonScenario::begin);
//        EventHandler.getEventHandler().createEvent(EventHandler.Event.SKELETON_SCENARIO_END, SkeletonScenario::end);

        // Configure the start timer to trigger after 3 seconds
        startTimer.setTimer(startTime);
        startTimer.setEvent(() -> {
            // Trigger the "begin" event for Skeleton Scenario
//            EventHandler.getEventHandler().callEvent(EventHandler.Event.SKELETON_SCENARIO_START);
        });

        // Configure the end timer to trigger after 5 seconds (recurring)
        endTimer.setTimer(eventDuration, true);
        endTimer.setEvent(() -> {
            // Trigger the "end" event for Skeleton Scenario
//            EventHandler.getEventHandler().callEvent(EventHandler.Event.SKELETON_SCENARIO_END);
        });
    }

    /**
     * Event handler for the start of the scenario.
     *
     * @param params Optional parameters (unused in this implementation).
     * @return Always returns null.
     */
    public static Object begin(Object... params) {
        // Resume the end timer to ensure the scenario completes
        skeletonScenario.endTimer.resumeTimer();
        return null;
    }

    /**
     * Event handler for the end of the scenario.
     *
     * @param params Optional parameters (unused in this implementation).
     * @return Always returns null.
     */
    public static Object end(Object... params) {
        return null;
    }

    /**
     * Retrieve the start timer.
     *
     * @return The timer managing the start of the scenario.
     */
    private Timer getStartTimer() {
        return startTimer;
    }

    /**
     * Retrieve the end timer.
     *
     * @return The timer managing the end of the scenario.
     */
    private Timer getEndTimer() {
        return endTimer;
    }

    /**
     * Polls the state of the timers. Checks if the start timer has finished,
     * and if so, also polls the end timer.
     */
    public static void poll() {
        if (skeletonScenario.getStartTimer().poll()) {
            skeletonScenario.getEndTimer().poll();
        }
    }
}
