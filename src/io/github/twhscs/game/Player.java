package io.github.twhscs.game;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;

public class Player implements Drawable {
  private Location playerLoc;
  private Texture playerSpritesheetTexture = new Texture();
  private Sprite playerSprite = new Sprite();
  private Map currentMap;
  private final Vector2i playerSize = new Vector2i(32, 48);
  private int animationCounter = 0;
  private int animationFrame = 0;
  private boolean animated = false;
  
  public Player() {
    playerLoc = new Location(0, 0);
    try {
      playerSpritesheetTexture.loadFromFile(Paths.get("resources/player.png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    playerSprite = new Sprite(playerSpritesheetTexture);
  }
  
  public void changeMap(Map m) {
    currentMap = m;
  }
  
  public Map getMap() {
    return currentMap;
  }
  
  public void move(Direction d) {
    Location newLoc = playerLoc.getRelativeLocation(d);
    if(currentMap.isValidLocation(newLoc)) {
      animated = true;
      playerLoc = newLoc;
      playerLoc.setDirection(d);
    }
  }
  
  public void resetLocation() {
    playerLoc = new Location(0, 0);
  }
  
  public IntRect getTextureCoords() {
    IntRect textureCoordsRect = new IntRect(0, 0, 0, 0);
    int animationAddition = animationFrame * 32;
    switch(playerLoc.getDirection()) {
      case NORTH:
        textureCoordsRect = new IntRect(0 + animationAddition, 144, playerSize.x, playerSize.y);
        break;
      case SOUTH:
        textureCoordsRect = new IntRect(0 + animationAddition, 0, playerSize.x, playerSize.y);
        break;
      case EAST:
        textureCoordsRect = new IntRect(0 + animationAddition, 96, playerSize.x, playerSize.y);
        break;
      case WEST:
        textureCoordsRect = new IntRect(0 + animationAddition, 48, playerSize.x, playerSize.y);
        break;
    }
    return textureCoordsRect;
  }
  
  public void draw(RenderTarget target, RenderStates states) {
    if (animated) {
      animationCounter++;
      if (animationCounter >= 10) {
        animationCounter = 0;
        animationFrame++;
        if (animationFrame >= 4) {
          animationFrame = 0;
          animated = false;
        }
      }
    }
    Vector2i playerPosition = playerLoc.getPosition();
    Direction playerDirection = playerLoc.getDirection();
    playerSprite.setPosition(
        new Vector2f((playerPosition.x * playerSize.x), (playerPosition.y * playerSize.x) - (playerSize.y - playerSize.x)));
    playerSprite.setTextureRect(getTextureCoords());
    RenderStates newStates = new RenderStates(playerSpritesheetTexture);
    playerSprite.draw(target, newStates);
  }
}
