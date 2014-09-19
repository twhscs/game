package io.github.twhscs.game;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 
 * @author Robert
 *
 */

public class Player {
  
  /**
   * player location
   */
  private Location loc = new Location(0, 0);
  
  /**
   * player sprite
   */
  private Sprite sprite;
  
  /**
   * create a new player
   * load texture and create sprite
   */
  public Player() {
    Texture playerTex = new Texture();
    try {
      playerTex.loadFromFile(Paths.get("resources/player.png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    sprite = new Sprite(playerTex);
  }
  
  public void move(Direction dir) {
    loc.setDirection(dir);
    IntRect texRect = new IntRect(0, 0, 0, 0);
    switch(dir) {
      case NORTH:
        texRect = new IntRect(0, 144, 32, 48);
        loc.setPosition(loc.getX(), loc.getY() - 1);
        break;
      case SOUTH:
        texRect = new IntRect(0, 0, 32, 48);
        loc.setPosition(loc.getX(), loc.getY() + 1);
        break;
      case WEST:
        texRect = new IntRect(0, 48, 32, 48);
        loc.setPosition(loc.getX() - 1, loc.getY());
        break;
      case EAST:
        texRect = new IntRect(0, 96, 32, 48);
        loc.setPosition(loc.getX() + 1, loc.getY());
        break;
    }
    sprite.setTextureRect(texRect);
    sprite.setPosition(new Vector2f(loc.getX() * 32, loc.getY() * 32));
  }
  
  public Sprite getSprite() {
    return sprite;
  }
}
