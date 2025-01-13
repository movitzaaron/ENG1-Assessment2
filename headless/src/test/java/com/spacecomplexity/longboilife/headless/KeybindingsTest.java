package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.Input;
import com.spacecomplexity.longboilife.game.globals.Keybindings;
import org.junit.jupiter.api.Test;

/** Unit tests for the Vector2Int class. TEST REF: 16 */
class KeybindingsTest {

  @Test
  void testKeybindingsValues() {
    assertEquals(Input.Keys.W, Keybindings.CAMERA_UP.getKey(), "CAMERA_UP should map to W key");
    assertEquals(Input.Keys.A, Keybindings.CAMERA_LEFT.getKey(), "CAMERA_LEFT should map to A key");
    assertEquals(Input.Keys.S, Keybindings.CAMERA_DOWN.getKey(), "CAMERA_DOWN should map to S key");
    assertEquals(
        Input.Keys.D, Keybindings.CAMERA_RIGHT.getKey(), "CAMERA_RIGHT should map to D key");
    assertEquals(
        Input.Keys.Q, Keybindings.CAMERA_ZOOM_IN.getKey(), "CAMERA_ZOOM_IN should map to Q key");
    assertEquals(
        Input.Keys.E, Keybindings.CAMERA_ZOOM_OUT.getKey(), "CAMERA_ZOOM_OUT should map to E key");
    assertEquals(
        Input.Keys.F11, Keybindings.FULLSCREEN.getKey(), "FULLSCREEN should map to F11 key");
    assertEquals(Input.Keys.ESCAPE, Keybindings.CANCEL.getKey(), "CANCEL should map to ESCAPE key");
    assertEquals(Input.Keys.SPACE, Keybindings.PAUSE.getKey(), "PAUSE should map to SPACE key");
    assertEquals(
        Input.Keys.T, Keybindings.TUTORIAL_MENU.getKey(), "TUTORIAL_MENU should map to T key");
  }
}
