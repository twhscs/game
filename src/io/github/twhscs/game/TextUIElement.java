package io.github.twhscs.game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextStyle;

import java.io.IOException;

/**
 * A UI element containing a dynamic text label.
 * @author Robert
 *
 */
public class TextUIElement implements Drawable {
  /**
   * The font for the text.
   */
  private final Font font = new Font();
  /**
   * The text object containing style and content.
   */
  private final Text text = new Text();
  
  /**
   * Create a new Text UI Element with the specified options and a default style.
   * @param p The position on the screen to display the label.
   * @param c The color to set the text.
   * @param size The size to render the text.
   */
  public TextUIElement(InterfacePosition p, Color c, int size) {
    this(p, c, size, TextStyle.REGULAR);
  }
  
  /**
   * Create a new Text UI element with the specified options and style.
   * @param p The position on the screen to display the label.
   * @param c The color to set the text.
   * @param size The size to render the text.
   * @param style The style to apply to the text.
   */
  public TextUIElement(InterfacePosition p, Color c, int size, int style) {
    // Try to load the font
    try {
      font.loadFromStream(getClass().getClassLoader().getResourceAsStream("kenpixel.ttf"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    text.setFont(font); // Apply the selected font
    text.setCharacterSize(size); // Update the character size
    text.setColor(c); // Change the color
    text.setStyle(style); // Set the desired style
    setPosition(p); // Position the label
  }
  
  /**
   * Update the value of the text.
   * @param s The new value for the text.
   */
  public void updateString(String s) {
    text.setString(s); // Update the text
  }
  
  /**
   * Position the element on the screen.
   * @param p The screen position.
   */
  public void setPosition(InterfacePosition p) {
    switch(p) {
      case TOP_LEFT:
        text.setPosition(0, 0); // If the position is top left, default to 0, 0
        break;
      default:
        break;
    }
  }
  
  /**
   * Draw the UI element onto the screen.
   */
  public void draw(RenderTarget target, RenderStates states) {
    text.draw(target, states);
  }
}
