package io.github.twhscs.game.util;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;

/**
 * Sprite resource loader and container.
 * @author Robert
 *
 */
public class SpriteResource extends Resource {
  private final Sprite sprite = new Sprite();
  
  private final Texture texture = new Texture();
  
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
  
  public Sprite getSprite() {
    return sprite;
  }
}
