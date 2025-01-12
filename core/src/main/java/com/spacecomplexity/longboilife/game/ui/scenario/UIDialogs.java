package com.spacecomplexity.longboilife.game.ui.scenario;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.scenarios.DuckScenario;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

// NEW: This is all new
/**
 * A UI test class that creates and shows a modal dialog. The dialog itself is modal, meaning it
 * will block input to underlying UI elements.
 */
public class UIDialogs extends UIElement {
  private final Skin skin;
  private final Stage stage;

  public UIDialogs(Viewport uiViewport, Table parentTable, Skin skin, Stage stage) {
    super(uiViewport, parentTable, skin);
    this.skin = skin;
    this.stage = stage;

    // Retrieve the larger font
    BitmapFont largeFont = skin.getFont("font-large");
    // Create a new LabelStyle with the larger font
    Label.LabelStyle largeLabelStyle = new Label.LabelStyle();
    largeLabelStyle.font = largeFont;
    // Store the new style in the new skin
    skin.add("large-label", largeLabelStyle, Label.LabelStyle.class);

    EventHandler eventHandler = EventHandler.getEventHandler();

    // Test Dialog
    eventHandler.createEvent(
        EventHandler.Event.TUTORIAL_DIALOG,
        (p) -> {
          tutorialDialog();
          return null;
        });
    eventHandler.createEvent(
        EventHandler.Event.TUTORIAL_TEXT_DIALOG,
        (p) -> {
          tutorialDialogOutcome();
          return null;
        });
    eventHandler.createEvent(
        EventHandler.Event.DUCK_SCENARIO_DIALOG,
        (p) -> {
          duckDialog();
          return null;
        });
    eventHandler.createEvent(
        EventHandler.Event.GRANT_SCENARIO_DIALOG,
        (p) -> {
          grantDialog();
          return null;
        });
    eventHandler.createEvent(
        EventHandler.Event.ROSES_SCENARIO_DIALOG,
        (p) -> {
          rosesDialog();
          return null;
        });
  }

  @Override
  public void render() {}

  @Override
  protected void placeTable() {}

  private void tutorialDialog() {
    Dialog dialog =
        new Dialog("Welcome to Longboi Life.", skin) {
          @Override
          protected void result(Object object) {
            if (object.equals(1)) {
              // Enables the tutorial
              tutorialDialogOutcome();
            } else {
              EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
            }
          }
        };

    // Create a Label with text wrapping enabled
    Label label =
        new Label(
            """
            Thank you for playing Longboi Life!\s

            If you have not played the game before. We recommend enabling the tutorial!""",
            skin,
            "large-label");

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
    Dialog tutorialDialog =
        new Dialog("Tutorial", skin) {
          @Override
          protected void result(Object object) {
            // Resumes the game when "Continue" is clicked
            EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
          }
        };
    // Label for the tutorial dialog
    Label tutorialLabel =
        new Label(
            """
                Press T at any time to bring this text back, or press the '?' button

                1. Move around the map by using WASD keys and change zoom using Q and E keys.

                2. To get started place buildings by selecting them from the build menu.

                3. Placing a building costs money. Once placed, the building can be moved or sold by clicking on it.

                4. Throughout the game events will occur that will impact your gameplay.

                5. To keep your students satisfied you must create adequate supporting buildings and connect them via roads.

                6. Grow your university by strategically managing your buildings and funds.


                Good luck, and enjoy Longboi Life!""",
            skin,
            "large-label");
    tutorialLabel.setWrap(true);

    // Create a scroll pane for the label
    ScrollPane scrollPane = new ScrollPane(tutorialLabel, skin);
    scrollPane.setScrollingDisabled(true, false); // Enable vertical scrolling only
    scrollPane.setFadeScrollBars(false); // Keep scrollbars always visible
    scrollPane.setForceScroll(false, true); // Force vertical scroll even if not actively scrolling
    tutorialDialog
        .getContentTable()
        .add(scrollPane)
        .width(500)
        .height(300)
        .pad(10)
        .align(Align.center);

    // Add a "Continue" button to close the tutorial dialog
    tutorialDialog.button("Continue", 0);

    // Show the tutorial dialog
    stage.addActor(tutorialDialog);
    tutorialDialog.show(stage);
  }

  private void grantDialog() {
    Dialog dialog =
        new Dialog("University Receives a Prestigious Research Grant", skin) {
          @Override
          protected void result(Object object) {
            if (object.equals(0)) {
              GameState.getState().money += 500000; // may need a revise when balancing game
            }

            EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
            // can call another event here that has access to more of the game variables,
            // passing in the result (object)
          }
        };

    // Create a Label with text wrapping enabled
    Label label =
        new Label(
            """
            Congratulations! You've been awarded a financial grant of £500,000 to support your efforts.

            Keep pushing forward, good luck on making your University prosper!""",
            skin,
            "large-label");

    // Set the label to wrap text
    label.setWrap(true);

    // Add the label to the dialog's content table
    dialog.getContentTable().add(label).width(500).height(300).pad(10); // You can adjust the size

    dialog.button("Accept grant", 0);

    // Show the dialog
    dialog.show(stage);
  }

  private void duckDialog() {
    Dialog dialog =
        new Dialog("University Duck has fallen ill", skin) {
          @Override
          protected void result(Object object) {
            EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
            if (object.equals(0)) {
              // deduct money
              GameState.getState().money = GameState.getState().money - DuckScenario.cost;
            } else {
              // slightly decrease satisfaction until next scenario is triggered
              GameState.getState().satScenarioModifier = 0.9f;
            }
          }
        };

    // Create a Label with text wrapping enabled
    Label label =
        new Label(
            """
                The beloved university duck, has fallen ill!!\s

                Its health crisis has sparked concern and emotional turmoil among the university community.""",
            skin,
            "large-label");

    // Set the label to wrap text
    label.setWrap(true);

    // Add the label to the dialog's content table
    dialog.getContentTable().add(label).width(500).pad(10); // You can adjust width as needed

    // Add the scroll pane as the content of the dialog
    dialog.getContentTable().clear(); // Clear existing content
    dialog.getContentTable().add(label).width(500).height(300).pad(10); // You can adjust the size

    dialog.button("Spend £" + DuckScenario.cost + " on duck surgery", 0);
    dialog.button("Let the duck die", 1);

    // Show the dialog
    dialog.show(stage);
  }

  private void rosesDialog() {
    Dialog dialog =
        new Dialog("Roses!", skin) {
          @Override
          protected void result(Object object) {
            if (object.equals(1)) {
              if (GameState.getState().satisfactionScore >= 0.3
                  && GameState.getState().getBuildingCount(BuildingType.GYM) >= 2) {
                GameState.getState().money += 200000;
                GameState.getState().satScenarioModifier = 1f;
                rosesDialogOutcome(true);
              } else {
                GameState.getState().satScenarioModifier = 0.9f;
                rosesDialogOutcome(false);
              }
            } else {
              EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
              // Resumes game
            }
          }
        };

    // Create a Label with text wrapping enabled
    Label label =
        new Label(
            """
                Your rival University has challenged you to a sports competition!

                Rumors suggest that universities with strong sports facilities and motivated students often fare better in such challenges...
                The victorious university will receive a £200,000 reward, but beware... failure will make your students less satisfied!""",
            skin,
            "large-label");
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
    Dialog rosesOutcomeDialog =
        new Dialog("Roses Outcome !", skin) {
          @Override
          protected void result(Object object) {
            // Resumes the game when "Continue" is clicked
            EventHandler.getEventHandler().callEvent(EventHandler.Event.RESUME_GAME);
          }
        };
    Label rosesOutcomeLabel;
    if (outcome) {
      // Label for the roses success dialog
      rosesOutcomeLabel =
          new Label(
              """
                     Congratulations! Your University has emerged victorious in the prestigious Roses competition!

                     Your University is now the envy of the academic world, and the £200,000 reward will surely aid in its continued growth.

                     Glory and pride are yours to celebrate!""",
              skin,
              "large-label");
    } else {
      // Label for the roses loss dialog
      rosesOutcomeLabel =
          new Label(
              """
                     Defeat...\s

                     The rival University proved to be stronger this time, dominating the competition across the 47 events.

                     The loss comes at a cost... Your students are quite upset. Perhaps your facilities and student satisfaction need a closer look...""",
              skin,
              "large-label");
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
