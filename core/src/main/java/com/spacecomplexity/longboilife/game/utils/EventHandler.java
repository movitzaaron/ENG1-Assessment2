package com.spacecomplexity.longboilife.game.utils;

import java.util.function.Function;

/** Class to manage events which can be called from anywhere within the game. */
public class EventHandler {
  public enum Event {
    BUILD,
    SELECT_BUILDING,
    CANCEL_OPERATIONS,
    SELL_BUILDING,
    MOVE_BUILDING,
    PAUSE_GAME,
    RESUME_GAME,
    OPEN_SELECTED_MENU,
    CLOSE_SELECTED_MENU,
    CLOSE_BUILD_MENU,
    GAME_END,
    RETURN_MENU,
    // >>>> NEW CODE START <<<<
    DUCK_SCENARIO_DIALOG,
    GRANT_SCENARIO_DIALOG,
    ROSES_SCENARIO_DIALOG,
    TUTORIAL_DIALOG,
    TUTORIAL_TEXT_DIALOG,
    BROKE_ACHIEVEMENT_DIALOG,
    SATISFIED_ACHIEVEMENT_DIALOG,
    FITNESS_ACHIEVEMENT_DIALOG,
    ACADEMIC_ACHIEVEMENT_DIALOG,
    FOODIE_ACHIEVEMENT_DIALOG,
    ;
    // >>>> NEW CODE END <<<<

    private Function<Object[], Object> callback = null;

    private void setCallback(Function<Object[], Object> callback) {
      this.callback = callback;
    }

    private Function<Object[], Object> getCallback() {
      // >>>> NEW CODE START <<<<
      // NEW: checks if callback is null
      if (callback == null) {
        return null;
      }
      // >>>> NEW CODE END <<<<
      return callback;
    }
  }

  private static final EventHandler eventHandler = new EventHandler();

  /**
   * Create an event.
   *
   * @param event the enum the event, needed when called.
   * @param callback the event method, this is what will be executed.
   */
  public void createEvent(Event event, Function<Object[], Object> callback) {
    event.setCallback(callback);
  }

  /**
   * Call a previously defined event.
   *
   * @param event the enum of the event defined.
   * @param params the parameter to pass to the event.
   * @return what the original event would return, this will need to be cast as we cannot know the
   *     type here.
   * @throws IllegalArgumentException if the event has not been defined.
   */
  public Object callEvent(Event event, Object... params) throws IllegalArgumentException {
    Function<Object[], Object> callback = event.getCallback();

    // If the callback is not defined then throw an error
    if (callback == null) {
      throw new IllegalArgumentException("No event defined for: \"" + event.name() + "\"");
    }

    // Execute the callback and return the result
    return callback.apply(params);
  }

  /**
   * Get the singleton instance of the {@link EventHandler} class.
   *
   * @return The single {@link EventHandler} class.
   */
  public static EventHandler getEventHandler() {
    return eventHandler;
  }
}
