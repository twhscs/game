package io.github.twhscs.game.util;

import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// TODO: to singleton or not to singleton?

/**
 * Handles the loading and retrieval of images, textures, fonts and sound buffers.
 */
public final class ResourceManager {

    private static final String ROOT_PACKAGE = "io/github/twhscs/game";
    private static final String IMAGE_DIRECTORY = "images";
    private static final String TEXTURE_DIRECTORY = "textures";
    private static final String FONT_DIRECTORY = "fonts";
    private static final String SOUND_BUFFER_DIRECTORY = "sound_buffers";
    private static final String IMAGE_EXTENSION = "png";
    private static final String TEXTURE_EXTENSION = "png";
    private static final String FONT_EXTENSION = "ttf";
    private static final String SOUND_BUFFER_EXTENSION = "wav";
    private static final Map<String, Image> IMAGE_MAP = new HashMap<String, Image>();
    private static final Map<String, Texture> TEXTURE_MAP = new HashMap<String, Texture>();
    private static final Map<String, Font> FONT_MAP = new HashMap<String, Font>();
    private static final Map<String, SoundBuffer> SOUND_BUFFER_MAP = new HashMap<String, SoundBuffer>();
    private static boolean imagesLoaded = false;
    private static boolean texturesLoaded = false;
    private static boolean fontsLoaded = false;
    private static boolean soundBuffersLoaded = false;

    private static InputStream loadResource(String name) {
        return ResourceManager.class.getResourceAsStream("/" + ROOT_PACKAGE + "/" + name);
    }

    // TODO: remove repetition

    /**
     * Creates and stores an {@link org.jsfml.graphics.Image} for each item in the array.
     *
     * @param images the names of the images to load.
     */
    public static void loadImages(String[] images) {
        if (!imagesLoaded) {
            for (String imageName : images) {
                final Image image = new Image();
                try {
                    image.loadFromStream(loadResource(IMAGE_DIRECTORY + "/" + imageName + "." + IMAGE_EXTENSION));
                    IMAGE_MAP.put(imageName, image);
                } catch (IOException e) {
                    System.out.println("Error loading image: " + imageName);
                    e.printStackTrace();
                }
            }
            imagesLoaded = true;
        }
    }

    /**
     * Creates and stores a {@link org.jsfml.graphics.Texture} for each item in the array.
     *
     * @param textures the names of the textures to load.
     */
    public static void loadTextures(String[] textures) {
        if (!texturesLoaded) {
            for (String textureName : textures) {
                final Texture texture = new Texture();
                try {
                    texture.loadFromStream(
                            loadResource(TEXTURE_DIRECTORY + "/" + textureName + "." + TEXTURE_EXTENSION));
                    TEXTURE_MAP.put(textureName, texture);
                } catch (IOException e) {
                    System.out.println("Error loading texture: " + textureName);
                    e.printStackTrace();
                }
            }
            texturesLoaded = true;
        }
    }

    /**
     * Creates and stores a {@link org.jsfml.graphics.Font} for each item in the array.
     *
     * @param fonts the names of the fonts to load.
     */
    public static void loadFonts(String[] fonts) {
        if (!fontsLoaded) {
            for (String fontName : fonts) {
                final Font font = new Font();
                try {
                    font.loadFromStream(loadResource(FONT_DIRECTORY + "/" + fontName + "." + FONT_EXTENSION));
                    FONT_MAP.put(fontName, font);
                } catch (IOException e) {
                    System.out.println("Error loading font: " + fontName);
                    e.printStackTrace();
                }
            }
            fontsLoaded = true;
        }
    }

    /**
     * Creates and stores a {@link org.jsfml.audio.SoundBuffer} for each item in the array.
     *
     * @param soundBuffers the names of the sound buffers to load.
     */
    public static void loadSoundBuffers(String[] soundBuffers) {
        if (!soundBuffersLoaded) {
            for (String soundBufferName : soundBuffers) {
                final SoundBuffer soundBuffer = new SoundBuffer();
                try {
                    soundBuffer.loadFromStream(loadResource(
                            SOUND_BUFFER_DIRECTORY + "/" + soundBufferName + "." + SOUND_BUFFER_EXTENSION));
                    SOUND_BUFFER_MAP.put(soundBufferName, soundBuffer);
                } catch (IOException e) {
                    System.out.println("Error loading sound buffer: " + soundBuffer);
                    e.printStackTrace();
                }
            }
            soundBuffersLoaded = true;
        }
    }

    /**
     * Gets the specified {@link org.jsfml.graphics.Image}
     *
     * @param imageName the name of the {@link org.jsfml.graphics.Image}
     * @return the specified {@link org.jsfml.graphics.Image} if it exists, otherwise returns {@code null}
     */
    public static Image getImage(String imageName) {
        if (imagesLoaded && IMAGE_MAP.containsKey(imageName)) {
            return IMAGE_MAP.get(imageName);
        } else {
            return null;
        }
    }

    /**
     * Gets the specified {@link org.jsfml.graphics.Texture}
     *
     * @param textureName the name of the {@link org.jsfml.graphics.Texture}
     * @return the specified {@link org.jsfml.graphics.Texture} if it exists, otherwise returns {@code null}
     */
    public static Texture getTexture(String textureName) {
        if (texturesLoaded && TEXTURE_MAP.containsKey(textureName)) {
            return TEXTURE_MAP.get(textureName);
        } else {
            return null;
        }
    }

    /**
     * Gets the specified {@link org.jsfml.graphics.Font}
     *
     * @param fontName the name of the {@link org.jsfml.graphics.Font}
     * @return the specified {@link org.jsfml.graphics.Font} if it exists, otherwise returns {@code null}
     */
    public static Font getFont(String fontName) {
        if (fontsLoaded && FONT_MAP.containsKey(fontName)) {
            return FONT_MAP.get(fontName);
        } else {
            return null;
        }
    }

    /**
     * Gets the specified {@link org.jsfml.audio.SoundBuffer}
     *
     * @param soundBufferName the name of the {@link org.jsfml.audio.SoundBuffer}
     * @return the specified {@link org.jsfml.audio.SoundBuffer} if it exists, otherwise returns {@code null}
     */
    public static SoundBuffer getSoundBuffer(String soundBufferName) {
        if (soundBuffersLoaded && SOUND_BUFFER_MAP.containsKey(soundBufferName)) {
            return SOUND_BUFFER_MAP.get(soundBufferName);
        } else {
            return null;
        }
    }

}
