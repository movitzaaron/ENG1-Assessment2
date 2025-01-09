package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to manage achievements
 */
public class AchievementManager {

    // Enum to define available achievements
    public enum Achievement {
        BROKE,
        SATISFIED,
        FITNESS,
        ACADEMIC,
        FOODIE
    }

    /**
     * <p>Accesses preferences file if it exists, otherwise creates it.</p>
     */
    private static final Preferences achPreferences = Gdx.app.getPreferences("achievements");
    /**
     * Creates set of unlocked achievements
     */
    private final Set<Achievement> unlockedAchievements = new HashSet<>();

    /**
     * Load achievements from persistent storage.
     */
    public AchievementManager() {
        loadAchievements();
    }


    /**
     * Check the game state against the conditions of each achievement and call the event if necessary
     */
    public void poll(){
        GameState gameState = GameState.getState();

        if (!unlockedAchievements.contains(Achievement.BROKE) && gameState.money == 0){
            unlockAchievement(Achievement.BROKE, EventHandler.Event.BROKE_ACHIEVEMENT_DIALOG);
        }
        else if (!unlockedAchievements.contains(Achievement.SATISFIED) && gameState.satisfactionScore == 1){
            unlockAchievement(Achievement.SATISFIED, EventHandler.Event.SATISFIED_ACHIEVEMENT_DIALOG);
        }
        else if (!unlockedAchievements.contains(Achievement.FITNESS) && gameState.getBuildingCount(BuildingType.GYM) == 3){
            unlockAchievement(Achievement.FITNESS, EventHandler.Event.FITNESS_ACHIEVEMENT_DIALOG);
        }
        else if (!unlockedAchievements.contains(Achievement.ACADEMIC) && gameState.getBuildingCount(BuildingType.LIBRARY) == 3){
            unlockAchievement(Achievement.ACADEMIC, EventHandler.Event.ACADEMIC_ACHIEVEMENT_DIALOG);
        }
        else if (!unlockedAchievements.contains(Achievement.FOODIE) && gameState.getBuildingCount(BuildingType.GREGGS) == 3){
            unlockAchievement(Achievement.FOODIE, EventHandler.Event.FOODIE_ACHIEVEMENT_DIALOG);
        }
    }

    /**
     * Unlocks the achievement by calling the associated event, and printing achievement name.
     */
    private void unlockAchievement(Achievement achievement, EventHandler.Event event){
        unlockedAchievements.add(achievement);
        achPreferences.putBoolean(achievement.name(), true);
        achPreferences.flush();
        EventHandler.getEventHandler().callEvent(event);
        System.out.println("Achievement unlocked: " + achievement.name());
    }

    /**
     * Load achievements from persistent storage.
     */
    private void loadAchievements() {
        for (Achievement achievement : Achievement.values()) {
            if (achPreferences.getBoolean(achievement.name(), false)) {
                unlockedAchievements.add(achievement);
            }
        }
    }

    /**
     * Reset all achievements (for debugging or testing).
     */
    public void resetAchievements() {
        unlockedAchievements.clear();
        achPreferences.clear();
        achPreferences.flush();
        System.out.println("All achievements reset.");
    }

    /**
     * Get a list of unlocked achievements.
     *
     * @return A set of unlocked achievements.
     */
    public Set<Achievement> getUnlockedAchievements() {
        // reload achievements from prefs file
        loadAchievements();
        return new HashSet<>(unlockedAchievements);
    }

}
