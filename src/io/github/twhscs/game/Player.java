package io.github.twhscs.game;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.audio.SoundSource;
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

/**
 * The player (user) playing the game.
 * @author Robert
 *
 */
public class Player implements Drawable {
  private Location playerLoc;
  private Texture playerSpritesheetTexture = new Texture();
  private Sprite playerSprite = new Sprite();
  private Map currentMap;
  private final Vector2i playerSize = new Vector2i(32, 48);
  private PlayerAction currentAction = PlayerAction.NONE;
  private Location newPlayerLoc;
  private int frameCounter = 0;
  private final float animationSpeed = 15.f;
  private int animationFrame = 0;
  private SoundBuffer cannotMoveBuffer = new SoundBuffer();
  private Sound cannotMove = new Sound();
  private Vector2f tempPosition = new Vector2f(0, 0); // DIRTY HACK
  public static float health = 100f;
  
  public Player() {
    playerLoc = new Location(0, 0);
    try {
      playerSpritesheetTexture.loadFromFile(Paths.get("resources/player.png"));
      cannotMoveBuffer.loadFromFile(Paths.get("resources/stuck.wav"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    playerSprite = new Sprite(playerSpritesheetTexture);
    cannotMove.setBuffer(cannotMoveBuffer);
  }
  
  public void changeMap(Map m) {
    currentMap = m;
  }
  
  public Map getMap() {
    return currentMap;
  }
  
  public void move(Direction d) {
    if (currentAction == PlayerAction.NONE) {
      Location newLoc = playerLoc.getRelativeLocation(d);
      playerLoc.setDirection(d);
      if (currentMap.isValidLocation(newLoc)) {
        currentAction = PlayerAction.MOVING;
        newPlayerLoc = newLoc;
      } else if (cannotMove.getStatus() == SoundSource.Status.STOPPED) {
        cannotMove.play();
      }
    }
  }
  
  public void resetLocation() {
    playerLoc = new Location(0, 0);
  }
  
  public IntRect getTextureCoords() {
    IntRect textureCoordsRect = new IntRect(0, 0, 0, 0);
    switch(playerLoc.getDirection()) {
      case NORTH:
        textureCoordsRect = new IntRect(0 + (animationFrame * 32), 144, playerSize.x, playerSize.y);
        break;
      case SOUTH:
        textureCoordsRect = new IntRect(0 + (animationFrame * 32), 0, playerSize.x, playerSize.y);
        break;
      case EAST:
        textureCoordsRect = new IntRect(0 + (animationFrame * 32), 96, playerSize.x, playerSize.y);
        break;
      case WEST:
        textureCoordsRect = new IntRect(0 + (animationFrame * 32), 48, playerSize.x, playerSize.y);
        break;
    }
    return textureCoordsRect;
  }
  
  public void update() {
    // this is a dirty hack and should be fixed asap
    tempPosition = new Vector2f(0, 0);
    Vector2i currentPlayerPosition = playerLoc.getPosition();
    if (currentAction == PlayerAction.MOVING) {
      if (frameCounter >= animationSpeed) {
        frameCounter = 0;
        currentAction = PlayerAction.NONE;
        playerLoc = newPlayerLoc;
        newPlayerLoc = null;
        Vector2i newPlayerPosition = playerLoc.getPosition();
        tempPosition = new Vector2f(newPlayerPosition.x, newPlayerPosition.y);
        animationFrame = 0;
      } else {
        float additionX = 0.0f;
        float additionY = 0.0f;
        switch(playerLoc.getDirection()) {
          case NORTH:
            additionY = -(1.0f / animationSpeed) * (frameCounter);
            break;
          case SOUTH:
            additionY = (1.0f / animationSpeed) * (frameCounter);
            break;
          case EAST:
            additionX = (1.0f / animationSpeed) * (frameCounter);
            break;
          case WEST:
            additionX = -(1.0f / animationSpeed) * (frameCounter);
            break;
        }
        float change = Math.abs(additionX + additionY);
        if (change >= 0.f && change < .25f) {
          animationFrame = 0;
        } else if (change >= .25f && change < .5f) {
          animationFrame = 1;
        } else if (change >= .5f && change < .75f) {
          animationFrame = 2;
        } else if (change >= .75f && change <= 1.0f) {
          animationFrame = 3;
        }
        tempPosition = new Vector2f(currentPlayerPosition.x + additionX, currentPlayerPosition.y + additionY);
      }
      frameCounter++;
    } else {
      tempPosition = new Vector2f(currentPlayerPosition.x, currentPlayerPosition.y);
    }
  }
  
  public void draw(RenderTarget target, RenderStates states) {
    playerSprite.setPosition(
        new Vector2f(tempPosition.x * playerSize.x, 
            (tempPosition.y * playerSize.x) - (playerSize.y - playerSize.x)));
    
    playerSprite.setTextureRect(getTextureCoords());
    RenderStates newStates = new RenderStates(playerSpritesheetTexture);
    playerSprite.draw(target, newStates);
  }
}
