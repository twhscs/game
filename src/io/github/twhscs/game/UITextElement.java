package io.github.twhscs.game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextStyle;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * GUI elements.
 * @author Robert
 *
 */
public class UITextElement implements Drawable {
  private Font font = new Font();
  private Text text = new Text();
  private String value;
  
  public UITextElement(InterfacePosition p, Color c, int size) {
    this(p, c, size, TextStyle.REGULAR);
  }
  
  public UITextElement(InterfacePosition p, Color c, int size, int style) {
    try {
      font.loadFromFile(Paths.get("resources/kenpixel.ttf"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    
    text = new Text("", font, size);
    text.setColor(c);
    text.setStyle(style);
    setPosition(p);
  }
  
  public void updateString(String s) {
    text.setString(s);
  }
  
  public void setPosition(InterfacePosition p) {
    switch(p) {
      case TOP_LEFT:
        text.setPosition(0, 0);
        break;
    }
  }
  
  public void draw(RenderTarget target, RenderStates states) {
    text.draw(target, states);
  }
}
