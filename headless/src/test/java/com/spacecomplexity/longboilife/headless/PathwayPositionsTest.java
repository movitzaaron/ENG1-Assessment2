package com.spacecomplexity.longboilife.headless;


import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.pathways.PathwayPositions;
import com.spacecomplexity.longboilife.game.pathways.PathwayTextures;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the PathwayPositions enum.
 *   TEST REF : 7
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PathwayPositionsTest extends AbstractHeadlessGdxTest{

    @BeforeAll
    public void setUp() {}

    @AfterAll
    public void tearDown() {}

    @Test
    public void testConstructorAndGetters() {
        // Test TOP_BOTTOM
        PathwayPositions top_bottom = PathwayPositions.TOP_BOTTOM;
        assertEquals(0, top_bottom.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.STRAIGHT, top_bottom.getTextureType(), "Texture type should be set correctly");

        // Test LEFT_RIGHT
        PathwayPositions left_right = PathwayPositions.LEFT_RIGHT;
        assertEquals(90, left_right.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.STRAIGHT, left_right.getTextureType(), "Texture type should be set correctly");

        // Test LEFT_TOP
        PathwayPositions left_top = PathwayPositions.LEFT_TOP;
        assertEquals(0, left_top.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.CORNER, left_top.getTextureType(), "Texture type should be set correctly");

        // Test BOTTOM_LEFT
        PathwayPositions bottom_left = PathwayPositions.BOTTOM_LEFT;
        assertEquals(90, bottom_left.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.CORNER, bottom_left.getTextureType(), "Texture type should be set correctly");

        // Test RIGHT_BOTTOM
        PathwayPositions right_bottom = PathwayPositions.RIGHT_BOTTOM;
        assertEquals(180, right_bottom.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.CORNER, right_bottom.getTextureType(), "Texture type should be set correctly");

        // Test TOP_RIGHT
        PathwayPositions top_right = PathwayPositions.TOP_RIGHT;
        assertEquals(270, top_right.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.CORNER, top_right.getTextureType(), "Texture type should be set correctly");

        // Test LEFT_TOP_RIGHT
        PathwayPositions left_top_right = PathwayPositions.LEFT_TOP_RIGHT;
        assertEquals(0, left_top_right.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.TJUNC, left_top_right.getTextureType(), "Texture type should be set correctly");

        // Test BOTTOM_LEFT_TOP
        PathwayPositions bottom_left_top = PathwayPositions.BOTTOM_LEFT_TOP;
        assertEquals(90, bottom_left_top.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.TJUNC, bottom_left_top.getTextureType(), "Texture type should be set correctly");

        // Test RIGHT_BOTTOM_LEFT
        PathwayPositions right_bottom_left = PathwayPositions.RIGHT_BOTTOM_LEFT;
        assertEquals(180, right_bottom_left.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.TJUNC, right_bottom_left.getTextureType(), "Texture type should be set correctly");

        // Test TOP_RIGHT_BOTTOM
        PathwayPositions top_right_bottom = PathwayPositions.TOP_RIGHT_BOTTOM;
        assertEquals(270, top_right_bottom.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.TJUNC, top_right_bottom.getTextureType(), "Texture type should be set correctly");

        // Test TOP_LEFT_BOTTOM_RIGHT
        PathwayPositions top_left_bottom_right = PathwayPositions.TOP_LEFT_BOTTOM_RIGHT;
        assertEquals(0, top_left_bottom_right.getRotation(), "Rotation should be set correctly.");
        assertEquals(PathwayTextures.Type.CROSS, top_left_bottom_right.getTextureType(), "Texture type should be set correctly");
    }
}
