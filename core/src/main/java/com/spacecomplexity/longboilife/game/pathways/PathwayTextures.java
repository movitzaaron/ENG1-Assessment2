package com.spacecomplexity.longboilife.game.pathways;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.game.building.BuildingType;

import java.util.HashMap;

/**
 * Class to get the texture for a specific pathway building and texture type.
 * Implements lazy loading to load textures only when needed.
 */
public class PathwayTextures {
    /**
     * Type of pathway connections.
     */
    public enum Type {
        STRAIGHT,
        CORNER,
        TJUNC,
        CROSS,
    }

    /**
     * Map containing the file paths for each texture.
     * This map is initialized with all possible BuildingType and Type combinations.
     */
    private static final HashMap<BuildingType, HashMap<Type, String>> texturePaths = new HashMap<BuildingType, HashMap<Type, String>>() {{
        put(BuildingType.ROAD, new HashMap<Type, String>() {{
            put(Type.STRAIGHT, "buildings/roads/straight.png");
            put(Type.CORNER, "buildings/roads/corner.png");
            put(Type.TJUNC, "buildings/roads/3-way.png");
            put(Type.CROSS, "buildings/roads/4-way.png");
        }});
        // Add more BuildingTypes and their corresponding texture paths here
    }};

    /**
     * Map containing the loaded textures.
     * Textures are loaded and stored here on demand.
     */
    private static final HashMap<BuildingType, HashMap<Type, Texture>> textureList = new HashMap<BuildingType, HashMap<Type, Texture>>();

    /**
     * Retrieves the texture for the specified BuildingType and Type.
     * Loads the texture if it hasn't been loaded yet.
     *
     * @param buildingType       the building type (e.g., ROAD).
     * @param pathwayTextureType the type of pathway texture (e.g., STRAIGHT).
     * @return the corresponding Texture object.
     * @throws IllegalArgumentException if the texture path is not defined.
     */
    public static Texture getTexture(BuildingType buildingType, Type pathwayTextureType) {
        // Retrieve the inner map for the specified BuildingType
        HashMap<Type, Texture> typeMap = textureList.get(buildingType);
        if (buildingType != BuildingType.ROAD) {
            throw new IllegalArgumentException("No inner map for BuildingType: " + buildingType);
        }

        if (typeMap == null) {
            typeMap = new HashMap<Type, Texture>();
            textureList.put(buildingType, typeMap);
        }

        // Retrieve the Texture for the specified Type
        Texture texture = typeMap.get(pathwayTextureType);
        if (texture == null) {
            String path = getTexturePath(buildingType, pathwayTextureType);
            if (path == null) {
                throw new IllegalArgumentException("No texture path defined for BuildingType: " + buildingType + ", Type: " + pathwayTextureType);
            }
            texture = new Texture(path);
            typeMap.put(pathwayTextureType, texture);
        }

        return texture;
    }

    /**
     * Retrieves the file path for the specified BuildingType and Type.
     *
     * @param buildingType the building type.
     * @param type         the pathway texture type.
     * @return the file path as a String, or null if not defined.
     */
    private static String getTexturePath(BuildingType buildingType, Type type) {
        HashMap<Type, String> typeMap = texturePaths.get(buildingType);
        if (typeMap != null) {
            return typeMap.get(type);
        }
        return null;
    }

    /**
     * Disposes of all loaded textures.
     * Call this method when textures are no longer needed to free up resources.
     */
    public static void dispose() {
        for (HashMap<Type, Texture> typeMap : textureList.values()) {
            for (Texture texture : typeMap.values()) {
                texture.dispose();
            }
        }
        textureList.clear();
    }
}
