package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.tile.Tile;
import com.spacecomplexity.longboilife.game.tile.TileType;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import com.spacecomplexiy.longboilife.headless.HeadlessLauncher;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tile class.
 *   TEST REF : 8
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TileTest extends AbstractHeadlessGdxTest{

    @BeforeAll
    public void setUp() {}

    @AfterAll
    public void tearDown() {}

    @Test
    public void testTileConstructorAndGetters() {
        // Arrange
        TileType type = TileType.GRASS;

        // Act
        Tile tile = new Tile(type);

        // Assert
        assertEquals(type, tile.getType(), "Tile type should match the constructor argument.");
        assertEquals(tile.isBuildable(), type.isNaturallyBuildable() ,"Tile buildable status should match type.");
        assertNull(tile.getBuildingRef(), "Tile building ref should be initialised to null.");
    }

    @Test
    public void testSetBuildable(){
        // Arrange
        TileType type = TileType.WATER;
        Tile tile = new Tile(type);
        Tile tile2 = new Tile(type);

        // Act
        tile.setBuildable(true);

        // Assert
        assertTrue(tile.isBuildable(),"Tile buildable status should be true.");
        assertFalse(tile2.isBuildable(),"Tile buildable status should not be true.");
    }

    @Test
    public void testSetBuildingRef(){
        // Arrange
        TileType tileType = TileType.GRASS;
        Tile tile = new Tile(tileType);
        BuildingType buildingType = BuildingType.GREGGS;
        Vector2Int initialPosition = new Vector2Int(5, 5);
        Building building = new Building(buildingType, initialPosition);

        // Act
        tile.setBuildingRef(building);

        // Assert
        assertEquals(tile.getBuildingRef(), building, "Building ref should be set appropriately.");
    }
}
