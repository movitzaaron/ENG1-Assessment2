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
                if (object.equals(1)){
                    //Enables the tutorial
                    tutorialDialogOutcome();
                }
                else {
                    EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
                    }
            }
        };

        // Create a Label with text wrapping enabled
        Label label = new Label("Thank you for playing Longboi Life ! \n\n" +
            "If you have not played the game before. We recommend enabling the tutorial!", skin);

        // Set the label to wrap text
        label.setWrap(true);

        // Add the label to the dialog's content table
        dialog.getContentTable().add(label).width(500).height(300).pad(10); // You can adjust the size

        dialog.button("Disable tutorial", 0);
        dialog.button("Enable tutorial", 1);

        // Show the dialog
        dialog.show(stage);
    }

    private void tutorialDialogOutcome() {
        // Followup dialog: Tutorial explanation
        Dialog tutorialDialog = new Dialog("Tutorial", skin) {
            @Override
            protected void result(Object object) {
                // Resumes the game when "Continue" is clicked
                EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
            }
        };
        // Label for the tutorial dialog
        Label tutorialLabel = new Label(
            "Welcome to the tutorial!\n\n\n" +
                "1. To get started place buildings by selecting them from the build menu.\n\n" +
                "2. Placing a building costs money. Once placed, the building can be moved or sold by clicking on it.\n\n" +
                "3. Throughout the game events will occur that will impact your gameplay.\n\n" +
                "4. To keep your students satisfied you must create adequate supporting buildings and connect them via roads.\n\n" +
                "5. Grow your university by strategically managing your buildings and funds.\n\n\n" +
                "Good luck, and enjoy Longboi Life!",
            skin
        );
        tutorialLabel.setWrap(true);

        // Add the label to the content table
        tutorialDialog.getContentTable().add(tutorialLabel).width(500).height(300).pad(10);

        // Add a "Continue" button to close the tutorial dialog
        tutorialDialog.button("Continue", 0);

        // Show the tutorial dialog
        tutorialDialog.show(stage);
    }

    private void grantDialog() {
        Dialog dialog = new Dialog("University Receives a Prestigious Research Grant", skin) {
            @Override
            protected void result(Object object) {
                System.out.println("Dialog choice: " + object);
                if (object.equals(0)){
                    GameState.getState().money += 500000; // may need a revise when balancing game
                }

                EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
                // can call another event here that has access to more of the game variables,
                // passing in the result (object)
            }
        };

        // Create a Label with text wrapping enabled
        Label label = new Label("Congratulations! You've been awarded a financial grant of £500,000 to support your efforts.\n\n" +
            "We understand that managing resources can be challenging, but this grant is here to help you bounce back and continue building your legacy.\n\n" +
            "Keep pushing forward, and remember, strategic decisions are the key to success!", skin);

        // Set the label to wrap text
        label.setWrap(true);

        // Add the label to the dialog's content table
        dialog.getContentTable().add(label).width(500).height(300).pad(10); // You can adjust the size

        dialog.button("Accept grant", 0);

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
                    if(GameState.getState().satisfactionScore >= 0.3 && GameState.getState().getBuildingCount(BuildingType.GYM) >= 2 ) {
                        GameState.getState().money += 200000;
                        GameState.getState().satisfactionScore += 0.1;
                        rosesDialogOutcome(true);
                    } else {
                        GameState.getState().money -= 100000;
                        GameState.getState().satisfactionScore -= 0.1;
                        rosesDialogOutcome(false);
                    }
                } else {
                    EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
                    // Resumes game
                }
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

    private void rosesDialogOutcome(boolean outcome) {
        // Followup dialog: Roses outcome
        Dialog rosesOutcomeDialog = new Dialog("Roses Outcome !", skin) {
            @Override
            protected void result(Object object) {
                // Resumes the game when "Continue" is clicked
                EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
            }
        };
        Label rosesOutcomeLabel;
        if(outcome) {
            // Label for the roses success dialog
             rosesOutcomeLabel = new Label(
                "Congratulations! Your University has emerged victorious in the prestigious Roses competition!\n\n" +
                    "Your students' dedication, fitness, and preparation have paid off, showcasing their incredible spirit across all 47 sports.\n\n" +
                    "Your University is now the envy of the academic world, and the £200,000 reward will surely aid in its continued growth.\n\n" +
                    "Well done! Glory and pride are yours to celebrate!",
                skin
            );
        } else {
            // Label for the roses loss dialog
             rosesOutcomeLabel = new Label(
                "Defeat... Your University fought valiantly in the Roses competition but fell short of victory.\n\n" +
                    "The rival University proved to be stronger this time, dominating the competition across the 47 events.\n\n" +
                    "The loss comes at a cost... £100,000 must now be paid to your rivals. Perhaps your facilities and student satisfaction need a closer look...\n\n" +
                    "Take this as a lesson and come back stronger. The next challenge awaits!",
                skin
            );
        }
        rosesOutcomeLabel.setWrap(true);

        // Add the label to the content table
        rosesOutcomeDialog.getContentTable().add(rosesOutcomeLabel).width(500).height(300).pad(10);

        // Add a "Continue" button to close the tutorial dialog
        rosesOutcomeDialog.button("Continue", 0);

        // Show the tutorial dialog
        rosesOutcomeDialog.show(stage);
    }
}
