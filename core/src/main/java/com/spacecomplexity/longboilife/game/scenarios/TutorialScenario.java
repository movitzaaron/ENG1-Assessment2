package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;

public class TutorialScenario {
  public static TutorialScenario tutorialScenario = new TutorialScenario();

  private final Timer timer;

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

  /** Pause both timers if they are currently going */
  public static void pauseTimers() {
    if (!tutorialScenario.getTimer().isPaused()) {
      tutorialScenario.getTimer().pauseTimer();
    }
  }

  /** Resume both timers if they were previously paused */
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

  public static void reset() {
    tutorialScenario = new TutorialScenario();
  }
}
