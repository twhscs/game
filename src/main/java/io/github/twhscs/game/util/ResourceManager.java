package io.github.twhscs.game.util;

import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles loading and accessing images, textures, fonts and sound buffers.
 */
public class ResourceManager {
    private final Map<String, Image> IMAGE_MAP;
    private final Map<String, Texture> TEXTURE_MAP;
    private final Map<String, Font> FONT_MAP;
    private final Map<String, SoundBuffer> SOUND_BUFFER_MAP;
    private final String ROOT_PACKAGE;
    private final String IMAGE_DIRECTORY;
    private final String TEXTURE_DIRECTORY;
    private final String FONT_DIRECTORY;
    private final String SOUND_BUFFER_DIRECTORY;
    private final String IMAGE_EXTENSION;
    private final String TEXTURE_EXTENSION;
    private final String FONT_EXTENSION;
    private final String SOUND_BUFFER_EXTENSION;

    /**
     * Constructs a resource manager with the specified directories and extensions.
     *
     * @param ROOT_PACKAGE           the root package containing the resources.
     * @param IMAGE_DIRECTORY        the directory containing images.
     * @param IMAGE_EXTENSION        the filename extension for images.
     * @param TEXTURE_DIRECTORY      the directory containing textures.
     * @param TEXTURE_EXTENSION      the filename extension for textures.
     * @param FONT_DIRECTORY         the directory containing fonts.
     * @param FONT_EXTENSION         the filename extension for fonts
     * @param SOUND_BUFFER_DIRECTORY the directory containing sound buffers.
     * @param SOUND_BUFFER_EXTENSION the filename extension for sound buffers.
     */
    public ResourceManager(String ROOT_PACKAGE, String IMAGE_DIRECTORY, String IMAGE_EXTENSION, String TEXTURE_DIRECTORY, String TEXTURE_EXTENSION, String FONT_DIRECTORY, String FONT_EXTENSION, String SOUND_BUFFER_DIRECTORY, String SOUND_BUFFER_EXTENSION) {
        IMAGE_MAP = new HashMap<String, Image>();
        TEXTURE_MAP = new HashMap<String, Texture>();
        FONT_MAP = new HashMap<String, Font>();
        SOUND_BUFFER_MAP = new HashMap<String, SoundBuffer>();
        this.ROOT_PACKAGE = "/" + ROOT_PACKAGE.replace(".", "/");
        this.IMAGE_DIRECTORY = IMAGE_DIRECTORY;
        this.TEXTURE_DIRECTORY = TEXTURE_DIRECTORY;
        this.FONT_DIRECTORY = FONT_DIRECTORY;
        this.SOUND_BUFFER_DIRECTORY = SOUND_BUFFER_DIRECTORY;
        this.IMAGE_EXTENSION = IMAGE_EXTENSION;
        this.TEXTURE_EXTENSION = TEXTURE_EXTENSION;
        this.FONT_EXTENSION = FONT_EXTENSION;
        this.SOUND_BUFFER_EXTENSION = SOUND_BUFFER_EXTENSION;
    }

    /**
     * Gets the {@link java.io.InputStream} for the specified resource inside the jar.
     *
     * @param name the name of the resource.
     * @return the {@link java.io.InputStream} of the specified resource.
     */
    public static InputStream loadResource(String name) {
        return ResourceManager.class.getResourceAsStream(name);
    }

    /**
     * Creates an {@link org.jsfml.graphics.Image} for each item in the array and adds it to the map.
     *
     * @param imageNames an array of image names to load without directories or extensions.
     */
    public void loadImages(String[] imageNames) {
        for (String imageName : imageNames) {
            final Image IMAGE = new Image();
            try {
                IMAGE.loadFromStream(loadResource(ROOT_PACKAGE + "/" + IMAGE_DIRECTORY + "/" + imageName + "." + IMAGE_EXTENSION));
                IMAGE_MAP.put(imageName, IMAGE);
            } catch (IOException e) {
                System.out.println("Error loading image " + imageName);
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a {@link org.jsfml.graphics.Texture} for each item in the array and adds it to the map.
     *
     * @param textureNames an array of texture names to load without directories or extensions.
     */
    public void loadTextures(String[] textureNames) {
        for (String textureName : textureNames) {
            final Texture TEXTURE = new Texture();
            try {
                TEXTURE.loadFromStream(loadResource(ROOT_PACKAGE + "/" + TEXTURE_DIRECTORY + "/" + textureName + "." + TEXTURE_EXTENSION));
                TEXTURE_MAP.put(textureName, TEXTURE);
            } catch (IOException e) {
                System.out.println("Error loading texture " + textureName);
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a {@link org.jsfml.graphics.Font} for each item in the array and adds it to the map.
     *
     * @param fontNames an array of font names to load without directories or extensions.
     */
    public void loadFonts(String[] fontNames) {
        for (String fontName : fontNames) {
            final Font FONT = new Font();
            try {
                FONT.loadFromStream(loadResource(ROOT_PACKAGE + "/" + FONT_DIRECTORY + "/" + fontName + "." + FONT_EXTENSION));
                FONT_MAP.put(fontName, FONT);
            } catch (IOException e) {
                System.out.println("Error loading font " + fontName);
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a {@link org.jsfml.audio.SoundBuffer} for each item in the array and adds it to the map.
     *
     * @param soundBufferNames an array of font names to load without directories or extensions.
     */
    public void loadSoundBuffers(String[] soundBufferNames) {
        for (String soundBufferName : soundBufferNames) {
            final SoundBuffer SOUND_BUFFER = new SoundBuffer();
            try {
                SOUND_BUFFER.loadFromStream(loadResource(ROOT_PACKAGE + "/" + SOUND_BUFFER_DIRECTORY + "/" + soundBufferName + "." + SOUND_BUFFER_EXTENSION));
                SOUND_BUFFER_MAP.put(soundBufferName, SOUND_BUFFER);
            } catch (IOException e) {
                System.out.println("Error loading sound buffer " + soundBufferName);
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the specified {@link org.jsfml.graphics.Image} from the map.
     *
     * @param imageName the name of the image to retrieve.
     * @return the specified {@link org.jsfml.graphics.Image}
     */
    public Image getImage(String imageName) {
        return IMAGE_MAP.get(imageName);
    }

    /**
     * Gets the specified {@link org.jsfml.graphics.Texture} from the map.
     *
     * @param textureName the name of the texture to retrieve.
     * @return the specified {@link org.jsfml.graphics.Texture}
     */
    public Texture getTexture(String textureName) {
        return TEXTURE_MAP.get(textureName);
    }

    /**
     * Gets the specified {@link org.jsfml.graphics.Font} from the map.
     *
     * @param fontName the name of the font to retrieve.
     * @return the specified {@link org.jsfml.graphics.Font}
     */
    public Font getFont(String fontName) {
        return FONT_MAP.get(fontName);
    }

    /**
     * Gets the specified {@link org.jsfml.audio.SoundBuffer} from the map.
     *
     * @param soundBufferName the name of the sound buffer to retrieve.l
     * @return the specified {@link org.jsfml.audio.SoundBuffer}
     */
    public SoundBuffer getSoundBuffer(String soundBufferName) {
        return SOUND_BUFFER_MAP.get(soundBufferName);
    }

    @Override
    public String toString() {
        return "ResourceManager{" +
                "IMAGE_MAP=" + IMAGE_MAP +
                ", TEXTURE_MAP=" + TEXTURE_MAP +
                ", FONT_MAP=" + FONT_MAP +
                ", SOUND_BUFFER_MAP=" + SOUND_BUFFER_MAP +
                ", ROOT_PACKAGE='" + ROOT_PACKAGE + '\'' +
                ", IMAGE_DIRECTORY='" + IMAGE_DIRECTORY + '\'' +
                ", TEXTURE_DIRECTORY='" + TEXTURE_DIRECTORY + '\'' +
                ", FONT_DIRECTORY='" + FONT_DIRECTORY + '\'' +
                ", SOUND_BUFFER_DIRECTORY='" + SOUND_BUFFER_DIRECTORY + '\'' +
                ", IMAGE_EXTENSION='" + IMAGE_EXTENSION + '\'' +
                ", TEXTURE_EXTENSION='" + TEXTURE_EXTENSION + '\'' +
                ", FONT_EXTENSION='" + FONT_EXTENSION + '\'' +
                ", SOUND_BUFFER_EXTENSION='" + SOUND_BUFFER_EXTENSION + '\'' +
                '}';
    }
}
