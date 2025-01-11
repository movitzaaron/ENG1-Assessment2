package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class GameStateTest extends AbstractHeadlessGdxTest {
    private GameState gameState;

    @BeforeEach
    void setUp() {
        gameState = GameState.TEST_CreateInstance();
        gameState.reset();
    }

    @Test
    @DisplayName("Singleton Instance Test")
    void testSingletonInstance() {
        // Arrange & Act
        GameState instance1 = GameState.getState();
        GameState instance2 = GameState.getState();

        // Assert
        assertSame(instance1, instance2, "Both instances should be the same (singleton)");
    }

    @Test
    @DisplayName("Default Values After Reset")
    void testDefaultValuesAfterReset() {
        // Arrange & Act
        gameState.reset();

        // Assert
        assertEquals(1f, gameState.scaleFactor, "Default scaleFactor should be 1");
        assertEquals(1f, gameState.uiScaleFactor, "Default uiScaleFactor should be 1");
        assertEquals(1400f, gameState.cameraSpeed, "Default cameraSpeed should be 1400");
        assertEquals(3f, gameState.cameraKeyZoomSpeed, "Default cameraKeyZoomSpeed should be 3");
        assertEquals(32f, gameState.cameraScrollZoomSpeed, "Default cameraScrollZoomSpeed should be 32");
        assertEquals(800000f, gameState.money, "Default money should be 800000");
        assertEquals(0f, gameState.satisfactionScore, "Default satisfactionScore should be 0");
        assertNull(gameState.placingBuilding, "Default placingBuilding should be null");
        assertNull(gameState.selectedBuilding, "Default selectedBuilding should be null");
        assertNull(gameState.movingBuilding, "Default movingBuilding should be null");
        assertFalse(gameState.paused, "Default paused should be false");
        assertNotNull(gameState.buildingsCount, "buildingsCount should be initialized");
        assertTrue(gameState.buildingsCount.isEmpty(), "buildingsCount should be empty after reset");
        assertEquals(0f, gameState.satisfactionScoreVelocity, "Default satisfactionScoreVelocity should be 0");
        assertFalse(gameState.satisfactionModifierPositive, "Default satisfactionModifierPositive should be false");
        assertFalse(gameState.gameOver, "Default gameOver should be false");
    }

    @Test
    @DisplayName("Get Building Count When Not Set")
    void testGetBuildingCount_NotSet() {
        // Arrange
        BuildingType buildingType = BuildingType.GREGGS;

        // Act
        Integer count = gameState.getBuildingCount(buildingType);

        // Assert
        assertEquals(0, count, "Building count should be 0 when not set");
    }

    @Test
    @DisplayName("Change Building Count and Get Building Count")
    void testChangeBuildingCount() {
        // Arrange
        BuildingType buildingType = BuildingType.GYM; // Assume BARRACKS is a valid enum constant

        // Act & Assert
        assertEquals(0, gameState.getBuildingCount(buildingType), "Initial building count should be 0");

        gameState.changeBuildingCount(buildingType, 5);
        assertEquals(5, gameState.getBuildingCount(buildingType), "Building count should be 5 after adding 5");

        gameState.changeBuildingCount(buildingType, -2);
        assertEquals(3, gameState.getBuildingCount(buildingType), "Building count should be 3 after removing 2");

        gameState.changeBuildingCount(buildingType, 0);
        assertEquals(3, gameState.getBuildingCount(buildingType), "Building count should remain 3 after no change");
    }

    @Test
    @DisplayName("Change Building Count for Multiple Building Types")
    void testChangeBuildingCount_MultipleTypes() {
        // Arrange
        BuildingType halls = BuildingType.HALLS;
        BuildingType gym = BuildingType.GYM;

        // Act
        gameState.changeBuildingCount(halls, 3);
        gameState.changeBuildingCount(gym, 7);

        // Assert
        assertEquals(3, gameState.getBuildingCount(halls), "Barracks count should be 3");
        assertEquals(7, gameState.getBuildingCount(gym), "Factory count should be 7");
    }

    @Test
    @DisplayName("Building Count Does Not Go Negative")
    void testChangeBuildingCount_NegativeResult() {
        // Arrange
        BuildingType buildingType = BuildingType.LIBRARY;

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            gameState.changeBuildingCount(buildingType, -1);
        }, "Building count cannot go below 0");
    }

    @Test
    @DisplayName("Change Building Count with Null BuildingType")
    void testChangeBuildingCount_NullBuildingType() {
        // Arrange
        BuildingType buildingType = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            gameState.changeBuildingCount(buildingType, 1);
        }, "Changing building count with null BuildingType should throw NullPointerException");
    }

    @Test
    @DisplayName("Get Building Count with Null BuildingType")
    void testGetBuildingCount_NullBuildingType() {
        // Arrange
        BuildingType buildingType = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            gameState.getBuildingCount(buildingType);
        }, "Getting building count with null BuildingType should throw NullPointerException");
    }

    @Test
    @DisplayName("Direct Modification of Public Fields")
    void testDirectModificationOfPublicFields() {
        // Arrange
        gameState.money = 1000000f;
        gameState.paused = true;
        gameState.gameOver = true;
        gameState.scaleFactor = 2.0f;

        // Act
        float money = gameState.money;
        boolean paused = gameState.paused;
        boolean gameOver = gameState.gameOver;
        float scaleFactor = gameState.scaleFactor;

        // Assert
        assertEquals(1000000f, money, "Money should be directly modifiable");
        assertTrue(paused, "Paused should be directly modifiable");
        assertTrue(gameOver, "GameOver should be directly modifiable");
        assertEquals(2.0f, scaleFactor, "ScaleFactor should be directly modifiable");
    }

    @Test
    @DisplayName("BuildingsCount HashMap Isolation")
    void testBuildingsCountIsolation() {
        // Arrange
        BuildingType buildingType = BuildingType.GYM;
        gameState.changeBuildingCount(buildingType, 4);

        // Act
        HashMap<BuildingType, Integer> buildingsCount = gameState.buildingsCount;
        buildingsCount.put(BuildingType.HALLS, 10);

        // Assert
        assertEquals(4, gameState.getBuildingCount(buildingType), "Original building count should remain unchanged");
        assertEquals(10, gameState.getBuildingCount(BuildingType.HALLS), "Newly added building count should be reflected");
    }

    @Test
    @DisplayName("Reset BuildingsCount HashMap")
    void testResetBuildingsCount() {
        // Arrange
        BuildingType buildingType = BuildingType.GREGGS;
        gameState.changeBuildingCount(buildingType, 5);
        assertEquals(5, gameState.getBuildingCount(buildingType), "Building count should be 5 before reset");

        // Act
        gameState.reset();

        // Assert
        assertEquals(0, gameState.getBuildingCount(buildingType), "Building count should reset to 0");
    }

    @Test
    @DisplayName("Modify BuildingsCount via Reflection")
    void testModifyBuildingsCount_Reflection() throws NoSuchFieldException, IllegalAccessException {
        // Advanced Test: Directly modifying the buildingsCount map via reflection
        // to ensure encapsulation if needed.

        // Arrange
        Field buildingsCountField = GameState.class.getDeclaredField("buildingsCount");
        buildingsCountField.setAccessible(true);
        HashMap<BuildingType, Integer> buildingsCount = (HashMap<BuildingType, Integer>) buildingsCountField.get(gameState);

        // Act
        buildingsCount.put(BuildingType.GREGGS, 15);

        // Assert
        assertEquals(15, gameState.getBuildingCount(BuildingType.GREGGS), "Building count should reflect changes made via reflection");
    }
}
