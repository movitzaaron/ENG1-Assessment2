package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

/**
 * Represents the "Grant Scenario" in the game.
 *
 * <p>This scenario introduces an event triggered when the player's money drops below a specific
 * threshold. When triggered, the game pauses and displays a grant-related dialog. This event occurs
 * only once until the scenario is reset.
 *
 * <p><strong>Key Features:</strong>
 *
 * <ul>
 *   <li>Monitors the player's money to determine if the grant scenario should be triggered.
 *   <li>Pauses the game and triggers a dialog event when conditions are met.
 *   <li>Provides a reset method to reinitialize the scenario and allow re-triggering if needed.
 * </ul>
 *
 * <p><strong>Usage:</strong>
 *
 * <pre>{@code
 * // Poll the scenario state dynamically
 * GrantScenario.poll();
 *
 * // Reset the scenario state for re-triggering
 * GrantScenario.reset();
 * }</pre>
 */
public class GrantScenario {
  /** Singleton instance of the GrantScenario. */
  public static GrantScenario grantScenario = new GrantScenario();

  /** Tracks whether the grant event has already been triggered. */
  public static boolean grantTaken = false;

  /**
   * Polls the state of the scenario.
   *
   * <p>Checks if the player's money is below or equal to 200,000 and whether the grant scenario has
   * already been triggered. If the conditions are met, it pauses the game and triggers the
   * grant-related dialog. The scenario will not trigger again until it is reset.
   */
  public static void poll() {
    // Check the condition dynamically
    if (GameState.getState().money <= 200000 && !grantTaken) {
      grantTaken = true; // Ensure the event is triggered only once
      EventHandler.getEventHandler().callEvent(EventHandler.Event.PAUSE_GAME);
      EventHandler.getEventHandler().callEvent(EventHandler.Event.GRANT_SCENARIO_DIALOG);
    }
  }

  /**
   * Resets the GrantScenario instance and its state.
   *
   * <p>Reset the scenario, allowing the grant event to be triggered again if the conditions
   * are met.
   */
  public static void reset() {
    grantScenario = new GrantScenario();
    grantTaken = false;
  }
}
