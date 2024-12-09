package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BuildingCategoryTest {

    @Test
    @DisplayName("Test BuildingCategory enum constants and their display names")
    public void testBuildingCategoryDisplayNames() {
        assertEquals("Pathway", BuildingCategory.PATHWAY.getDisplayName(), "PATHWAY display name should be 'Pathway'");
        assertEquals("Accommodation", BuildingCategory.ACCOMMODATION.getDisplayName(), "ACCOMMODATION display name should be 'Accommodation'");
        assertEquals("Educational", BuildingCategory.EDUCATIONAL.getDisplayName(), "EDUCATIONAL display name should be 'Educational'");
        assertEquals("Food", BuildingCategory.FOOD.getDisplayName(), "FOOD display name should be 'Food'");
        assertEquals("Recreational", BuildingCategory.RECREATIONAL.getDisplayName(), "RECREATIONAL display name should be 'Recreational'");
    }

    @Test
    @DisplayName("Test BuildingCategory to ensure all categories are present")
    public void testAllBuildingCategories() {
        BuildingCategory[] expectedCategories = {
            BuildingCategory.PATHWAY,
            BuildingCategory.ACCOMMODATION,
            BuildingCategory.EDUCATIONAL,
            BuildingCategory.FOOD,
            BuildingCategory.RECREATIONAL
        };

        assertArrayEquals(expectedCategories, BuildingCategory.values(), "All BuildingCategory enums should be present");
    }
}
