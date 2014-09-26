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
  private final Font font = new Font();
  
  private final Color backColor = new Color(Color.BLACK, 230);
  private final Color buttonColor = new Color(Color.CYAN, 120);
  private final Color outlineColor = new Color(Color.CYAN, 200);
  
  private final String[] buttonNames = {"newGame", "loadGame", "options", "credits", "exit"};
  
  private final Vector2f buttonSize = new Vector2f(150,30);
  
  private RectangleShape backRect;
  
  private HashMap<String, RectangleShape> buttonList = new HashMap<String, RectangleShape>();
  
  private ArrayList<Text> textList = new ArrayList<Text>();
  
  private String selected;
  
  private boolean visible = false;
  
  public MainMenu(Vector2i dimen) {
    try {
      font.loadFromStream(getClass().getClassLoader().getResourceAsStream("fonts/kenpixel.ttf"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    backRect = new RectangleShape(new Vector2f(dimen.x, dimen.y));
    backRect.setFillColor(backColor);
    buttonList.put("newGame", new RectangleShape(buttonSize));
    buttonList.put("loadGame", new RectangleShape(buttonSize));
    buttonList.put("options", new RectangleShape(buttonSize));
    buttonList.put("credits", new RectangleShape(buttonSize));
    buttonList.put("exit", new RectangleShape(buttonSize));
    selected = "newGame";
    for (int i = 0; i < buttonNames.length; i++) {
      RectangleShape curButton = buttonList.get(buttonNames[i]);
      curButton.setFillColor(buttonColor);
      curButton.setPosition(dimen.x/2 - buttonSize.x/2, dimen.y/2 + (buttonSize.y + 20) * (i-2));
      curButton.setOutlineColor(outlineColor);
      curButton.setOutlineThickness(buttonList.get(selected).equals(curButton) ? 4f : 2f);
      Text name = new Text();
      name.setCharacterSize(14);
      name.setColor(Color.WHITE);
      name.setFont(font);
      name.setStyle(TextStyle.BOLD);
      name.setString(buttonNames[i]);
      name.setPosition(
          curButton.getPosition().x + curButton.getLocalBounds().width/2 - name.getLocalBounds().width/2,
          curButton.getPosition().y + 7);
      textList.add(name);
    }
  }
  
  public void setVisible(boolean on) {
    visible = on;
  }
  
  public boolean isVisible() {
    return visible;
  }
  
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
