package io.github.twhscs.game.ui;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.TextStyle;

import io.github.twhscs.game.util.TextResource;

/**
 * A UI element containing a dynamic text label.
 * @author Robert
 *
 */
public class TextUIElement implements Drawable {

  private final TextResource text = new TextResource("kenpixel");
  
  /**
   * Create a new Text UI Element with the specified options and a default style.
   * @param p The position on the screen to display the label.
   * @param c The color to set the text.
   * @param size The size to render the text.
   */
  public TextUIElement(UIPosition p, Color c, int size) {
    this(p, c, size, TextStyle.REGULAR);
  }
  
  /**
   * Create a new Text UI element with the specified options and style.
   * @param p The position on the screen to display the label.
   * @param c The color to set the text.
   * @param size The size to render the text.
   * @param style The style to apply to the text.
   */
  public TextUIElement(UIPosition p, Color c, int size, int style) {
    text.getText().setCharacterSize(size); // Update the character size
    text.getText().setColor(c); // Change the color
    text.getText().setStyle(style); // Set the desired style
    setPosition(p); // Position the label
  }
  
  /**
   * Update the value of the text.
   * @param s The new value for the text.
   */
  public void updateString(String s) {
    text.getText().setString(s); // Update the text
  }
  
  /**
   * Position the element on the screen.
   * @param p The screen position.
   */
  public void setPosition(UIPosition p) {
    switch(p) {
      case TOP_LEFT:
        text.getText().setPosition(0, 0); // If the position is top left, default to 0, 0
        break;
      default:
        break;
    }
  }
  
  /**
   * Draw the UI element onto the screen.
   */
  public void draw(RenderTarget target, RenderStates states) {
    text.getText().draw(target, states);
  }
}
