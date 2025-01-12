package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

public class GrantScenario {
  public static GrantScenario grantScenario = new GrantScenario();

  public static boolean grantTaken = false;

  public static void poll() {
    // Check the condition dynamically
    if (GameState.getState().money <= 200000 && !grantTaken) {
      grantTaken = true; // Ensure the event is triggered only once
      EventHandler.getEventHandler().callEvent(EventHandler.Event.PAUSE_GAME);
      EventHandler.getEventHandler().callEvent(EventHandler.Event.GRANT_SCENARIO_DIALOG);
    }
  }

  public static void reset() {
    grantScenario = new GrantScenario();
    grantTaken = false;
  }
}
