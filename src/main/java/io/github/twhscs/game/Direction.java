package io.github.twhscs.game;

/**
 * Enumeration of the four cardinal directions.
 */
public enum Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    /**
     * Gets the opposite of a direction.
     *
     * @param direction the {@link io.github.twhscs.game.Direction} to get the opposite of.
     * @return the opposite {@link io.github.twhscs.game.Direction}
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
            default:
                return null;
        }
    }
}
