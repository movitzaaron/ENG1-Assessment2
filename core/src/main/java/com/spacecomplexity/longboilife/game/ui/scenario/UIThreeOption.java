package com.spacecomplexity.longboilife.game.ui.scenario;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.scenarios.DuckDeath;
import com.spacecomplexity.longboilife.game.ui.UIElement;

public class UIThreeOption extends UIElement {
    /**
     * Initialise base UI elements
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIThreeOption(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Create Title Label
        Label title = new Label("Duck died", skin);
        title.setFontScale(3f);
        title.setColor(Color.WHITE);

        // Create Description Label
        Label desc = new Label("Choose an option between 'x', '-' and ':)'", skin);
        desc.setFontScale(1f);
        desc.setColor(Color.CORAL);

        // Create Buttons
        TextButton xButton = new TextButton("x", skin);
        xButton.setColor(Color.RED);
        xButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DuckDeath.getDuckDeath().setShowUI(false);
                DuckDeath.getDuckDeath().setResult(-1);
            }
        });

        TextButton okButton = new TextButton("-", skin);
        okButton.setColor(Color.GRAY);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DuckDeath.getDuckDeath().setShowUI(false);
                DuckDeath.getDuckDeath().setResult(0);
            }
        });

        TextButton goodButton = new TextButton("✓", skin);
        goodButton.setColor(Color.GREEN);
        goodButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DuckDeath.getDuckDeath().setShowUI(false);
                DuckDeath.getDuckDeath().setResult(1);
            }
        });

        // Define Button Size and Padding
        int buttonSize = 50; // Increased size for better visibility
        float buttonPadding = 10f;

        // Add Title to Table with colspan
        table.add(title).padTop(20).padLeft(10).padRight(10).colspan(3).center();
        table.row();

        // Add Description to Table with colspan
        table.add(desc).padBottom(20).padLeft(10).padRight(10).colspan(3).center();
        table.row();

        // Create a nested table for buttons to manage their alignment and spacing
        Table buttonTable = new Table();
        buttonTable.defaults().pad(buttonPadding); // Set default padding for all buttons

        // Add Buttons to the Button Table
        buttonTable.add(xButton).width(buttonSize).height(buttonSize);
        buttonTable.add(okButton).width(buttonSize).height(buttonSize);
        buttonTable.add(goodButton).width(buttonSize).height(buttonSize);

        // Add Button Table to the Main Table with colspan and center alignment
        table.add(buttonTable).colspan(3).center();

        // Set Background and Pack the Table
        table.setBackground(skin.getDrawable("panel1"));
        table.pack(); // Automatically size the table based on its contents
        placeTable();
    }

    @Override
    public void render() {
        table.setVisible(DuckDeath.getDuckDeath().isShowUI());
    }

    @Override
    protected void placeTable() {
        // Center the table in the viewport
        table.setPosition(
            uiViewport.getWorldWidth() / 2 - table.getWidth() / 2,
            uiViewport.getWorldHeight() / 2 - table.getHeight() / 2
        );
    }
}
