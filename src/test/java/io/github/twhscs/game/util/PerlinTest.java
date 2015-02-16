package io.github.twhscs.game.util;

import org.jsfml.system.Vector2i;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PerlinTest {

    @Test
    public void testNoise() throws Exception {
        float[][] noise = Perlin.noise(new Vector2i(10, 10), 2);
        assertNotNull("Noise should not be null.", noise);
        assertEquals("Noise matrix should be 10 x 10.", new Vector2i(10, 10),
                     new Vector2i(noise.length, noise[0].length));
    }

}