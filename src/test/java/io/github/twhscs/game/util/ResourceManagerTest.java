package io.github.twhscs.game.util;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class ResourceManagerTest {

    @Before
    public void setUp() throws Exception {
        ResourceManager.loadImages(new String[]{"test_image"});
        ResourceManager.loadTextures(new String[]{"test_texture"});
        ResourceManager.loadFonts(new String[]{"test_font"});
        ResourceManager.loadSoundBuffers(new String[]{"test_sound_buffer"});
    }

    @Test
    public void testGetImage() throws Exception {
        assertNotNull("Test image should not be null.", ResourceManager.getImage("test_image"));
    }

    @Test
    public void testGetTexture() throws Exception {
        assertNotNull("Test texture should not be null.", ResourceManager.getTexture("test_texture"));
    }

    @Test
    public void testGetFont() throws Exception {
        assertNotNull("Test font should not be null.", ResourceManager.getFont("test_font"));
    }

    @Test
    public void testGetSoundBuffer() throws Exception {
        assertNotNull("Test sound buffer should not be null.", ResourceManager.getSoundBuffer("test_sound_buffer"));
    }

}