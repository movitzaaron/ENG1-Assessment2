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
public class UIEscapeTip extends UIElement {
  private Label label;

  /**
   * Initialise Tip UI elements
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
