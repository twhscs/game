package io.github.twhscs.game;

import org.jsfml.system.Vector2f;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MapTest {
    private Map map;

    @Before
    public void setUp() throws Exception {
        map = new Map(100, 100, 32, 0.5f, 25, null, null);
    }

    @Test
    public void testIsValidPosition() throws Exception {
        assertEquals("Position (-1, -1) should be invalid.", false, map.isValidPosition(new Vector2f(-1.0f, -1.0f)));
        assertEquals("Position (200, 200) should be invalid.", false, map.isValidPosition(new Vector2f(200.0f, 200.0f)));
        assertEquals("Position (50, 50) should be valid.", true, map.isValidPosition(new Vector2f(50.0f, 50.0f)));
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("Class Map should have a toString() method.", map.toString());
    }
}