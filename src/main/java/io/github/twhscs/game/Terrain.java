package io.github.twhscs.game;

import org.jsfml.system.Vector2f;

class Terrain {
    
    /*
    See: http://gameprogrammingpatterns.com/flyweight.html
     */

    /**
     * A grass tile.
     */
    public final static Terrain GRASS = new Terrain(true, new Vector2f(0, 352), true, false);

    /**
     * A water tile.
     */
    public final static Terrain WATER = new Terrain(false, new Vector2f(864, 160), false, true);

    /**
     * A sand tile.
     */
    public final static Terrain SAND = new Terrain(true, new Vector2f(576, 352), true, false);

    /**
     * A snow tile.
     */
    public final static Terrain SNOW = new Terrain(true, new Vector2f(576, 544), true, false);

    private final boolean traversable;
    private final Vector2f textureCoordinates;
    private final boolean randomized;
    private final boolean animated;

    /**
     * Constructs a new {@link io.github.twhscs.game.Terrain} tile with the specified settings.
     *
     * @param traversable        {@code true} if the tile can be crossed, {@code false} if not.
     * @param textureCoordinates the coordinates of the tile's texture.
     * @param randomized         {@code true} if the tile is to be randomly flipped and rotated, {@code false} if not.
     * @param animated           {@code true} if the tile is animated, {@code false} if not.
     */
    public Terrain(boolean traversable, Vector2f textureCoordinates, boolean randomized, boolean animated) {
        this.traversable = traversable;
        this.textureCoordinates = textureCoordinates;
        this.randomized = randomized;
        this.animated = animated;
    }

    /**
     * Checks if the tile is animated.
     *
     * @return {@code true} if the tile is animated.
     */
    public boolean isAnimated() {
        return animated;
    }

    /**
     * Checks if the tile can be crossed.
     *
     * @return {@code true} if the tile can be crossed.
     */
    public boolean isTraversable() {
        return traversable;
    }

    /**
     * Gets the coordinates of the tile's texture.
     *
     * @return the coordinates of the tile's texture.
     */
    public Vector2f getTextureCoordinates() {
        return textureCoordinates;
    }

    /**
     * Checks if the tile is randomly flipped and rotated.
     *
     * @return {@code true} if the tile is randomly flipped and rotated.
     */
    public boolean isRandomized() {
        return randomized;
    }

    @Override
    public String toString() {
        return "Terrain{" +
               "traversable=" + traversable +
               ", textureCoordinates=" + textureCoordinates +
               ", randomized=" + randomized +
               ", animated=" + animated +
               '}';
    }

}
