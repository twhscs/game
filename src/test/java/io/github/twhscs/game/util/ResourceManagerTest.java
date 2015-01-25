package io.github.twhscs.game.util;

import org.junit.Before;
import org.junit.Test;
//TODO: create tests
public class ResourceManagerTest {
    private ResourceManager resourceManager;

    @Before
    public void setUp() throws Exception {
        resourceManager = new ResourceManager("images", "png", "textures", "png", "fonts", "ttf", "sound_buffers", "wav");
    }

    @Test
    public void testLoadResource() throws Exception {

    }

    @Test
    public void testLoadImages() throws Exception {

    }

    @Test
    public void testLoadTextures() throws Exception {

    }

    @Test
    public void testLoadFonts() throws Exception {

    }

    @Test
    public void testLoadSoundBuffers() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }
}