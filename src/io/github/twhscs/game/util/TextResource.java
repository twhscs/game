package io.github.twhscs.game.util;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.Text;

import java.io.IOException;

/**
 * Sprite resource loader and container.
 * @author Robert
 *
 */
public class TextResource extends Resource {
  private final Text text = new Text();
  
  private final Font font = new Font();
  
  private final String directorySubfolder = "fonts/";
  
  private final String fileFormat = ".ttf";
  
  public TextResource(String filename) {
    try {
      font.loadFromStream(getStream(directorySubfolder + filename + fileFormat));
    } catch (IOException e) {
      e.printStackTrace();
    }
    text.setFont(font);
  }
  
  public Text getText() {
    return text;
  }
}
