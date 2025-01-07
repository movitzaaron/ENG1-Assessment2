package com.spacecomplexity.longboilife.menu;

import com.badlogic.gdx.Gdx;
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
import com.spacecomplexity.longboilife.game.utils.SaveScore;

/**
 * Main class to control the leaderboard screen.
 */
public class MenuLeaderboard implements Screen{
    private final Main game;
    private Viewport viewport;
    private Texture backgroundTexture;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private Label namesLabel;
    private Label scoresLabel;

    public MenuLeaderboard(Main game){
        this.game = game;

        // Initialise viewport and drawing elements
        viewport = new FitViewport(640, 480);
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        // Load UI skin for buttons
        skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));
    }

    @Override
    public void show() {
        stage.clear();
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Initialise back button
        TextButton backButton = new TextButton("Back", skin, "round");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Exit the application
                game.switchScreen(Main.ScreenType.MENU);
            }
        });

        // Initialize the leaderboard label
        String topFive = SaveScore.getTopFive();
        String names = "Nothing to show";
        String scores = "";
        if(topFive.length() > 1){
            String[] topFiveSplit = topFive.split("\\|");
            names = topFiveSplit[0].substring(0, topFiveSplit[0].length() - 2);
            scores = topFiveSplit[1].substring(0, topFiveSplit[1].length() - 2);
        }

        BitmapFont font = skin.getFont("font-title"); // use the title font for the leaderboard as it is big on screen
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        Label title = new Label("Leaderboard", labelStyle);
        title.setStyle(labelStyle);
        title.setFontScale(2f);
        title.setAlignment(Align.center);

        namesLabel = new Label(names, skin);
        namesLabel.setStyle(labelStyle);
        namesLabel.setAlignment(Align.left);
        namesLabel.setFontScale(1.5f);

        scoresLabel = new Label(scores, skin);
        scoresLabel.setStyle(labelStyle);
        scoresLabel.setAlignment(Align.right);
        scoresLabel.setFontScale(1.5f);

        Table boardTable = new Table();
        boardTable.add(namesLabel).pad(20).left().expand();
        boardTable.add(scoresLabel).pad(20).right().expand();
        boardTable.setBackground(skin.getDrawable("panel1"));


        table.add(title).center();

        table.row().pad(10);

        table.add(boardTable).expand();

        table.row().pad(10);

        table.add(backButton).pad(10).bottom().left().width(100).height(50);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1f);

        String topFive = SaveScore.getTopFive();
        String names = "Nothing to show";
        String scores = "";
        if(topFive.length() > 1){
            String[] topFiveSplit = topFive.split("\\|");
            names = topFiveSplit[0].substring(0, topFiveSplit[0].length() - 2);
            scores = topFiveSplit[1].substring(0, topFiveSplit[1].length() - 2);
        }
        namesLabel.setText(names);
        scoresLabel.setText(scores);
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
        batch.dispose();
    }
}
