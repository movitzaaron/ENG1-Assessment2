package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.EventHandler.Event;
import com.spacecomplexity.longboilife.game.utils.Timer;

/**
 * Singleton class that manages the Roses Scenario in the game.
 * Handles a sequence of timed events: beginning and ending the Roses Scenario state.
 * Utilizes the {@link Timer} class to control timing and {@link EventHandler}
 * to trigger relevant game events.
 */
public class RosesScenario {
    // Singleton instance of the RosesScenario class, rosesScenario
    public static final RosesScenario rosesScenario = new RosesScenario();

    // Timer to manage the start of the RosesScenario Scenario event
    private final Timer startTimer;

    // Timer to manage the end of the RosesScenario Scenario event
    private final Timer endTimer;

    // Scenario begins after 1 minute
    private final int startTime = 60 * 1000;
    // Scenario lasts 30 seconds after starting
    private final int eventDuration = 30 * 1000;

    private boolean showUI = false;
    private int result;

    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes the start and end timers and sets up their events.
     */
    private RosesScenario() {
        startTimer = new Timer();
        endTimer = new Timer();

        // Register events for the start and end of the Roses Scenario sequence
        EventHandler.getEventHandler().createEvent(Event.ROSES_BEGIN, RosesScenario::begin);
        EventHandler.getEventHandler().createEvent(EventHandler.Event.ROSES_END, RosesScenario::end);

        // Configure the start timer to trigger after 3 seconds
        startTimer.setTimer(startTime);
        startTimer.setEvent(() -> {
            System.out.println("Roses start timer over");
            // Trigger the "begin" event for Roses Scenario
            EventHandler.getEventHandler().callEvent(EventHandler.Event.ROSES_BEGIN);
        });

        // Configure the end timer to trigger after 5 seconds (recurring)
        endTimer.setTimer(eventDuration, true);
        endTimer.setEvent(() -> {
            System.out.println("Roses duration timer over");
            // Trigger the "end" event for Roses Scenario
            EventHandler.getEventHandler().callEvent(EventHandler.Event.ROSES_END);
        });
    }

    /**
     * Event handler for the start of the scenario.
     *
     * @param params Optional parameters (unused in this implementation).
     * @return Always returns null.
     */
    public static Object begin(Object... params) {
        System.out.println("Roses has begun!");
        rosesScenario.showUI = true;

        // Line may interact badly when multiple scenarios happen at the same time?
        EventHandler.getEventHandler().callEvent(EventHandler.Event.CANCEL_OPERATIONS);

        // Resume the end timer to ensure the scenario completes
        rosesScenario.endTimer.resumeTimer();
        return null;
    }

    /**
     * Event handler for the end of the scenario.
     *
     * @param params Optional parameters (unused in this implementation).
     * @return Always returns null.
     */
    public static Object end(Object... params) {
        System.out.println("Roses has ended!");
        rosesScenario.showUI = false;
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
     * Pause both timers if they are currently going
     */
    public static void pauseTimers(){
        rosesScenario.getStartTimer().pauseTimer();
        if (!rosesScenario.getEndTimer().isPaused()) {
            rosesScenario.getEndTimer().pauseTimer();
        }
    }

    /**
     * Resume both timers if they were previously
     */
    public static void resumeTimers(){
        rosesScenario.getStartTimer().resumeTimer();
        if (rosesScenario.getStartTimer().poll()) {
            rosesScenario.getEndTimer().resumeTimer();
        }}

    /**
     * Polls the state of the timers. Checks if the start timer has finished,
     * and if so, also polls the end timer.
     */
    public static void poll() {
        if (rosesScenario.getStartTimer().poll()) {
            rosesScenario.getEndTimer().poll();
        }
        if (GameState.getState().paused){
            rosesScenario.pauseTimers();
        }
        else{
            rosesScenario.resumeTimers();
        }
    }

    public void setShowUI(boolean showUI) {
        this.showUI = showUI;
    }

    public boolean isShowUI() {
        return showUI;
    }

    public void setResult(int result) {
        this.result = result;
        System.out.println(result);
    }
}
