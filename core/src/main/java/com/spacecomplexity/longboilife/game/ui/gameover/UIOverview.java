package com.spacecomplexity.longboilife.game.ui.gameover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.SaveScore;

/**
 * Class to represent the Overview UI after the game is completed.
 */
public class UIOverview extends UIElement {
    /**
     * Initialise overview elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIOverview(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        GameState.getState().isTyping = true;

        String overview = String.format("Game Over!\r\nSatisfaction Score: %.2f%%\r\nEnter your name below to save your score:", GameState.getState().satisfactionScore * 100);

        // Initialise label
        Label label = new Label(overview, skin);
        label.setAlignment(Align.center);
        label.setFontScale(1.2f);
        label.setColor(Color.WHITE);

        TextField nameField = new TextField(overview, skin);
        nameField.setMaxLength(14);
        nameField.setText("");


        // Initialise exitButton
        TextButton exitButton = new TextButton("Exit without saving", skin);
        exitButton.setColor(Color.RED);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState.getState().isTyping = false;
                // Call the events to return to the menu
                EventHandler.getEventHandler().callEvent(EventHandler.Event.RETURN_MENU);
            }
        });

        // Initialise saveButton
        TextButton saveButton = new TextButton("Save and exit", skin);
        saveButton.setColor(Color.GREEN);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState.getState().isTyping = false;
                // Save the user score
                SaveScore.updateScores(nameField.getText(), Math.round(GameState.getState().satisfactionScore * 1000));
                // Call the events to return to the menu
                EventHandler.getEventHandler().callEvent(EventHandler.Event.RETURN_MENU);
            }
        });


        // Place label onto table
        table.add(label).align(Align.center).pad(5);
        table.row();
        table.add(nameField).align(Align.center).pad(5);
        table.row();
        table.add(saveButton).align(Align.center).pad(5);
        table.row().pad(50);
        table.add(exitButton).align(Align.center).pad(5);


        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(320, 200);
        placeTable();
    }

    public void render() {
    }

    @Override
    protected void placeTable() {
        table.setPosition((uiViewport.getWorldWidth() - table.getWidth()) / 2, uiViewport.getWorldHeight() - table.getHeight());
    }
}
