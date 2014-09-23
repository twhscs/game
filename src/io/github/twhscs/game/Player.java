package io.github.twhscs.game;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.audio.SoundSource;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Holds everything pertaining to the player (user) playing the game.
 * @author Robert
 *
 */
public class Player extends Entity implements Drawable {
  /**
   * The texture for the sprite.
   */
  private final Texture playerSpritesheetTexture = new Texture();
  /**
   * The player sprite that supports animation.
   */
  private final AnimatedSprite playerSprite;
  /**
   * The map the player is currently on.
   */
  private Map currentMap;
  /**
   * The action the player is currently performing.
   */
  private PlayerAction currentAction = PlayerAction.NONE;
  /**
   * The buffer that allows the 'stuck' sound to be loaded.
   */
  private final SoundBuffer cannotMoveBuffer = new SoundBuffer();
  /**
   * The object that plays the 'stuck' sound.
   */
  private final Sound cannotMove = new Sound();
  
  /**
   * Creates a new player with a location at x, y.
   * @param x Starting x position.
   * @param y Starting y position.
   */
  public Player(int x, int y) {
    entityLoc = new Location(x, y); // Create a new location at position x, y
    // Try to load sprite texture and 'stuck' sound
    try {
      playerSpritesheetTexture.loadFromFile(Paths.get("resources/player.png"));
      cannotMoveBuffer.loadFromFile(Paths.get("resources/stuck.wav"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    Sprite sprite = new Sprite(); // Create a new regular sprite
    sprite.setTexture(playerSpritesheetTexture); // Set the texture for the sprite
    // Create a new animated sprite from the regular sprite
    playerSprite = new AnimatedSprite(sprite, entityLoc);
    cannotMove.setBuffer(cannotMoveBuffer); // Set the sound object from the buffer (loading)
    
  }
  
  /**
   * Create a new player at (0, 0).
   */
  public Player() {
    this(0, 0);
  }
  
  /**
   * Change the map the player is on.
   * @param m The new map.
   */
  public void changeMap(Map m) {
    currentMap = m;
  }
  
  /**
   * Get the map the player is currently on.
   * @return The map the player is on.
   */
  public Map getMap() {
    return currentMap;
  }
  
  /**
   * Move the player in the specified direction.
   * @param d The direction to move the player.
   */
  public void move(Direction d) {
    if (currentAction == PlayerAction.NONE) { // If the player is not doing anything
      /*
       * Get the location relative to the current location
       * For example NORTH would return the location above the current location
       */
      Location newLoc = entityLoc.getRelativeLocation(d);
      if (currentMap.isValidLocation(newLoc)) { // Check if location exists
        currentAction = PlayerAction.MOVING; // Change player action to moving
        entityLoc.setDirection(d); // Change player direction to new direction
        playerSprite.startWalkingAnimation(); // Begin animating the sprite
        // If the location is invalid, play a sound
      } else if (cannotMove.getStatus() == SoundSource.Status.STOPPED) {
        cannotMove.play();
      }
    }
  }
  
  /**
   * Update the animation progress.
   */
  public void update() {
    if (currentAction == PlayerAction.MOVING) { // If the player is moving
      if (playerSprite.finishedAnimating()) { // If the animation is complete
        currentAction = PlayerAction.NONE; // Stop moving
        // Actually move the location
        entityLoc = entityLoc.getRelativeLocation(entityLoc.getDirection());
        // Update the sprite with the new location
        playerSprite.updatePosition(entityLoc);
      } else {
        playerSprite.animate(); // Proceed with the animation
      }
    }
  }
  
  /**
   * Get animated sprite.
   * @return Animated sprite.
   */
  public Sprite getSprite() {
    return playerSprite.getSprite();
  }
  
  /**
   * Draw the player on the screen.
   */
  public void draw(RenderTarget target, RenderStates states) {
    // Get the animated sprite and draw it
    playerSprite.getSprite().draw(target, states); 
  }
}
