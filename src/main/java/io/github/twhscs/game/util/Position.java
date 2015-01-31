package io.github.twhscs.game.util;

import org.jsfml.system.Vector2f;

/**
 * Represents a 2D location on a grid.
 */
public class Position {
    private Vector2f position;

    /**
     * Constructs a {@link io.github.twhscs.game.util.Position} at the specified x and y coordinates.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public Position(float x, float y) {
        position = new Vector2f(x, y);
    }

    /**
     * Constructs a {@link io.github.twhscs.game.util.Position} from a {@link org.jsfml.system.Vector2f}
     *
     * @param position the {@link org.jsfml.system.Vector2f} to create a {@link io.github.twhscs.game.util.Position} from.
     */
    public Position(Vector2f position) {
        this.position = position;
    }

    /**
     * Gets the {@link io.github.twhscs.game.util.Position} as a {@link org.jsfml.system.Vector2f}
     *
     * @return the {@link io.github.twhscs.game.util.Position} as a {@link org.jsfml.system.Vector2f}
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * Sets the {@link io.github.twhscs.game.util.Position} from a {@link org.jsfml.system.Vector2f}
     *
     * @param position the {@link org.jsfml.system.Vector2f} used to update the {@link io.github.twhscs.game.util.Position}
     */
    public void setPosition(Vector2f position) {
        this.position = position;
    }

    /**
     * Gets the x coordinate of the {@link io.github.twhscs.game.util.Position}
     *
     * @return the x coordinate of the {@link io.github.twhscs.game.util.Position}
     */
    public float getX() {
        return position.x;
    }

    /**
     * Sets the x coordinate of the {@link io.github.twhscs.game.util.Position}
     *
     * @param x the new x coordinate of the {@link Position}
     */
    public void setX(float x) {
        position = new Vector2f(x, position.y);
    }

    /**
     * Gets the y coordinate of the {@link io.github.twhscs.game.util.Position}
     *
     * @return the y coordinate of the {@link io.github.twhscs.game.util.Position}
     */
    public float getY() {
        return position.y;
    }

    /**
     * Sets the y coordinate of the {@link io.github.twhscs.game.util.Position}
     *
     * @param y the new y coordinate of the {@link io.github.twhscs.game.util.Position}
     */
    public void setY(float y) {
        position = new Vector2f(position.x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "position=" + position +
                '}';
    }

    /**
     * Gets a new {@link io.github.twhscs.game.util.Position} in the specified {@link io.github.twhscs.game.util.Direction} relative to the current {@link io.github.twhscs.game.util.Position}
     *
     * @param direction The {@link io.github.twhscs.game.util.Direction} of the new {@link io.github.twhscs.game.util.Position} relative to the current {@link io.github.twhscs.game.util.Position}
     * @return The new {@link io.github.twhscs.game.util.Direction}
     */
    public Position getRelativePosition(Direction direction) {
        switch (direction) {
            case NORTH:
                return new Position(position.x, position.y - 1);
            case SOUTH:
                return new Position(position.x, position.y + 1);
            case WEST:
                return new Position(position.x - 1, position.y);
            case EAST:
                return new Position(position.x + 1, position.y);
            case NORTH_WEST:
                return new Position(position.x - 1, position.y - 1);
            case NORTH_EAST:
                return new Position(position.x + 1, position.y - 1);
            case SOUTH_WEST:
                return new Position(position.x - 1, position.y + 1);
            case SOUTH_EAST:
                return new Position(position.x + 1, position.y + 1);
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position1 = (Position) o;

        if (!position.equals(position1.position)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }
}
