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
        eventHandler.createEvent(EventHandler.Event.FITNESS_ACHIEVEMENT_DIALOG, (p) -> {
            fitnessDialog();
            return null;
        });

        eventHandler.createEvent(EventHandler.Event.ACADEMIC_ACHIEVEMENT_DIALOG, (p) -> {
            academicDialog();
            return null;
        });

        eventHandler.createEvent(EventHandler.Event.FOODIE_ACHIEVEMENT_DIALOG, (p) -> {
            foodieDialog();
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
        heading.setFontScale(1.5f);

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
        heading.setFontScale(1.5f);

        Label text = new Label("You hit 100% Satisfaction! Congratulations!", skin);
        text.setWrap(true);

        dialog.getContentTable().add(heading).width(500).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(text).width(500).pad(10);

        dialog.button("Close", 0);

        dialog.show(stage);
    }

    private void fitnessDialog(){
        Dialog dialog = new Dialog(title, skin);

        Label heading = new Label("Fitness Fanatic", skin);
        heading.setFontScale(1.5f);

        Label text = new Label("You really like gyms, huh? 3 is a lot.", skin);
        text.setWrap(true);

        dialog.getContentTable().add(heading).width(500).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(text).width(500).pad(10);

        dialog.button("Close", 0);

        dialog.show(stage);
    }

    private void academicDialog(){
        Dialog dialog = new Dialog(title, skin);

        Label heading = new Label("Academic weapon", skin);
        heading.setFontScale(1.5f);

        Label text = new Label("3 libraries? It's giving UoY.", skin);
        text.setWrap(true);

        dialog.getContentTable().add(heading).width(500).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(text).width(500).pad(10);

        dialog.button("Close", 0);

        dialog.show(stage);
    }

    private void foodieDialog(){
        Dialog dialog = new Dialog(title, skin);

        Label heading = new Label("Foodie", skin);
        heading.setFontScale(1.5f);

        Label text = new Label("3 restaurants!! The students must be jumping for joy.", skin);
        text.setWrap(true);

        dialog.getContentTable().add(heading).width(500).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(text).width(500).pad(10);

        dialog.button("Close", 0);

        dialog.show(stage);
    }
}
