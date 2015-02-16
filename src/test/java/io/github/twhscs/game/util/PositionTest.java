package io.github.twhscs.game.util;

import org.jsfml.system.Vector2f;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {

    @Test
    public void testGetRelativePosition() throws Exception {
        Vector2f position = new Vector2f(2.0f, 3.0f);
        assertEquals("Position (2, 2) should be north of (2, 3).", new Vector2f(2.0f, 2.0f), Position
                .getRelativePosition(position, Direction.NORTH, 1.0f));
        assertEquals("Position (2, 4) should be south of (2, 3).", new Vector2f(2.0f, 4.0f), Position
                .getRelativePosition(position, Direction.SOUTH, 1.0f));
        assertEquals("Position (1, 3) should be west of (2, 3).", new Vector2f(1.0f, 3.0f), Position
                .getRelativePosition(position, Direction.WEST, 1.0f));
        assertEquals("Position (3, 3) should be east of (2, 3).", new Vector2f(3.0f, 3.0f), Position
                .getRelativePosition(position, Direction.EAST, 1.0f));
        assertEquals("Position (1, 2) should be north west of (2, 3).", new Vector2f(1.0f, 2.0f), Position
                .getRelativePosition(position, Direction.NORTH_WEST, 1.0f));
        assertEquals("Position (3, 2) should be north east of (2, 3).", new Vector2f(3.0f, 2.0f), Position
                .getRelativePosition(position, Direction.NORTH_EAST, 1.0f));
        assertEquals("Position (1, 4) should be south west of (2, 3).", new Vector2f(1.0f, 4.0f), Position
                .getRelativePosition(position, Direction.SOUTH_WEST, 1.0f));
        assertEquals("Position (3, 4) should be south east of (2, 3).", new Vector2f(3.0f, 4.0f), Position
                .getRelativePosition(position, Direction.SOUTH_EAST, 1.0f));
        assertEquals("Position (2, 2.5) should be north of (2, 3) with a step of 0.5.", new Vector2f(2.0f, 2.5f),
                     Position.getRelativePosition(position, Direction.NORTH, 0.5f));
        assertEquals("Position (2, 5) should be south of (2, 3) with a step of 2.", new Vector2f(2.0f, 5.0f),
                     Position.getRelativePosition(position, Direction.SOUTH, 2.0f));
    }

    @Test
    public void testRound() throws Exception {
        Vector2f position = new Vector2f(2.4f, 3.9f);
        assertEquals("Position (2.4, 3.9) should round to (2, 4).", new Vector2f(2.0f, 4.0f), Position.round(position));
    }

    @Test
    public void testCeil() throws Exception {
        Vector2f position = new Vector2f(2.1f, 3.9f);
        assertEquals("Position (2.1, 3.9) should ceil to (3, 4).", new Vector2f(3.0f, 4.0f), Position.ceil(position));
    }

    @Test
    public void testFloor() throws Exception {
        Vector2f position = new Vector2f(2.1f, 3.9f);
        assertEquals("Position (2.1, 3.9) should floor to (2, 3).", new Vector2f(2.0f, 3.0f), Position.floor(position));
    }
}