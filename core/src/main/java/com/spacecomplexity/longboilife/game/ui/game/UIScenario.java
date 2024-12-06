package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;

/**
 * Class to represent the UI building counter.
 */
public class UIScenario extends UIElement {
    /**
     * Initialise clock menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIScenario(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        Label title = new Label("duck is dead", skin);
        title.setFontScale(3f);
        title.setColor(Color.WHITE);

        Label desc = new Label("do something", skin);
        desc.setFontScale(1f);
        desc.setColor(Color.WHITE);

        TextButton button = new TextButton("x", skin);
        button.setColor(Color.RED);
        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameState.getState().duckDeathAlert = false;
            }
        });

        table.add(button).width(25).height(20).top().right();
        table.row();
        table.add(title).padRight(40).padLeft(30);
        table.row();
        table.add(desc).pad(15);


        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(310, 150);
        placeTable();
    }

    public void render() {
        table.setVisible(GameState.getState().duckDeathAlert);
    }

    @Override
    protected void placeTable() {
        table.setPosition(uiViewport.getWorldWidth()/2 - table.getWidth()/2, uiViewport.getWorldHeight()/2 - table.getHeight()/2);
    }
}
