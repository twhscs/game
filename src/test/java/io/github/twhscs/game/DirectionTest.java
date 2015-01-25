package io.github.twhscs.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {

    @Test
    public void testGetOppositeDirection() throws Exception {
        assertEquals("The opposite of north should be south.", Direction.SOUTH, Direction.getOppositeDirection(Direction.NORTH));
        assertEquals("The opposite of south should be north.", Direction.NORTH, Direction.getOppositeDirection(Direction.SOUTH));
        assertEquals("The opposite of west should be east.", Direction.EAST, Direction.getOppositeDirection(Direction.WEST));
        assertEquals("The opposite of east should be west.", Direction.WEST, Direction.getOppositeDirection(Direction.EAST));
    }
}