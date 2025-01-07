package com.spacecomplexity.longboilife.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.Main;
import com.spacecomplexity.longboilife.MainInputManager;
import com.spacecomplexity.longboilife.game.utils.AchievementManager;

/**
 * Main class to control the leaderboard screen.
 */
public class MenuAchievements implements Screen {
    private final Main game;
    private final AchievementManager achievementManager;
    private Viewport viewport;
    private Texture backgroundTexture;
    private Stage stage;
    private Skin skin;

    public MenuAchievements(Main game, AchievementManager achievementManager) {
        this.game = game;
        this.achievementManager = achievementManager;

        // Initialise viewport and drawing elements
        viewport = new FitViewport(640, 480);
        stage = new Stage(viewport);


        // Load UI skin for buttons
        skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Title
        BitmapFont font = skin.getFont("font-title");
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        Label title = new Label("Achievements", labelStyle);
        title.setFontScale(2f);
        title.setAlignment(Align.center);

        table.add(title).pad(10).center();
        table.row().pad(20);

        // Achievement List#
        Table achievementsTable = new Table();
        achievementsTable.setBackground(skin.getDrawable("panel1"));

        for (AchievementManager.Achievement achievement : AchievementManager.Achievement.values()) {
            boolean unlocked = achievementManager.getUnlockedAchievements().contains(achievement);

            Label achievementLabel = new Label(achievement.name(), skin);
            achievementLabel.setStyle(new Label.LabelStyle(font, unlocked ? Color.GREEN : Color.GRAY));
            achievementLabel.setFontScale(1.5f);
            achievementLabel.setAlignment(Align.left);

            achievementsTable.add(achievementLabel).pad(10).left().expandX();
            achievementsTable.row();
        }

        table.add(achievementsTable).expand().fill();
        table.row().pad(20);



        // Initialise back button
        TextButton backButton = new TextButton("Back", skin, "round");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Exit the application
                game.switchScreen(Main.ScreenType.MENU);
            }
        });

        // Position back button in the bottom-right
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        stage.addActor(buttonTable);

        buttonTable.bottom().right().pad(30);
        buttonTable.add(backButton).width(100).height(50);

        // Allows UI to capture touch events
        InputMultiplexer inputMultiplexer = new InputMultiplexer(new MainInputManager(), stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1f);

        // Draw and apply ui
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    // Disallows input of hidden buttons
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}
