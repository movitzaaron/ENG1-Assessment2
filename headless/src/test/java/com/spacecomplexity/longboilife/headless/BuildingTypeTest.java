package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the BuildingType enum.
 *   TEST REF : 3
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BuildingTypeTest extends AbstractHeadlessGdxTest {

    @BeforeAll
    public void setUp() {
        // Initialize any required setup for textures or mocks if necessary
    }

    @AfterAll
    public void tearDown() {
        // Dispose all BuildingType textures to clean up after tests
        for (BuildingType type : BuildingType.values()) {
            type.dispose();
        }
    }

    @Test
    @DisplayName("Test BuildingType enum constants and their properties")
    public void testBuildingTypeProperties() {
        // Test GREGGS
        BuildingType greggs = BuildingType.GREGGS;
        assertEquals("Greggs", greggs.getDisplayName(), "GREGGS display name should be 'Greggs'");
        assertEquals("buildings/greggs.png", greggs.getTexturePath(), "GREGGS texture path mismatch");
        assertEquals(new Vector2Int(3, 3), greggs.getSize(), "GREGGS size mismatch");
        assertEquals(BuildingCategory.FOOD, greggs.getCategory(), "GREGGS category should be FOOD");
        assertEquals(5000, greggs.getCost(), "GREGGS cost mismatch");

        // Test LIBRARY
        BuildingType library = BuildingType.LIBRARY;
        assertEquals("Library", library.getDisplayName(), "LIBRARY display name should be 'Library'");
        assertEquals("buildings/library.png", library.getTexturePath(), "LIBRARY texture path mismatch");
        assertEquals(new Vector2Int(4, 4), library.getSize(), "LIBRARY size mismatch");
        assertEquals(BuildingCategory.EDUCATIONAL, library.getCategory(), "LIBRARY category should be EDUCATIONAL");
        assertEquals(200000, library.getCost(), "LIBRARY cost mismatch");

        // Test GYM
        BuildingType gym = BuildingType.GYM;
        assertEquals("Gym", gym.getDisplayName(), "GYM display name should be 'Gym'");
        assertEquals("buildings/gym.png", gym.getTexturePath(), "GYM texture path mismatch");
        assertEquals(new Vector2Int(4, 3), gym.getSize(), "GYM size mismatch");
        assertEquals(BuildingCategory.RECREATIONAL, gym.getCategory(), "GYM category should be RECREATIONAL");
        assertEquals(80000, gym.getCost(), "GYM cost mismatch");

        // Test HALLS
        BuildingType halls = BuildingType.HALLS;
        assertEquals("Halls", halls.getDisplayName(), "HALLS display name should be 'Halls'");
        assertEquals("buildings/halls.png", halls.getTexturePath(), "HALLS texture path mismatch");
        assertEquals(new Vector2Int(4, 4), halls.getSize(), "HALLS size mismatch");
        assertEquals(BuildingCategory.ACCOMMODATION, halls.getCategory(), "HALLS category should be ACCOMMODATION");
        assertEquals(12000, halls.getCost(), "HALLS cost mismatch");

        // Test ROAD
        BuildingType road = BuildingType.ROAD;
        assertEquals("Road", road.getDisplayName(), "ROAD display name should be 'Road'");
        assertEquals("buildings/roads/straight.png", road.getTexturePath(), "ROAD texture path mismatch");
        assertEquals(new Vector2Int(1, 1), road.getSize(), "ROAD size mismatch");
        assertEquals(BuildingCategory.PATHWAY, road.getCategory(), "ROAD category should be PATHWAY");
        assertEquals(100, road.getCost(), "ROAD cost mismatch");
    }

    @Test
    @DisplayName("Test getBuildingsOfType method for each BuildingCategory")
    public void testGetBuildingsOfType() {
        // Test for PATHWAY
        BuildingType[] pathwayBuildings = BuildingType.getBuildingsOfType(BuildingCategory.PATHWAY);
        assertEquals(1, pathwayBuildings.length, "There should be 1 PATHWAY building type");
        assertEquals(BuildingType.ROAD, pathwayBuildings[0], "PATHWAY building should be ROAD");

        // Test for ACCOMMODATION
        BuildingType[] accommodationBuildings = BuildingType.getBuildingsOfType(BuildingCategory.ACCOMMODATION);
        assertEquals(1, accommodationBuildings.length, "There should be 1 ACCOMMODATION building type");
        assertEquals(BuildingType.HALLS, accommodationBuildings[0], "ACCOMMODATION building should be HALLS");

        // Test for EDUCATIONAL
        BuildingType[] educationalBuildings = BuildingType.getBuildingsOfType(BuildingCategory.EDUCATIONAL);
        assertEquals(1, educationalBuildings.length, "There should be 1 EDUCATIONAL building type");
        assertEquals(BuildingType.LIBRARY, educationalBuildings[0], "EDUCATIONAL building should be LIBRARY");

        // Test for FOOD
        BuildingType[] foodBuildings = BuildingType.getBuildingsOfType(BuildingCategory.FOOD);
        assertEquals(1, foodBuildings.length, "There should be 1 FOOD building type");
        assertEquals(BuildingType.GREGGS, foodBuildings[0], "FOOD building should be GREGGS");

        // Test for RECREATIONAL
        BuildingType[] recreationalBuildings = BuildingType.getBuildingsOfType(BuildingCategory.RECREATIONAL);
        assertEquals(1, recreationalBuildings.length, "There should be 1 RECREATIONAL building type");
        assertEquals(BuildingType.GYM, recreationalBuildings[0], "RECREATIONAL building should be GYM");
    }

    @Test
    @DisplayName("Test getTexture method initializes textures correctly")
    public void testGetTexture() {
        for (BuildingType type : BuildingType.values()) {
            // Mock the Texture class to prevent actual texture loading
            Texture mockTexture = mock(Texture.class);
            type.setTexture(mockTexture);

            // Retrieve the texture and verify it's the mock
            Texture texture = type.getTexture();
            assertNotNull(texture, type.getDisplayName() + " texture should not be null");
            assertEquals(mockTexture, texture, "Texture should be the mocked instance");

            // Optionally, verify that the texture's constructor was called with the correct path
            // Note: This requires using spies or more advanced Mockito features
        }
    }

    @Test
    @DisplayName("Test dispose method disposes textures correctly")
    public void testDispose() {
        for (BuildingType type : BuildingType.values()) {
            // Create a mock Texture
            Texture mockTexture = mock(Texture.class);
            type.setTexture(mockTexture);

            // Ensure dispose has not been called yet
            verify(mockTexture, never()).dispose();

            // Call dispose on BuildingType
            type.dispose();

            // Verify that dispose was called on the mock Texture
            verify(mockTexture, times(1)).dispose();

            // Ensure that the texture reference is set to null after disposal
            assertNull(getTextureField(type), "Texture should be set to null after dispose");
        }
    }

    /**
     * Helper method to access the private 'texture' field of BuildingType enum via reflection.
     * This is necessary to verify that the texture has been set to null after disposal.
     *
     * @param type The BuildingType enum constant.
     * @return The value of the 'texture' field.
     */
    private Texture getTextureField(BuildingType type) {
        try {
            java.lang.reflect.Field textureField = BuildingType.class.getDeclaredField("texture");
            textureField.setAccessible(true);
            return (Texture) textureField.get(type);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to access texture field: " + e.getMessage());
            return null;
        }
    }
}
