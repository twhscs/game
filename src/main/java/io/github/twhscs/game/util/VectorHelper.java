package io.github.twhscs.game.util;

import org.jsfml.system.Vector2f;

/**
 * Provides helper functions for floating point 2D vectors.
 */
public final class VectorHelper {

    /**
     * Returns a new {@link org.jsfml.system.Vector2f} in the specified {@link io.github.twhscs.game.util.Direction}
     * relative to the current {@link org.jsfml.system.Vector2f}
     *
     * @param position  the current {@link org.jsfml.system.Vector2f}
     * @param direction the {@link io.github.twhscs.game.util.Direction} of the new {@link org.jsfml.system.Vector2f}
     *                  relative to the current {@link org.jsfml.system.Vector2f}
     * @param step      the distance between the two vectors.
     * @return the new {@link org.jsfml.system.Vector2f}
     */
    public static Vector2f relativePosition(Vector2f position, Direction direction, float step) {
        switch (direction) {
            case NORTH:
                return Vector2f.sub(position, new Vector2f(0.0f, step));
            case SOUTH:
                return Vector2f.add(position, new Vector2f(0.0f, step));
            case WEST:
                return Vector2f.sub(position, new Vector2f(step, 0.0f));
            case EAST:
                return Vector2f.add(position, new Vector2f(step, 0.0f));
            case NORTH_WEST:
                return Vector2f.sub(position, new Vector2f(step, step));
            case NORTH_EAST:
                return Vector2f.add(position, new Vector2f(step, -step));
            case SOUTH_WEST:
                return Vector2f.add(position, new Vector2f(-step, step));
            case SOUTH_EAST:
                return Vector2f.add(position, new Vector2f(step, step));
            default:
                return null;
        }
    }

    /**
     * Performs {@link java.lang.Math#round} on both the x and y coordinates of the specified
     * {@link org.jsfml.system.Vector2f}
     *
     * @param position the {@link org.jsfml.system.Vector2f} to round.
     * @return the rounded {@link org.jsfml.system.Vector2f}
     */
    public static Vector2f round(Vector2f position) {
        return new Vector2f(Math.round(position.x), Math.round(position.y));
    }

    /**
     * Performs {@link java.lang.Math#ceil} on both the x and y coordinates of the specified
     * {@link org.jsfml.system.Vector2f}
     *
     * @param position the {@link org.jsfml.system.Vector2f} to ceil.
     * @return the ceiled {@link org.jsfml.system.Vector2f}
     */
    public static Vector2f ceil(Vector2f position) {
        return new Vector2f((float) Math.ceil(position.x), (float) Math.ceil(position.y));
    }

    /**
     * Performs {@link java.lang.Math#floor} on both the x and y coordinates of the specified
     * {@link org.jsfml.system.Vector2f}
     *
     * @param position the {@link org.jsfml.system.Vector2f} to floor.
     * @return the floored {@link org.jsfml.system.Vector2f}
     */
    public static Vector2f floor(Vector2f position) {
        return new Vector2f((float) Math.floor(position.x), (float) Math.floor(position.y));
    }

}
