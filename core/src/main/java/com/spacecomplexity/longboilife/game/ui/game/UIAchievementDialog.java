package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

public class UIAchievementDialog extends UIElement {
    private final Skin skin;
    private final Stage stage;
    private final String title = "You unlocked an achievement!";

    /**
     * A UI class that creates dialogs to display achievements
     */

    public UIAchievementDialog(Viewport uiViewport, Table parentTable, Skin skin, Stage stage) {
        super(uiViewport, parentTable, skin);
        this.skin = skin;
        this.stage = stage;

        EventHandler eventHandler = EventHandler.getEventHandler();

        eventHandler.createEvent(EventHandler.Event.BROKE_ACHIEVEMENT_DIALOG, (p) -> {
            brokeDialog();
            return null;
        });
        eventHandler.createEvent(EventHandler.Event.SATISFIED_ACHIEVEMENT_DIALOG, (p) -> {
            satisfiedDialog();
            return null;
        });
    }

    @Override
    public void render() {
    }

    @Override
    protected void placeTable() {
    }

    /**
     * Dialog for the 'broke' achievement
     */
    private void brokeDialog() {
        Dialog dialog = new Dialog(title, skin);

        Label heading = new Label("Broke", skin);
        heading.setFontScale(2f);

        Label text = new Label("You have run out of money! Congratulations!", skin);
        text.setWrap(true);

        dialog.getContentTable().add(heading).width(500).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(text).width(500).pad(10);

        dialog.button("Close");

        dialog.show(stage);
    }

    /**
     * Dialog for the 'satisfied' achievement
     */
    private void satisfiedDialog() {
        Dialog dialog = new Dialog(title, skin);

        Label heading = new Label("Satisfied", skin);
        heading.setFontScale(2f);

        Label text = new Label("You hit 100% Satisfaction! Congratulations!", skin);
        text.setWrap(true);

        dialog.getContentTable().add(heading).width(500).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(text).width(500).pad(10);

        dialog.button("Close", 0);

        dialog.show(stage);
    }
}
