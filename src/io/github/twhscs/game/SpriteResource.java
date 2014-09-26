package io.github.twhscs.game;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;

/**
 * Sprite resource loader and container.
 * @author Robert
 *
 */
public class SpriteResource extends Resource {
  private Sprite sprite;
  
  private Texture texture;
  
  private final String directorySubfolder = "images/";
  
  private final String fileFormat = ".png";
  
  public SpriteResource(String filename) {
    try {
      texture.loadFromStream(getStream(directorySubfolder + filename + fileFormat));
    } catch (IOException e) {
      e.printStackTrace();
    }
    sprite.setTexture(texture);
  }
}
