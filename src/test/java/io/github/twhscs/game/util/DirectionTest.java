package io.github.twhscs.game.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {

    @Test
    public void testGetOppositeDirection() throws Exception {
        assertEquals("The opposite of north should be south.", Direction.SOUTH, Direction.getOppositeDirection(Direction.NORTH));
        assertEquals("The opposite of south should be north.", Direction.NORTH, Direction.getOppositeDirection(Direction.SOUTH));
        assertEquals("The opposite of west should be east.", Direction.EAST, Direction.getOppositeDirection(Direction.WEST));
        assertEquals("The opposite of east should be west.", Direction.WEST, Direction.getOppositeDirection(Direction.EAST));
        assertEquals("The opposite of north west should be south east.", Direction.SOUTH_EAST, Direction.getOppositeDirection(Direction.NORTH_WEST));
        assertEquals("The opposite of north east should be south west.", Direction.SOUTH_WEST, Direction.getOppositeDirection(Direction.NORTH_EAST));
        assertEquals("The opposite of south west should be north east.", Direction.NORTH_EAST, Direction.getOppositeDirection(Direction.SOUTH_WEST));
        assertEquals("The opposite of south east should be north west.", Direction.NORTH_WEST, Direction.getOppositeDirection(Direction.SOUTH_EAST));
    }
}