package io.github.twhscs.game.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextStyle;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class MainMenu implements Drawable {
  private final Font font = new Font(); // Create a new font
  
  private final Color backColor = new Color(Color.BLACK, 230); // Default background color
  private final Color buttonColor = new Color(Color.CYAN, 120); // Default button color
  private final Color outlineColor = new Color(Color.CYAN, 200); // Default outline color
  
  /**
   * List of button Names (Used for organization)
   */
  private final String[] buttonNames = {"newGame", "loadGame", "options", "credits", "exit"};
  /**
   * Default size of the buttons
   */
  private final Vector2f buttonSize = new Vector2f(150,30);
  /**
   * The background rectangle
   */
  private RectangleShape backRect;
  /**
   * HashMap used to hold the buttons by their names
   */
  private HashMap<String, RectangleShape> buttonList = new HashMap<String, RectangleShape>();
  /**
   * ArrayList to hold the text objects for the sake of efficiency
   */
  private ArrayList<Text> textList = new ArrayList<Text>();
  /**
   * The current button that is selected
   */
  private String selected;
  /**
   * The boolean value that tells whether or not the menu is visible
   */
  private boolean visible = false;
  /**
   * Constructor class
   * @param dimen The dimension of the window
   */
  public MainMenu(Vector2i dimen) {
    /**
     * Load the font
     */
    try {
      font.loadFromStream(getClass().getClassLoader().getResourceAsStream("fonts/kenpixel.ttf"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    /**
     * Create the background rectangle and set its default color
     */
    backRect = new RectangleShape(new Vector2f(dimen.x, dimen.y));
    backRect.setFillColor(backColor);
    /**
     * Create all the buttons and add them to the HashMap "buttonList"
     */
    buttonList.put("newGame", new RectangleShape(buttonSize));
    buttonList.put("loadGame", new RectangleShape(buttonSize));
    buttonList.put("options", new RectangleShape(buttonSize));
    buttonList.put("credits", new RectangleShape(buttonSize));
    buttonList.put("exit", new RectangleShape(buttonSize));
    /**
     * Set the selected button to the first button.
     */
    selected = buttonNames[0];
    /**
     * Create a loop through the buttonNames array
     */
    for (int i = 0; i < buttonNames.length; i++) {
      /**
       * Get the current button in the array
       */
      RectangleShape curButton = buttonList.get(buttonNames[i]);
      /**
       * Set the button's color to the default color
       */
      curButton.setFillColor(buttonColor);
      /**
       * Set the position of the button depending on its order in the array
       */
      curButton.setPosition(dimen.x/2 - buttonSize.x/2, dimen.y/2 + (buttonSize.y + 20) * (i-2));
      /**
       * Set the outline color of the button to the default
       */
      curButton.setOutlineColor(outlineColor);
      /**
       * Set the outline thickness of the button to:
       * - 2f if it is not selected
       * - 4f if it is selected
       */
      curButton.setOutlineThickness(buttonList.get(selected).equals(curButton) ? 4f : 2f);
      /**
       * Create the button text and set its properties
       */
      Text name = new Text();
      name.setCharacterSize(14);
      name.setColor(Color.WHITE);
      name.setFont(font);
      name.setStyle(TextStyle.BOLD);
      /**
       * Set the text's string to the current button name
       */
      name.setString(buttonNames[i]);
      /**
       * Position the text in the middle of the button
       */
      name.setPosition(
          curButton.getPosition().x + curButton.getLocalBounds().width/2 - name.getLocalBounds().width/2,
          curButton.getPosition().y + 7);
      /**
       * Add the text to the textList ArrayList
       */
      textList.add(name);
    }
  }
  /**
   * Set the menu to visible or invisible
   * @param on If true the menu will be visible; if false the menu will be invisible.
   */
  public void setVisible(boolean on) {
    visible = on;
  }
  /**
   * Returns whether the menu is visible or not
   * @return Whether the menu is visible or not
   */
  public boolean isVisible() {
    return visible;
  }
  /**
   * If the selected button is not the first button, the selected button will move up
   */
  public void goUp() {
    if (visible) {
      for (int i = 0; i < buttonNames.length; i++) {
        if (buttonNames[i] == selected && i > 0) {
          selected = buttonNames[i-1];
          buttonList.get(buttonNames[i]).setOutlineThickness(2f);
          buttonList.get(buttonNames[i-1]).setOutlineThickness(4f);
          break;
        }
      }
    }
  }
  /**
   * If the selected button is not the first button, the selected button will move down
   */
  public void goDown() {
    if (visible) {
      for (int i = 0; i < buttonNames.length; i++) {
        if (buttonNames[i] == selected && i < buttonNames.length - 1) {
          selected = buttonNames[i+1];
          buttonList.get(buttonNames[i]).setOutlineThickness(2f);
          buttonList.get(buttonNames[i+1]).setOutlineThickness(4f);
          break;
        }
      }
    }
  }
  /**
   * Draw the objects
   */
  public void draw(RenderTarget target, RenderStates states) {
    backRect.draw(target, states);
    for (Object value : buttonList.values()) {
      ((Drawable) value).draw(target, states);
    }
    for (Text t : textList) {
      t.draw(target, states);
    }
  }
  
}
