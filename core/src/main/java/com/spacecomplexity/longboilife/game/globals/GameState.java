package com.spacecomplexity.longboilife.game.globals;

import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import java.util.HashMap;

/** Singleton class to contain variables relating to state/config of the game. */
public class GameState {
  private static final GameState gameState = new GameState();

  // >>>> NEW CODE START <<<<
  // NEW: returns an instance of gameState for testing purposes
  public static GameState testCreateInstance() {
    return new GameState();
  }

  // >>>> NEW CODE END <<<<

  /** The current scale factor of game for rendering. */
  public float scaleFactor = 1;

  /** The current scale factor of the UI. */
  public float uiScaleFactor = 1;

  /** Camera speed whilst controlling with keyboard. */
  public float cameraSpeed;

  /** Camera zooming speed whilst controlling with keyboard. */
  public float cameraKeyZoomSpeed;

  /** Camera zooming speed whilst controlling with mouse/trackpad. */
  public float cameraScrollZoomSpeed;

  /** The amount of money the player currently has. */
  public float money;

  /** The current satisfaction score. */
  public float satisfactionScore;

  // >>>> NEW CODE START <<<<
  /** Current satisfaction modifiers. */
  public float satDistanceModifier;

  public float satBuildingCountModifier;

  public float satBuildingProportionModifier;

  public float satEmptyCategoryModifier;

  public float satScenarioModifier;

  // >>>> NEW CODE END <<<<

  /**
   * The building selected to be placed.
   *
   * <p>If {@code null} then nothing is selected.
   */
  public BuildingType placingBuilding;

  /**
   * The currently selected building on the map.
   *
   * <p>If {@code null} then nothing is selected.
   */
  public Building selectedBuilding;

  /**
   * The currently selected building to be moved.
   *
   * <p>If {@code null} then nothing is selected.
   */
  public Building movingBuilding;

  /** If the game is currently paused. */
  public boolean paused;

  // >>>> NEW CODE START <<<<
  /** If the user is currently typing. */
  public boolean isTyping;

  /** The category of the currently open menu. */
  public BuildingCategory openMenuCategory;

  // >>>> NEW CODE END <<<<

  /**
   * The current count of buildings.
   *
   * <p>This should be modified by {@link GameState#getBuildingCount(BuildingType)} and {@link
   * GameState#changeBuildingCount(BuildingType, int)} not directly.
   *
   * <p>This is initialised in the constructor.
   */
  public HashMap<BuildingType, Integer> buildingsCount;

  /**
   * Helper function to get the number of a specified building.
   *
   * @param buildingType the building type to get.
   * @return the building count for this specific building.
   */
  public Integer getBuildingCount(BuildingType buildingType) {
    // >>>> NEW CODE START <<<<
    // NEW: checks building type is not null
    if (buildingType == null) {
      throw new NullPointerException(
          "Getting building count with null BuildingType should throw NullPointerException");
    }
    // >>>> NEW CODE END <<<<

    Integer count = buildingsCount.get(buildingType);
    // If this has not yet been set return 0
    if (count == null) {
      return 0;
    }

    return count;
  }

  // >>>> NEW CODE START <<<<
  /**
   * Helper function to get the number of buildings excluding pathways.
   *
   * @return the total building count excluding pathways
   */
  public Integer getBuildingCountNoPath() {
    Integer count = 0;
    for (BuildingType buildingType : BuildingType.values()) {
      if (buildingType.getCategory() != BuildingCategory.PATHWAY) {
        count += getBuildingCount(buildingType);
      }
    }
    return count;
  }

  // >>>> NEW CODE END <<<<

  /**
   * Helper function to change the number of a specified building.
   *
   * @param buildingType the building type to change.
   * @param change the amount to change it by.
   */
  public void changeBuildingCount(BuildingType buildingType, int change) {
    // >>>> NEW CODE START <<<<
    // NEW: checks building type is not null and building count does not go negative
    if (buildingType == null) {
      throw new NullPointerException(
          "Changing building count with null BuildingType should throw NullPointerException");
    }

    int count = getBuildingCount(buildingType);

    if (count == 0 && change < 0) {
      throw new IllegalStateException("Building count cannot go below 0");
    }
    // >>>> NEW CODE END <<<<

    buildingsCount.put(buildingType, count + change);
  }

  /** The value that the satisfaction score changes by each second. */
  public float satisfactionScoreVelocity = 0;

  /** If the last satisfaction modifier calculated was positive. */
  public boolean satisfactionModifierPositive = false;

  /** If the game has ended. */
  public boolean gameOver = false;

  /**
   * Get the singleton instance of the {@link GameState} class.
   *
   * @return The single {@link GameState} class.
   */
  public static GameState getState() {
    return gameState;
  }

  private GameState() {
    reset();
  }

  /** Reset all values to default. */
  public void reset() {
    scaleFactor = 1;
    uiScaleFactor = 1;
    cameraSpeed = 1400;
    cameraKeyZoomSpeed = 3;
    cameraScrollZoomSpeed = 32;
    money = 800000;
    satisfactionScore = 0f;
    // >>>> NEW CODE START <<<<
    satDistanceModifier = 0f;
    satBuildingCountModifier = 0f;
    satBuildingProportionModifier = 0f;
    satEmptyCategoryModifier = 0f;
    satScenarioModifier = 1f;
    openMenuCategory = null;
    // >>>> NEW CODE END <<<<
    placingBuilding = null;
    selectedBuilding = null;
    movingBuilding = null;
    paused = false;
    buildingsCount = new HashMap<>();
    satisfactionScoreVelocity = 0;
    satisfactionModifierPositive = false;
    gameOver = false;
  }
}
