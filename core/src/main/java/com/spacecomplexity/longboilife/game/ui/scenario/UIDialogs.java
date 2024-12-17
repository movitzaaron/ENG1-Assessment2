package com.spacecomplexity.longboilife.game.ui.scenario;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.building.BuildingType;
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

        EventHandler eventHandler = EventHandler.getEventHandler();

        // Test Dialog
        eventHandler.createEvent(EventHandler.Event.TUTORIAL_DIALOG, (p) -> {
            tutorialDialog();
            return null;
        });
        eventHandler.createEvent(EventHandler.Event.DUCK_SCENARIO_DIALOG, (p) -> {
            duckDialog();
            return null;
        });
        eventHandler.createEvent(EventHandler.Event.GRANT_SCENARIO_DIALOG, (p) -> {
            grantDialog();
            return null;
        });
        eventHandler.createEvent(EventHandler.Event.ROSES_SCENARIO_DIALOG, (p) -> {
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

    private void tutorialDialog() {
        Dialog dialog = new Dialog("Welcome to Longboi Life.", skin) {
            @Override
            protected void result(Object object) {
                System.out.println("Dialog choice: " + object);
                if (object.equals(0)){
                    //Enables the tutorial
                    GameState.getState().money += 800000;

                }

                EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
                // Resumes the game,
            }
        };

        // Create a Label with text wrapping enabled
        Label label = new Label("Thank you for playing Longboi Life ! \n\n" +
            "If you have not played the game before. We recommend enabling the tutorial!", skin);

        // Set the label to wrap text
        label.setWrap(true);

        // Add the label to the dialog's content table
        dialog.getContentTable().add(label).width(500).height(300).pad(10); // You can adjust the size

        dialog.button("Enable tutorial", 0);
        dialog.button("Disable tutorial", 1);

        // Show the dialog
        dialog.show(stage);
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
                if(object.equals(0)){
                    GameState.getState().money = GameState.getState().money - 1000;
                }
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

        dialog.button("Nurse the duck back to life", 0);
        dialog.button("Let duck die", 1);

        // Show the dialog
        dialog.show(stage);
    }


    private void rosesDialog() {
        Dialog dialog = new Dialog("Roses!", skin) {
            @Override
            protected void result(Object object) {
                System.out.println("Dialog choice: " + object);
                if(object.equals(1)){
                    if(GameState.getState().satisfactionScore > 0.3 && GameState.getState().getBuildingCount(BuildingType.GYM) >= 2 ) {
                        GameState.getState().money += 200000;
                        GameState.getState().satisfactionScore += 10;
                    } else {
                        GameState.getState().money -= 100000;
                        GameState.getState().satisfactionScore -= 10;
                    }

                }
                EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
                // Resumes game
            }
        };

        // Create a Label with text wrapping enabled
        Label label = new Label(
            "Your rival University has challenged you to a prestigious sports competition!\n\n" +
                "With over 47 individual sports on display, this event will test your students' fitness, teamwork, and determination to the limit.\n\n" +
                "Rumors suggest that universities with strong sports facilities and highly motivated students often fare better in such challenges...\n\n" +
                "The victorious university will receive a £200,000 reward, but beware.... failure will cost you £100,000!\n\n" +
                "Are your students ready to rise to the occasion and bring glory to your university?",
            skin
        );
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
