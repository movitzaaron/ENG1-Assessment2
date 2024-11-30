package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import com.spacecomplexiy.longboilife.headless.HeadlessLauncher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Building class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BuildingTest {

    @BeforeAll
    public void setUp() {
        // No initialization needed
    }

    @AfterAll
    public void tearDown() {
        // Dispose of any loaded textures if necessary
        for (BuildingType type : BuildingType.values()) {
            type.dispose();
        }
    }

    @Test
    public void testBuildingConstructorAndGetters() {
        // Arrange
        BuildingType type = BuildingType.GREGGS;
        Vector2Int position = new Vector2Int(10, 20);

        // Act
        Building building = new Building(type, position);

        // Assert
        assertEquals(type, building.getType(), "Building type should match the constructor argument.");
        assertEquals(position, building.getPosition(), "Building position should match the constructor argument.");
    }

    @Test
    public void testSetPosition() {
        // Arrange
        BuildingType type = BuildingType.LIBRARY;
        Vector2Int initialPosition = new Vector2Int(5, 5);
        Building building = new Building(type, initialPosition);

        Vector2Int newPosition = new Vector2Int(15, 25);

        // Act
        building.setPosition(newPosition);

        // Assert
        assertEquals(newPosition, building.getPosition(), "Building position should be updated to the new position.");
    }

    @Test
    public void testSetPositionToNull() {
        // Arrange
        BuildingType type = BuildingType.HALLS;
        Vector2Int initialPosition = new Vector2Int(0, 0);
        Building building = new Building(type, initialPosition);

        // Act
        building.setPosition(null);

        // Assert
        assertNull(building.getPosition(), "Building position should be set to null.");
    }

    @Test
    public void testConstructorWithNullType() {
        // Arrange
        Vector2Int position = new Vector2Int(10, 20);

        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Building(null, position);
        }, "Constructor should throw NullPointerException when type is null.");

        assertEquals("Building type cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructorWithNullPosition() {
        // Arrange
        BuildingType type = BuildingType.ROAD;

        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new Building(type, null);
        }, "Constructor should throw NullPointerException when position is null.");

        assertEquals("Building position cannot be null.", exception.getMessage());
    }

    @Test
    public void testAllTexturesExist() {
        HeadlessLauncher.main(new String[0]);
        BuildingType type = BuildingType.ROAD;
        assertTrue(Gdx.files.internal(type.getTexturePath()).exists());
        type = BuildingType.LIBRARY;
        assertTrue(Gdx.files.internal(type.getTexturePath()).exists());
        type = BuildingType.GREGGS;
        assertTrue(Gdx.files.internal(type.getTexturePath()).exists());
        type = BuildingType.HALLS;
        assertTrue(Gdx.files.internal(type.getTexturePath()).exists());
        type = BuildingType.GYM;
        assertTrue(Gdx.files.internal(type.getTexturePath()).exists());
    }
}
