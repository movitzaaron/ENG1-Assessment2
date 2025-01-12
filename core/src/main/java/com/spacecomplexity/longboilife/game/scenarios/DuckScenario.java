package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

/**
 * Represents the "Duck Scenario" in the game.
 *
 * <p>This class defines the logic and behavior for the Duck Scenario, including the initialisation
 * of a countdown timer, handling associated events, and managing game state interactions. The
 * scenario triggers a dialog and pauses the game if the player can afford the scenario's outcome.
 *
 * <p><strong>Key Features:</strong>
 *
 * <ul>
 *   <li>Manages a countdown timer that triggers the scenario-specific events.
 *   <li>Handles game pauses and dialogs related to the Duck Scenario.
 *   <li>Provides utility methods to pause, resume, and poll the timer states.
 * </ul>
 *
 * <p>This class uses the singleton pattern to ensure there is only one instance of the DuckScenario
 * throughout the game's lifecycle.
 *
 * <p><strong>Usage:</strong>
 *
 * <pre>{@code
 * // Poll the state of the scenario
 * DuckScenario.poll();
 *
 * // Pause the scenario timers
 * DuckScenario.pauseTimers();
 *
 * // Reset the scenario to its initial state
 * DuckScenario.reset();
 * }</pre>
 */
public class DuckScenario {
  /** Singleton instance of the DuckScenario. */
  public static DuckScenario duckScenario = new DuckScenario();

  /** The cost associated with triggering the Duck Scenario event. */
  public static int cost = 50000;

  private final Timer timer;

  /**
   * Private constructor to initialise the DuckScenario.
   *
   * <p>Sets up a countdown timer with a duration of 3 minutes and attaches an event that checks if
   * the player can afford the scenario cost. If the condition is met, the game is paused, and a
   * scenario dialog is triggered.
   */
  private DuckScenario() {
    timer = new Timer();
    /*
     Creates a countdown timer until the fixed event
     Calls the events that the scenario uses
    */
    timer.setTimer(3 * 60 * 1000, true);
    timer.setEvent(
        () -> {
          // only trigger the event if the user can afford the outcome
          if (GameState.getState().money > cost) {
            EventHandler.getEventHandler().callEvent(EventHandler.Event.PAUSE_GAME);
            EventHandler.getEventHandler().callEvent(EventHandler.Event.DUCK_SCENARIO_DIALOG);
          }
        });
  }

  /**
   * Retrieve the start timer.
   *
   * @return The {@link Timer} managing the start of the Duck Death event.
   */
  public Timer getTimer() {
    return timer;
  }

  /** Pause both timers if they are currently going. */
  public static void pauseTimers() {
    if (!duckScenario.getTimer().isPaused()) {
      duckScenario.getTimer().pauseTimer();
    }
  }

  /** Resume both timers if they were previously paused. */
  public static void resumeTimers() {
    if (duckScenario.getTimer().isPaused()) {
      duckScenario.getTimer().resumeTimer();
    }
  }

  /**
   * Polls the state of the timers. Checks if the start timer has finished, and if so, also polls
   * the end timer.
   */
  public static void poll() {
    duckScenario.getTimer().poll();
    if (GameState.getState().paused) {
      DuckScenario.pauseTimers();
    } else {
      DuckScenario.resumeTimers();
    }
  }

  /**
   * Resets the DuckScenario instance.
   *
   * <p>This method recreates the singleton instance and resets the timer and associated events to
   * their initial state.
   */
  public static void reset() {
    duckScenario = new DuckScenario();
  }
}
