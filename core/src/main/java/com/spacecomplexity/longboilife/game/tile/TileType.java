package com.spacecomplexity.longboilife.game.tile;

import com.badlogic.gdx.graphics.Texture;

/**
 * Contains a list of all tile types, including there default data.
 */
public enum TileType {
    GRASS("tiles/grass.png", true),
    WATER("tiles/water.png", false),
    ;

    private Texture texture;
    private final String texturePath;
    private final boolean isNaturallyBuildable;

    /**
     * Constructor to create a {@link TileType} with specified attributes.
     *
     * @param texturePath          the texture path representing the tile.
     * @param isNaturallyBuildable a boolean indicating if the tile can be built on.
     */
    TileType(String texturePath, boolean isNaturallyBuildable) {
        this.texturePath = texturePath;
        this.isNaturallyBuildable = isNaturallyBuildable;
    }

    public Texture getTexture() {
        if (texture == null) {
            this.texture = new Texture(texturePath);
        }
        return texture;
    }

    /**
     * Get whether this type of tile is allowed to be built on.
     *
     * @return whether this type of tile is allowed to be built on.
     */
    public boolean isNaturallyBuildable() {
        return isNaturallyBuildable;
    }

    /**
     * Will dispose of the all loaded assets (like textures).
     * <p>
     * <strong>Warning:</strong> Once disposed of no attributes will be able to be reloaded, which could lead to undefined behaviour.
     */
    public void dispose() {
        texture.dispose();
    }
}
