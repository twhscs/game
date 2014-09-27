package io.github.twhscs.game.util;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

import java.io.IOException;

/**
 * Sprite resource loader and container.
 * @author Robert
 *
 */
public class ImageResource extends Resource {
  private final Image image = new Image();
  
  private final String directorySubfolder = "";
  
  private final String fileFormat = ".png";
  
  public ImageResource(String filename) {
    try {
      image.loadFromStream(getStream(directorySubfolder + filename + fileFormat));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public Image getImage() {
    return image;
  }
  
  public Texture getTexture() {
    Texture t = new Texture();
    try {
      t.loadFromImage(image);
    } catch (TextureCreationException e) {
      e.printStackTrace();
    }
    return t;
  }
}
