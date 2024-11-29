package com.spacecomplexity.longboilife.game.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;

import java.util.stream.Stream;

/**
 * Contains a list of all buildings, including there default data.
 */
public enum BuildingType {
    GREGGS("Greggs", "buildings/greggs.png", new Vector2Int(2, 2), BuildingCategory.FOOD, 5000),
    LIBRARY("Library", "buildings/library.png", new Vector2Int(4, 4), BuildingCategory.EDUCATIONAL, 200000),
    GYM("Gym", "buildings/gym.png", new Vector2Int(4, 3), BuildingCategory.RECREATIONAL, 80000),
    HALLS("Halls", "buildings/halls.png", new Vector2Int(3, 3), BuildingCategory.ACCOMMODATION, 12000),
    ROAD("Road", "buildings/roads/straight.png", new Vector2Int(1, 1), BuildingCategory.PATHWAY, 100),
    ;

    private final String displayName;
    private Texture texture;
    private final String texturePath;
    private final Vector2Int size;
    private final BuildingCategory category;
    private final float cost;

    /**
     * Create a {@link BuildingType} with specified attributes.
     *
     * @param displayName the name to display when selecting this building.
     * @param texturePath     the texture path representing the building.
     * @param size        the size of the building (in tiles).
     * @param category    the category of the building.
     * @param cost        the cost to place the building.
     */
    BuildingType(String displayName, String texturePath, Vector2Int size, BuildingCategory category, float cost) {
        this.displayName = displayName;
        this.texturePath = texturePath;
        this.size = size;
        this.category = category;
        this.cost = cost;
    }

    /**
     * Lazily initializes and returns the Texture.
     *
     * @return the Texture associated with this BuildingType.
     */
    public Texture getTexture() {
        if (texture == null) {
            texture = new Texture(texturePath);
        }
        return texture;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Vector2Int getSize() {
        return size;
    }

    public BuildingCategory getCategory() {
        return category;
    }

    public float getCost() {
        return cost;
    }

    public static BuildingType[] getBuildingsOfType(BuildingCategory category) {
        return Stream.of(BuildingType.values())
            .filter(buildingType -> buildingType.getCategory().equals(category))
            .toArray(BuildingType[]::new);

    }

    /**
     * Will dispose of the all loaded assets (like textures).
     * <p>
     * <strong>Warning:</strong> Once disposed of no attributes will be able to be reloaded, which could lead to undefined behaviour.
     */
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
