package com.spacecomplexity.longboilife.game.building;

import com.spacecomplexity.longboilife.AbstractHeadlessGdxTest;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Building class.
 */
public class BuildingTest extends AbstractHeadlessGdxTest {

    /**
     * Tests the constructor and getter methods of the Building class.
     */
    @Test
    public void testBuildingConstructorAndGetters() {
        // Arrange
        BuildingType type = BuildingType.GREGGS; // Using actual enum constants
        Vector2Int position = new Vector2Int(10, 20);

        // Act
        Building building = new Building(type, position);

        // Assert
        assertEquals(type, building.getType(), "Building type should match the constructor argument.");
        assertEquals(position, building.getPosition(), "Building position should match the constructor argument.");
    }

    /**
     * Tests the setPosition method of the Building class.
     */
    @Test
    public void testSetPosition() {
        // Arrange
        BuildingType type = BuildingType.GYM; // Using actual enum constants
        Vector2Int initialPosition = new Vector2Int(5, 5);
        Building building = new Building(type, initialPosition);

        Vector2Int newPosition = new Vector2Int(15, 25);

        // Act
        building.setPosition(newPosition);

        // Assert
        assertEquals(newPosition, building.getPosition(), "Building position should be updated to the new position.");
    }

    /**
     * Tests that the Building class allows setting the position to null.
     * Adjust this test based on your intended behavior.
     */
    @Test
    public void testSetPositionToNull() {
        // Arrange
        BuildingType type = BuildingType.HALLS; // Using actual enum constants
        Vector2Int initialPosition = new Vector2Int(0, 0);
        Building building = new Building(type, initialPosition);

        // Act
        building.setPosition(null);

        // Assert
        assertNull(building.getPosition(), "Building position should be set to null.");
    }

    /**
     * Tests that the Building constructor throws a NullPointerException when type is null.
     * This assumes that your constructor should not accept a null type.
     */
    @Test
    public void testConstructorWithNullType() {
        // Arrange
        Vector2Int position = new Vector2Int(10, 20);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new Building(null, position);
        }, "Constructor should throw NullPointerException when type is null.");
    }

    /**
     * Tests that the Building constructor throws a NullPointerException when position is null.
     * This assumes that your constructor should not accept a null position.
     */
    @Test
    public void testConstructorWithNullPosition() {
        // Arrange
        BuildingType type = BuildingType.ROAD; // Using actual enum constants

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            new Building(type, null);
        }, "Constructor should throw NullPointerException when position is null.");
    }
}
