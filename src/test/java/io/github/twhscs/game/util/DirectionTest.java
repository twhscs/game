package io.github.twhscs.game.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectionTest {

    @Test
    public void testOppositeDirection() throws Exception {
        assertEquals("The opposite of north should be south.", Direction.SOUTH,
                     Direction.oppositeDirection(Direction.NORTH));
        assertEquals("The opposite of south should be north.", Direction.NORTH,
                     Direction.oppositeDirection(Direction.SOUTH));
        assertEquals("The opposite of west should be east.", Direction.EAST,
                     Direction.oppositeDirection(Direction.WEST));
        assertEquals("The opposite of east should be west.", Direction.WEST,
                     Direction.oppositeDirection(Direction.EAST));
        assertEquals("The opposite of north west should be south east.", Direction.SOUTH_EAST,
                     Direction.oppositeDirection(Direction.NORTH_WEST));
        assertEquals("The opposite of north east should be south west.", Direction.SOUTH_WEST,
                     Direction.oppositeDirection(Direction.NORTH_EAST));
        assertEquals("The opposite of south west should be north east.", Direction.NORTH_EAST,
                     Direction.oppositeDirection(Direction.SOUTH_WEST));
        assertEquals("The opposite of south east should be north west.", Direction.NORTH_WEST,
                     Direction.oppositeDirection(Direction.SOUTH_EAST));
    }

    @Test
    public void testRandomDirection() throws Exception {
        assertTrue("The random direction should be a valid direction.",
                   Arrays.asList(Direction.values()).contains(Direction.randomDirection()));
    }

    @Test
    public void testRandomCardinalDirection() throws Exception {
        List<Direction> cardinalDirections =
                Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);
        assertTrue("The random direction should be a valid cardinal direction.",
                   cardinalDirections.contains(Direction.randomCardinalDirection()));
    }

    @Test
    public void testRandomOrdinalDirection() throws Exception {
        List<Direction> ordinalDirections =
                Arrays.asList(Direction.NORTH_WEST, Direction.NORTH_EAST, Direction.SOUTH_WEST, Direction.SOUTH_EAST);
        assertTrue("The random direction should be a valid ordinal direction.",
                   ordinalDirections.contains(Direction.randomOrdinalDirection()));
    }
    
}