package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;

// NEW: This is brand new
/**
 * Represents the "Escape Tip" UI element in the game.
 *
 * <p>This UI element displays a tip message instructing the player to press "ESC" to cancel
 * building placement. It is dynamically shown or hidden based on the player's current action
 * in the game.</p>
 *
 * <p><strong>Key Features:</strong></p>
 * <ul>
 *   <li>Displays a clear message to help players cancel building placement.</li>
 *   <li>Automatically toggles visibility based on the player's current state.</li>
 *   <li>Integrates seamlessly with the game's UI system.</li>
 * </ul>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>{@code
 * UIEscapeTip escapeTip = new UIEscapeTip(uiViewport, parentTable, skin);
 * escapeTip.showTip(); // To show the tip
 * escapeTip.hideTip(); // To hide the tip
 * }</pre>
 *
 * <p>This class extends {@link UIElement}, inheriting the ability to render within the game's UI
 * hierarchy.</p>
 */
public class UIEscapeTip extends UIElement {
  private Label label;

  /**
   * Initialise Tip UI elements.
   *
   * @param uiViewport the viewport used to render UI.
   * @param parentTable the table to render this element onto.
   * @param skin the provided skin.
   */
  public UIEscapeTip(Viewport uiViewport, Table parentTable, Skin skin) {
    super(uiViewport, parentTable, skin);

    // Initialise time label
    label = new Label(null, skin);
    label.setFontScale(1.5f);
    label.setColor(Color.WHITE);
    label.setText("Press ESC to cancel building placement.");

    // Place label onto table
    table.add(label).align(Align.center);

    // Style and place the table
    table.setBackground(skin.getDrawable("panel1"));
    table.setSize(370, 50);
  }

  @Override
  public void render() {
    table.setVisible(GameState.getState().placingBuilding != null);
  }

  @Override
  protected void placeTable() {
    table.setPosition(0, uiViewport.getWorldHeight() - (table.getHeight()) - 380);
  }

  public void showTip() {
    table.setVisible(true);
  }

  public void hideTip() {
    table.setVisible(false);
  }
}
