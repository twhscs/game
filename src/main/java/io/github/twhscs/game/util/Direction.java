package io.github.twhscs.game.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumeration of the eight cardinal and ordinal directions.
 */
public enum Direction {
    NORTH, SOUTH, WEST, EAST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST;

    private static List<Direction> CARDINALS = Arrays.asList(NORTH, SOUTH, EAST, WEST);

    /**
     * Gets the opposite of a direction.
     *
     * @param direction the {@link Direction} to get the opposite of.
     * @return the opposite {@link Direction}
     */
    public static Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            case EAST:
                return WEST;
            case NORTH_WEST:
                return SOUTH_EAST;
            case NORTH_EAST:
                return SOUTH_WEST;
            case SOUTH_WEST:
                return NORTH_EAST;
            case SOUTH_EAST:
                return NORTH_WEST;
            default:
                return null;
        }
    }

    /**
     * Gets a random direction.
     * @return a random direction.
     */
    public static Direction getRandomDirection() {
        return Direction.values()[(int)(Math.random() * 8)];
    }

    /**
     * Gets a random cardinal direction.
     * @return a random cardinal direction.
     */
    public static Direction getRandomCardinalDirection() {
        return CARDINALS.get((int)(Math.random() * CARDINALS.size()));
    }
}
