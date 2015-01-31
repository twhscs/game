package io.github.twhscs.game.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PositionTest {
    private Position position;

    @Before
    public void setUp() throws Exception {
        position = new Position(2, 3);
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("Class Position should have a toString() method.", position.toString());
    }

    @Test
    public void testGetRelativePosition() throws Exception {
        assertEquals("Position (2, 2) should be north of (2, 3).", new Position(2, 2), position.getRelativePosition(Direction.NORTH, 1.0f));
        assertEquals("Position (2, 4) should be south of (2, 3).", new Position(2, 4), position.getRelativePosition(Direction.SOUTH, 1.0f));
        assertEquals("Position (1, 3) should be west of (2, 3).", new Position(1, 3), position.getRelativePosition(Direction.WEST, 1.0f));
        assertEquals("Position (3, 3) should be east of (2, 3).", new Position(3, 3), position.getRelativePosition(Direction.EAST, 1.0f));
        assertEquals("Position (1, 2) should be north west of (2, 3).", new Position(1, 2), position.getRelativePosition(Direction.NORTH_WEST, 1.0f));
        assertEquals("Position (3, 2) should be north east of (2, 3).", new Position(3, 2), position.getRelativePosition(Direction.NORTH_EAST, 1.0f));
        assertEquals("Position (1, 4) should be south west of (2, 3).", new Position(1, 4), position.getRelativePosition(Direction.SOUTH_WEST, 1.0f));
        assertEquals("Position (3, 4) should be south east of (2, 3).", new Position(3, 4), position.getRelativePosition(Direction.SOUTH_EAST, 1.0f));
    }
}