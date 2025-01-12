package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

/**
 * Represents the "Tutorial Scenario" in the game.
 *
 * <p>This scenario introduces the game's tutorial by triggering an event shortly after the game
 * starts. The tutorial pauses the game and displays a tutorial dialog for the player.
 *
 * <p><strong>Key Features:</strong>
 *
 * <ul>
 *   <li>Manages a countdown timer set for 1 second.
 *   <li>Triggers events to pause the game and display a tutorial dialog when the timer expires.
 *   <li>Includes methods to pause, resume, and poll the timer state.
 * </ul>
 *
 * <p><strong>Usage:</strong>
 *
 * <pre>{@code
 * // Poll the timer state dynamically
 * TutorialScenario.poll();
 *
 * // Pause the scenario timers
 * TutorialScenario.pauseTimers();
 *
 * // Reset the scenario to its initial state
 * TutorialScenario.reset();
 * }</pre>
 */
public class TutorialScenario {
  /** Singleton instance of the TutorialScenario. */
  public static TutorialScenario tutorialScenario = new TutorialScenario();

  private final Timer timer;

  /**
   * Private constructor to initialise the TutorialScenario.
   *
   * <p>Sets up a countdown timer with a duration of 1 second and attaches an event to pause the
   * game and display the tutorial dialog when the timer expires.
   */
  private TutorialScenario() {
    timer = new Timer();
    /*
     Creates a countdown timer until the fixed event
     Calls the events that the scenario uses
    */
    timer.setTimer(1000, true);
    timer.setEvent(
        () -> {
          EventHandler.getEventHandler().callEvent(EventHandler.Event.PAUSE_GAME);
          EventHandler.getEventHandler().callEvent(EventHandler.Event.TUTORIAL_DIALOG);
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

  /** Pause both timers if they are currently going. */
  public static void pauseTimers() {
    if (!tutorialScenario.getTimer().isPaused()) {
      tutorialScenario.getTimer().pauseTimer();
    }
  }

  /** Resume both timers if they were previously paused. */
  public static void resumeTimers() {
    if (tutorialScenario.getTimer().isPaused()) {
      tutorialScenario.getTimer().resumeTimer();
    }
  }

  /**
   * Polls the state of the timers. Checks if the start timer has finished, and if so, also polls
   * the end timer.
   */
  public static void poll() {
    tutorialScenario.getTimer().poll();
    if (GameState.getState().paused) {
      TutorialScenario.pauseTimers();
    } else {
      TutorialScenario.resumeTimers();
    }
  }

  /**
   * Resets the TutorialScenario instance.
   *
   * <p>Resets the scenario, including its timer and associated events, allowing the tutorial
   * scenario to be re-triggered.
   */
  public static void reset() {
    tutorialScenario = new TutorialScenario();
  }
}
