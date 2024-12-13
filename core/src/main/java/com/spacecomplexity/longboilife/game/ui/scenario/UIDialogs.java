package com.spacecomplexity.longboilife.game.ui.scenario;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

/**
 * A UI test class that creates and shows a modal dialog.
 * The dialog itself is modal, meaning it will block input to underlying UI elements.
 */
public class UIDialogs extends UIElement {
    private final Skin skin;
    private final Stage stage;

    public UIDialogs(Viewport uiViewport, Table parentTable, Skin skin, Stage stage) {
        super(uiViewport, parentTable, skin);
        this.skin = skin;
        this.stage = stage;

        // Test Dialog
        EventHandler.getEventHandler().createEvent(EventHandler.Event.DUCK_SCENARIO_DIALOG, (p) -> {
            duckDialog();
            return null;
        });
        EventHandler.getEventHandler().createEvent(EventHandler.Event.GRANT_SCENARIO_DIALOG, (p) -> {
            grantDialog();
            return null;
        });
        EventHandler.getEventHandler().createEvent(EventHandler.Event.ROSES_SCENARIO_DIALOG, (p) -> {
            rosesDialog();
            return null;
        });
    }

    @Override
    public void render() {
    }

    @Override
    protected void placeTable() {
    }

    private void grantDialog() {
        Dialog dialog = new Dialog("University Receives a Prestigious Research Grant", skin) {
            @Override
            protected void result(Object object) {
                System.out.println("Dialog choice: " + object);
                if (object.equals(1)){
                    GameState.getState().money += 800000; // Current amount player starts with,
                                                          // unsure if this is sufficient.
                }

                EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
                // can call another event here that has access to more of the game variables,
                // passing in the result (object)
            }
        };

        // Create a Label with text wrapping enabled
        Label label = new Label("The university has been awarded a substantial research grant from a renowned foundation to fund groundbreaking projects in various academic fields. This financial boost is expected to enhance the university’s reputation, attract top-tier researchers, and provide students with cutting-edge learning opportunities. The administration is eager to promote this achievement and the university community celebrates the recognition.", skin);

        // Set the label to wrap text
        label.setWrap(true);

        // Add the label to the dialog's content table
        dialog.getContentTable().add(label).width(500).pad(10); // You can adjust width as needed


        // Add the scroll pane as the content of the dialog
        dialog.getContentTable().clear(); // Clear existing content
        dialog.getContentTable().add(label).width(500).height(300).pad(10); // You can adjust the size

        dialog.button("Refuse grant", 0);
        dialog.button("Take grant", 1);

        // Show the dialog
        dialog.show(stage);
    }

    private void duckDialog() {
        Dialog dialog = new Dialog("University Duck has died", skin) {
            @Override
            protected void result(Object object) {
                System.out.println("Dialog choice: " + object);
                EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
                // can call another event here that has access to more of the game variables,
                // passing in the result (object)
            }
        };

        // Create a Label with text wrapping enabled
        Label label = new Label("yap", skin);

        // Set the label to wrap text
        label.setWrap(true);

        // Add the label to the dialog's content table
        dialog.getContentTable().add(label).width(500).pad(10); // You can adjust width as needed


        // Add the scroll pane as the content of the dialog
        dialog.getContentTable().clear(); // Clear existing content
        dialog.getContentTable().add(label).width(500).height(300).pad(10); // You can adjust the size

        dialog.button("duck button 0", 0);
        dialog.button("duck button 1", 1);

        // Show the dialog
        dialog.show(stage);
    }


    private void rosesDialog() {
        Dialog dialog = new Dialog("Roses", skin) {
            @Override
            protected void result(Object object) {
                System.out.println("Dialog choice: " + object);
                EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
                // can call another event here that has access to more of the game variables,
                // passing in the result (object)
            }
        };

        // Create a Label with text wrapping enabled
        Label label = new Label("rosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhiterosesarewhite", skin);

        // Set the label to wrap text
        label.setWrap(true);

        // Add the label to the dialog's content table
        dialog.getContentTable().add(label).width(500).pad(10); // You can adjust width as needed


        // Add the scroll pane as the content of the dialog
        dialog.getContentTable().clear(); // Clear existing content
        dialog.getContentTable().add(label).width(500).height(300).pad(10); // You can adjust the size

        dialog.button("Don't host roses", 0);
        dialog.button("Host roses", 1);

        // Show the dialog
        dialog.show(stage);
    }
}
