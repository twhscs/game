package io.github.twhscs.game;

import org.jsfml.system.Vector2i;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IslandGeneratorTest {

    private final IslandGenerator islandGenerator = new IslandGenerator(new Vector2i(10, 10), 3);

    @Test
    public void testGenerate() throws Exception {
        Terrain[][] map = islandGenerator.generate();
        assertNotNull("The randomly generated island should not be empty.", map);
        assertEquals("The randomly generated island should be 100 x 100.", new Vector2i(10, 10),
                     new Vector2i(map.length, map[0].length));
    }

}