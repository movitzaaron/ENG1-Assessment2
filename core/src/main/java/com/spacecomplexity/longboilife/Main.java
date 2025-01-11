package com.spacecomplexity.longboilife;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.spacecomplexity.longboilife.game.GameScreen;
import com.spacecomplexity.longboilife.game.utils.AchievementManager;
import com.spacecomplexity.longboilife.menu.MenuAchievements;
import com.spacecomplexity.longboilife.menu.MenuScreen;
import com.spacecomplexity.longboilife.menu.MenuLeaderboard;

import java.util.HashMap;

/**
 * The main class (entry point).
 * Responsible for managing and switching screens.
 */
public class Main extends Game {
    /**
     * If the game is in fullscreen mode.
     */
    public static boolean fullscreen = false;
    /**
     * The previous dimensions of the game, for returning from fullscreen.
     */
    public static int prevAppWidth, prevAppHeight;

    // Needed param for MenuAchievements class
    private AchievementManager achievementManager;

    /**
     * Enum containing all screens and there class references.
     */
    public enum ScreenType {
        MENU(MenuScreen.class),
        GAME(GameScreen.class),
        LB(MenuLeaderboard.class),
        Achieve(MenuAchievements.class)
        ;

        private final Class<? extends Screen> screenClass;

        ScreenType(Class<? extends Screen> screenClass) {
            this.screenClass = screenClass;
        }

        public Class<? extends Screen> getScreenClass() {
            return screenClass;
        }
    }

    private HashMap<ScreenType, Screen> screens = new HashMap<>();

    @Override
    public void create() {
        // Initially load the menu screen
        if (Gdx.app.getType() != Application.ApplicationType.HeadlessDesktop){
            switchScreen(ScreenType.MENU);
        }
        achievementManager = new AchievementManager();
    }

    /**
     * Show a screen.
     *
     * @param screen the screen to show.
     */
    public void switchScreen(ScreenType screen) {
        // Lazy loading
        if (!screens.containsKey(screen)) {
            // >>>> NEW CODE START <<<<
            try {
                 Screen newScreen;
                if (screen == ScreenType.Achieve) {
                    // Switch to Achievement screen
                    newScreen = new MenuAchievements(this, achievementManager);
                } else {
                    newScreen = screen.getScreenClass().getConstructor(Main.class).newInstance(this);
                }
                screens.put(screen, newScreen);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create screen: " + screen.name(), e);
            }
            // >>>> NEW CODE END <<<<
        }

        // Switch to the screen
        setScreen(screens.get(screen));
    }

    @Override
    public void dispose() {
        for (Screen screen : screens.values()) {
            screen.dispose();
        }

        super.dispose();
    }
}
