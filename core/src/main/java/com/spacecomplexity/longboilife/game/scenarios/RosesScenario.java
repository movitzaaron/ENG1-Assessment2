package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

/**
 * Represents the "Roses Scenario" in the game.
 *
 * <p>This scenario triggers a fixed-timed event that pauses the game and displays a dialog related
 * to the Roses Scenario. The event occurs after a countdown timer completes.
 *
 * <p><strong>Key Features:</strong>
 *
 * <ul>
 *   <li>Manages a countdown timer set for 4 minutes.
 *   <li>Triggers events to pause the game and display a Roses Scenario dialog when the timer
 *       expires.
 *   <li>Includes methods to pause, resume, and poll the timer state.
 * </ul>
 *
 * <p><strong>Usage:</strong>
 *
 * <pre>{@code
 * // Poll the timer state dynamically
 * RosesScenario.poll();
 *
 * // Pause the scenario timers
 * RosesScenario.pauseTimers();
 *
 * // Reset the scenario to its initial state
 * RosesScenario.reset();
 * }</pre>
 */
public class RosesScenario {
  /** Singleton instance of the RosesScenario. */
  public static RosesScenario rosesScenario = new RosesScenario();

  private final Timer timer;

  /**
   * Private constructor to initialize the RosesScenario.
   *
   * <p>Sets up a countdown timer with a duration of 4 minutes and attaches an event to pause the
   * game and display a Roses Scenario dialog when the timer expires.
   */
  private RosesScenario() {
    timer = new Timer();
    /*
     Creates a countdown timer until the fixed event.
     Calls the events that the scenario uses.
    */
    timer.setTimer(4 * 60 * 1000, true);
    timer.setEvent(
        () -> {
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

  /** Pause both timers if they are currently going. */
  public static void pauseTimers() {
    if (!rosesScenario.getTimer().isPaused()) {
      rosesScenario.getTimer().pauseTimer();
    }
  }

  /** Resume both timers if they were previously paused. */
  public static void resumeTimers() {
    if (rosesScenario.getTimer().isPaused()) {
      rosesScenario.getTimer().resumeTimer();
    }
  }

  /**
   * Polls the state of the timers. Checks if the start timer has finished, and if so, also polls
   * the end timer.
   */
  public static void poll() {
    rosesScenario.getTimer().poll();
    if (GameState.getState().paused) {
      RosesScenario.pauseTimers();
    } else {
      RosesScenario.resumeTimers();
    }
  }

  /**
   * Resets the RosesScenario instance.
   *
   * <p>Resets the scenario, including its timer and associated events, allowing the Roses Scenario
   * to be re-triggered.
   */
  public static void reset() {
    rosesScenario = new RosesScenario();
  }
}
