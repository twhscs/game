package io.github.twhscs.game.util;

import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResourceManagerTest {
    private ResourceManager resourceManager;

    @Before
    public void setUp() throws Exception {
        resourceManager = new ResourceManager("io.github.twhscs.game", "images", "png", "textures", "png", "fonts",
                                              "ttf", "sound_buffers", "wav");
    }

    @Test
    public void testLoadResource() throws Exception {
        InputStream inputStream = ResourceManager.loadResource("/io/github/twhscs/game/data1.dat");
        int data = inputStream.read();
        String content = "";
        while (data != -1) {
            content += (char) data;
            data = inputStream.read();
        }
        assertEquals("Test data file should contain the word 'test' exactly.", "test", content);
    }

    @Test
    public void testLoadImages() throws Exception {
        String[] imageNames = {"image1"};
        resourceManager.loadImages(imageNames);
        Image image = resourceManager.getImage("image1");
        assertEquals("Image 1 should be 2x2 pixels.", new Vector2i(2, 2), image.getSize());
    }

    @Test
    public void testLoadTextures() throws Exception {
        String[] textureNames = {"texture1"};
        resourceManager.loadTextures(textureNames);
        Texture texture = resourceManager.getTexture("texture1");
        assertEquals("Texture 1 should be 3x3 pixels.", new Vector2i(3, 3), texture.getSize());
    }

    @Test
    public void testLoadFonts() throws Exception {
        String[] fontNames = {"font1", "font2"};
        resourceManager.loadFonts(fontNames);
        Font font1 = resourceManager.getFont("font1");
        Font font2 = resourceManager.getFont("font2");
        assertNotNull("Font 1 should not be null", font1);
        assertNotNull("Font 2 should not be null", font2);
    }

    @Test
    public void testLoadSoundBuffers() throws Exception {
        String[] soundBufferNames = {"sound_buffer1", "sound_buffer2"};
        resourceManager.loadSoundBuffers(soundBufferNames);
        SoundBuffer soundBuffer1 = resourceManager.getSoundBuffer("sound_buffer1");
        SoundBuffer soundBuffer2 = resourceManager.getSoundBuffer("sound_buffer2");
        assertEquals("Sound buffer 1 should have a sample count of 5072", 5072, soundBuffer1.getSampleCount());
        assertEquals("Sound buffer 2 should have a sample count of 7638", 7638, soundBuffer2.getSampleCount());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("Class ResourceManager should have a toString method.", resourceManager.toString());
    }
}