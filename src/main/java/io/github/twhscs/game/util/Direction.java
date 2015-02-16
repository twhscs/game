package io.github.twhscs.game.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enumeration of the eight cardinal and ordinal directions.
 */
public enum Direction {

    NORTH, SOUTH, WEST, EAST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST;

    private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    /**
     * Returns the opposite direction of the given direction.
     *
     * @param direction the {@link io.github.twhscs.game.util.Direction} to get the opposite of.
     * @return the opposite {@link io.github.twhscs.game.util.Direction}
     */
    public static Direction oppositeDirection(Direction direction) {
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
     * Returns one of the eight cardinal and ordinal directions randomly.
     *
     * @return a random {@link io.github.twhscs.game.util.Direction}
     */
    public static Direction randomDirection() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    /**
     * Returns one of the four cardinal directions randomly.
     *
     * @return a random cardinal {@link io.github.twhscs.game.util.Direction}
     */
    public static Direction randomCardinalDirection() {
        return VALUES.get(RANDOM.nextInt(4));
    }

    /**
     * Returns one of the four ordinal directions randomly.
     *
     * @return a random ordinal {@link io.github.twhscs.game.util.Direction}
     */
    public static Direction randomOrdinalDirection() {
        return VALUES.get(RANDOM.nextInt(4) + 4);
    }

}
