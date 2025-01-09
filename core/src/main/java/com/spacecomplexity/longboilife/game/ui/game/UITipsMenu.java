package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;


/**
 * Class to represent the Money UI.
 */
public class UITipsMenu extends UIElement {
    private Label label;

    /**
     * Initialise money menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UITipsMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Initialise title label
        Label title = new Label("Tips", skin);
        title.setFontScale(1.5f);
        title.setColor(Color.WHITE);
        title.setAlignment(Align.left);

        // Initialise tips label
        label = new Label(null, skin);
        label.setFontScale(1f);
        label.setColor(Color.WHITE);
        label.setAlignment(Align.left);

        // Place labels onto table
        table.add(title).left().expandX().padLeft(15).padBottom(5);
        table.row();
        table.add(label).left().expandX().padLeft(20);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(340, 80);
        placeTable();
    }

    public void render() {
        GameState gameState = GameState.getState();
        StringBuilder tipsText = new StringBuilder();
        int lineCount = 0;
        if (gameState.satDistanceModifier < 0.5){
            tipsText.append("- Place buildings closer together\n");
            lineCount++;
        }
        if (gameState.satBuildingCountModifier < 0.5){
            tipsText.append("- Place more buildings\n");
            lineCount++;
        }
        if (gameState.satBuildingProportionModifier < 0.8){
            tipsText.append("- Even out the number of each type of building\n");
            lineCount++;
        }
        if (gameState.satEmptyCategoryModifier < 1){
            tipsText.append("- Make sure you have a building of every category\n");
            lineCount++;
        }

        table.setVisible(!tipsText.isEmpty());

        label.setText(tipsText.toString().strip());

        table.setHeight(35 + lineCount * 20);
        table.setPosition((uiViewport.getWorldWidth() - table.getWidth())/2 - 50, uiViewport.getWorldHeight() - table.getHeight());
    }

    @Override
    protected void placeTable() {
        table.setPosition((uiViewport.getWorldWidth() - table.getWidth())/2 - 50, uiViewport.getWorldHeight() - table.getHeight());
    }
}
