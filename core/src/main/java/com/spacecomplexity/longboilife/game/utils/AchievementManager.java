package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import java.util.HashSet;
import java.util.Set;

// NEW: this is all new
/**
 * Manages the achievements system in the game.
 *
 * <p>This class is responsible for tracking, unlocking, and storing achievements. Achievements are
 * unlocked based on specific conditions checked against the current game state. Once unlocked,
 * achievements are persisted using {@link Preferences} to ensure they remain available across
 * sessions.
 *
 * <p><strong>Key Features:</strong>
 *
 * <ul>
 *   <li>Tracks the state of unlocked achievements.
 *   <li>Checks game state conditions to determine if achievements should be unlocked.
 *   <li>Integrates with the event handling system to trigger achievement-related events.
 *   <li>Supports persistence and reset of achievements.
 * </ul>
 */
public class AchievementManager {

  /** Defines the available achievements in the game. */
  public enum Achievement {
    BROKE,
    SATISFIED,
    FITNESS,
    ACADEMIC,
    FOODIE
  }

  /** Accesses preferences file if it exists, otherwise creates it. */
  private static final Preferences achPreferences = Gdx.app.getPreferences("achievements");

  /** Creates set of unlocked achievements. */
  private final Set<Achievement> unlockedAchievements = new HashSet<>();

  /** Load achievements from persistent storage. */
  public AchievementManager() {
    loadAchievements(achPreferences);
  }

  /** Load achievements from persistent storage. */
  public AchievementManager(Preferences preferences) {
    loadAchievements(preferences);
  }

  /**
   * Polls the game state to evaluate achievement conditions.
   *
   * <p>Checks the current game state against the conditions for each achievement. If a condition is
   * met and the achievement has not been unlocked, the achievement is unlocked and its associated
   * event is triggered.
   */
  public void poll() {
    GameState gameState = GameState.getState();
    poll(gameState, achPreferences);
  }

  /**
   * Polls the game state and evaluates conditions for unlocking achievements.
   *
   * @param gameState the current game state
   * @param preferences the preferences file to persist unlocked achievements
   */
  public void poll(GameState gameState, Preferences preferences) {
    if (!unlockedAchievements.contains(Achievement.BROKE) && gameState.money <= 1000) {
      unlockAchievement(
          Achievement.BROKE, EventHandler.Event.BROKE_ACHIEVEMENT_DIALOG, preferences);
    } else if (!unlockedAchievements.contains(Achievement.SATISFIED)
        && gameState.satisfactionScore == 1) {
      unlockAchievement(
          Achievement.SATISFIED, EventHandler.Event.SATISFIED_ACHIEVEMENT_DIALOG, preferences);
    } else if (!unlockedAchievements.contains(Achievement.FITNESS)
        && gameState.getBuildingCount(BuildingType.GYM) == 3) {
      unlockAchievement(
          Achievement.FITNESS, EventHandler.Event.FITNESS_ACHIEVEMENT_DIALOG, preferences);
    } else if (!unlockedAchievements.contains(Achievement.ACADEMIC)
        && gameState.getBuildingCount(BuildingType.LIBRARY) == 3) {
      unlockAchievement(
          Achievement.ACADEMIC, EventHandler.Event.ACADEMIC_ACHIEVEMENT_DIALOG, preferences);
    } else if (!unlockedAchievements.contains(Achievement.FOODIE)
        && gameState.getBuildingCount(BuildingType.GREGGS) == 3) {
      unlockAchievement(
          Achievement.FOODIE, EventHandler.Event.FOODIE_ACHIEVEMENT_DIALOG, preferences);
    }
  }

  /** Unlocks the achievement by calling the associated event, and printing achievement name. */
  protected void unlockAchievement(Achievement achievement, EventHandler.Event event) {
    unlockAchievement(achievement, event, achPreferences);
  }

  /** Unlocks the achievement by calling the associated event, and printing achievement name. */
  private void unlockAchievement(
      Achievement achievement, EventHandler.Event event, Preferences preferences) {
    unlockedAchievements.add(achievement);
    preferences.putBoolean(achievement.name(), true);
    preferences.flush();
    EventHandler.getEventHandler().callEvent(event);
    System.out.println("Achievement unlocked: " + achievement.name());
  }

  private void unlockAchievementsNS(Preferences preferences) {}

  /** Load achievements from persistent storage. */
  private void loadAchievements(Preferences preferences) {
    for (Achievement achievement : Achievement.values()) {
      if (preferences.getBoolean(achievement.name(), false)) {
        unlockedAchievements.add(achievement);
      }
    }
  }

  /** Reset all achievements (for debugging or testing). */
  public void resetAchievements(Preferences preferences) {
    unlockedAchievements.clear();
    preferences.clear();
    preferences.flush();
    System.out.println("All achievements reset.");
  }

  /**
   * Get a list of unlocked achievements.
   *
   * @return A set of unlocked achievements.
   */
  public Set<Achievement> getUnlockedAchievements() {
    return getUnlockedAchievements(achPreferences);
  }

  /**
   * Get a list of unlocked achievements.
   *
   * @return A set of unlocked achievements.
   */
  public Set<Achievement> getUnlockedAchievements(Preferences preferences) {
    // reload achievements from prefs file
    loadAchievements(preferences);
    return new HashSet<>(unlockedAchievements);
  }
}
