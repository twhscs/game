package io.github.twhscs.game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextStyle;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;

/**
 * A UI element containing a dynamic text label.
 * @author Robert
 *
 */
public class DialogueUIElement implements Drawable {
  /**
   * The font for the text.
   */
  private final Font font = new Font();
  /**
   * The text object containing style and content.
   */
  private final Text message = new Text();
  
  private final Text name = new Text();
  
  private Sprite portrait;
  
  private RectangleShape background;
  
  private boolean visible = false;
  
  private Vector2i screenResolution;
  
  private final int padding = 20;
  
  private final RectangleShape portraitOutline = new RectangleShape(new Vector2f(100, 100));
  
  private final int wrapLength = 44;
  
  private final int maxLines = 5;
  
  public DialogueUIElement(Vector2i r) {
    // Try to load the font
    try {
      font.loadFromStream(getClass().getClassLoader().getResourceAsStream("kenpixel.ttf"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    screenResolution = r;
    message.setCharacterSize(12);
    message.setColor(Color.WHITE);
    message.setFont(font);
    name.setCharacterSize(14);
    name.setColor(Color.WHITE);
    name.setFont(font);
    background = new RectangleShape(new Vector2f(screenResolution.x - padding, 140));
    background.setPosition((screenResolution.x - background.getLocalBounds().width) / 2, 
        (screenResolution.y - background.getLocalBounds().height) - (padding / 2));
    background.setFillColor(Color.BLUE);
    background.setOutlineThickness(2);
    background.setOutlineColor(Color.WHITE);
    portraitOutline.setOutlineColor(Color.WHITE);
    portraitOutline.setOutlineThickness(2);
    portraitOutline.setPosition(background.getGlobalBounds().left + padding, 
        background.getGlobalBounds().top + padding);
    name.setStyle(TextStyle.UNDERLINED);
  }
  
  public void setPortrait(Sprite p) {
    portrait = p;
    portrait.setPosition(background.getGlobalBounds().left + padding, 
        background.getGlobalBounds().top + padding);
  }
  
  public void setText(String s) {
    message.setString(wrapText(s));
    message.setPosition(portrait.getGlobalBounds().width + (padding * 2), 
        portrait.getGlobalBounds().top + padding);
  }
  
  public void draw(RenderTarget target, RenderStates states) {
    background.draw(target, states);
    portraitOutline.draw(target, states);
    portrait.draw(target, states);
    name.draw(target, states);
    message.draw(target, states);
  }
  
  public void hide() {
    visible = false;
  }
  
  public void show() {
    visible = true;
  }
  
  public boolean isVisible() {
    return visible;
  }
  
  public void setName(String n) {
    name.setString(n);
    name.setPosition((screenResolution.x - name.getLocalBounds().width) / 2, 
        background.getGlobalBounds().top - (padding / 2) + name.getLocalBounds().height);
  }
  
  public String wrapText(String t) {
    int lines = 0;
    String[] words = t.split(" ");
    String newString = "";
    int characterCounter = 0;
    for (int i = 0; i < words.length; i++) {
      if (lines <= maxLines) {
        int l = words[i].length();
        if (characterCounter + l <= wrapLength) {
          newString += words[i] + " ";
          characterCounter += l;
        } else {
          newString += "\n" + words[i] + " ";
          characterCounter = 0;
          lines++;
        }
      }
    }
    return newString;
  }
}
