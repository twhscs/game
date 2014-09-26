package io.github.twhscs.game.ui;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.TextStyle;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;

import io.github.twhscs.game.util.TextResource;

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
  private final TextResource message = new TextResource("kenpixel");
  /**
   * The speaker's name.
   */
  private final TextResource name = new TextResource("kenpixel");
  /**
   * The speaker's portrait.
   */
  private Sprite portrait;
  /**
   * The background of the dialogue UI.
   */
  private RectangleShape background;
  /**
   * Whether or not the UI is visible.
   */
  private boolean visible = false;
  /**
   * The window's resolution.
   */
  private Vector2i screenResolution;
  /**
   * Amount of spacing between the edges of the UI and it's contents.
   */
  private final int padding = 20;
  /**
   * A rectangle outlining the portrait.
   */
  private final RectangleShape portraitOutline = new RectangleShape(new Vector2f(100, 100));
  /**
   * Maximum characters per line of text to display.
   */
  private final int wrapLength = 44;
  /**
   * Maximum number of lines of text to display.
   */
  private final int maxLines = 5;
  
  /**
   * Create a new dialogue UI.
   * @param r The window's resolution.
   */
  public DialogueUIElement(Vector2i r) {
    // Try to load the font
    try {
      font.loadFromStream(getClass().getClassLoader().getResourceAsStream("fonts/kenpixel.ttf"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    screenResolution = r; // Set the resolution.
    // Create the dialogue text
    message.getText().setCharacterSize(12);
    message.getText().setColor(Color.WHITE);
    message.getText().setFont(font);
    // Create the name
    name.getText().setCharacterSize(14);
    name.getText().setColor(Color.WHITE);
    name.getText().setFont(font);
    name.getText().setStyle(TextStyle.UNDERLINED);
    // Create the background rectangle
    background = new RectangleShape(new Vector2f(screenResolution.x - padding, 140));
    background.setPosition((screenResolution.x - background.getLocalBounds().width) / 2, 
        (screenResolution.y - background.getLocalBounds().height) - (padding / 2));
    background.setFillColor(Color.BLUE);
    background.setOutlineThickness(2);
    background.setOutlineColor(Color.WHITE);
    // Create the portrait outline
    portraitOutline.setOutlineColor(Color.WHITE);
    portraitOutline.setOutlineThickness(2);
    portraitOutline.setPosition(background.getGlobalBounds().left + padding, 
        background.getGlobalBounds().top + padding);
  }
  
  /**
   * Update the UI portrait.
   * @param p The new portrait.
   */
  public void setPortrait(Sprite p) {
    portrait = p; // Update the portrait sprite
    // Position the portrait
    portrait.setPosition(background.getGlobalBounds().left + padding, 
        background.getGlobalBounds().top + padding);
  }
  
  /**
   * Update the current dialogue text.
   * @param s The new text.
   */
  public void setText(String s) {
    message.getText().setString(wrapText(s)); // Update the text
    // Position the text
    message.getText().setPosition(portrait.getGlobalBounds().width + (padding * 2), 
        portrait.getGlobalBounds().top + padding);
  }
  
  /**
   * Draw the UI to the window.
   */
  public void draw(RenderTarget target, RenderStates states) {
    background.draw(target, states);
    portraitOutline.draw(target, states);
    portrait.draw(target, states);
    name.getText().draw(target, states);
    message.getText().draw(target, states);
  }
  
  /**
   * Hide the UI from displaying.
   */
  public void hide() {
    visible = false;
  }
  
  /**
   * Show the UI for drawing.
   */
  public void show() {
    visible = true;
  }
  
  /**
   * Determine if the UI is visible.
   * @return Whether or not the UI is visible.
   */
  public boolean isVisible() {
    return visible;
  }
  
  /**
   * Update the name to display.
   * @param n The new name.
   */
  public void setName(String n) {
    name.getText().setString(n); // Update the name
    // Position the name
    name.getText().setPosition((screenResolution.x - name.getText().getLocalBounds().width) / 2, 
        background.getGlobalBounds().top - (padding / 2) + name.getText().getLocalBounds().height);
  }
  
  /**
   * Automatically insert new lines into a long string so it will properly fit.
   * @param t The long string to wrap.
   * @return The wrapped string.
   */
  public String wrapText(String t) {
    int lines = 0; // Keep track of inserted new lines
    String[] words = t.split(" "); // Split the string into words
    String newString = ""; // Create a blank new string to add to
    int characterCounter = 0; // Count the characters
    for (int i = 0; i < words.length; i++) { // Loop through each word
      if (lines <= maxLines) { // Continue if less than max lines
        int l = words[i].length(); // Get the current word length
        if (characterCounter + l <= wrapLength) { // If the word is short enough
          newString += words[i] + " "; // Add it to the new string
          characterCounter += l; // Count the characters
        } else {
          newString += "\n" + words[i] + " "; // Insert a new line and then add the word
          characterCounter = 0; // reset the character counter
          lines++; // Update the new line count
        }
      }
    }
    return newString; // Return the newly formatted string
  }
}
