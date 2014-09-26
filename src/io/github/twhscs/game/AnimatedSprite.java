package io.github.twhscs.game;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * A sprite supporting animation.
 * @author Robert
 *
 */
public class AnimatedSprite {
  /**
   * The speed of the animation in Hz (I think).
   */
  private final float animationSpeed = 15;
  /**
   * The frame (stage) in the animation.
   */
  private int animationFrame = 0;
  /**
   * Keeps track of time between frame changes.
   */
  private int frameCounter = 0;
  /**
   * The position at which the sprite is rendered.
   */
  private Vector2f spritePosition = new Vector2f(0, 0);
  /**
   * The sprite to animated.
   */
  private Sprite animatedSprite;
  /**
   * The location of the entity this sprite represents.
   */
  private Location entityLoc;
  /**
   * The size of the sprite.
   */
  private final Vector2i spriteSize = new Vector2i(32, 48);
  /**
   * The current animation to play.
   */
  private AnimationType currentAnimationType = AnimationType.NONE;
  
  /**
   * Create a new animated sprite.
   * @param s The sprite to animate.
   * @param l The location of the entity that the sprite represents.
   */
  public AnimatedSprite(Sprite s, Location l) {
    animatedSprite = s; // Set the sprite
    entityLoc = l; // Get parent's location
    updatePosition(l); // Update the sprite position
  }
  
  /**
   * Update the animation.
   */
  public void animate() {
    if (currentAnimationType != AnimationType.NONE) { // If the animation is supposed to update
      if (frameCounter >= animationSpeed) { // If 15 Hz has passed
        stopAnimation(); // Stop the animation
      } else if (currentAnimationType == AnimationType.WALKING) {
        // Get the position between old and new.
        spritePosition = entityLoc.interpolate(animationSpeed, frameCounter);
        /*
         * Think of the following like a number line
         * The player starts at 0 and wants to end up at 1
         * Every few milliseconds we increase his position by about .1 or so
         * We then divide this number line into fourths
         * If the player is in the 1st fourth, show frame 1 of the animation
         * If he is in the 2nd fourth, show frame 2, etc.
         */
        Vector2i entityPos = entityLoc.getPosition(); // Get the entity position
        // Subtract the new position from the starting position to get a number from 0.0 - 1.0
        Vector2f animationProgress = 
            Vector2f.sub(spritePosition, new Vector2f(entityPos.x, entityPos.y));
        // Take the absolute value because we're measuring distance
        float change = Math.abs(animationProgress.x + animationProgress.y);
        // As mentioned above, determine the frame based on the position
        if (change >= 0.f && change < .25f) {
          animationFrame = 0;
        } else if (change >= .25f && change < .5f) {
          animationFrame = 1;
        } else if (change >= .5f && change < .75f) {
          animationFrame = 2;
        } else if (change >= .75f && change <= 1.0f) {
          animationFrame = 3;
        }
      } else if (currentAnimationType == AnimationType.STATIONARY_WALK) {
        // Essentially the same as above just without moving the player
        float steps = animationSpeed / 4; // Divide number line into fourths
        // Based on which fourth the animation resides, change the frame
        if (frameCounter >= 0.f && frameCounter < steps) {
          animationFrame = 0;
        } else if (frameCounter >= steps && frameCounter < (2 * steps)) {
          animationFrame = 1;
        } else if (frameCounter >= (2 * steps) && frameCounter < (3 * steps)) {
          animationFrame = 2;
        } else if (frameCounter >= (3 * steps) && frameCounter <= (4 * steps)) {
          animationFrame = 3;
        }
      }
      frameCounter++; // Increment on each update to keep track of time
    }
  }
  
  /**
   * Stop the animation.
   */
  private void stopAnimation() {
    // Reset the following
    currentAnimationType = AnimationType.NONE;
    frameCounter = 0;
    animationFrame = 0;
  }
  
  /**
   * Start the animation.
   */
  public void startAnimation(AnimationType t) {
    currentAnimationType = t;
  }
  
  /**
   * Determine whether or not the animation finished.
   * @return If the animation finished.
   */
  public boolean finishedAnimating() {
    return currentAnimationType == AnimationType.NONE;
  }
  
  /**
   * Update the entity position.
   * @param l The new location.
   */
  public void updatePosition(Location l) {
    entityLoc = l; // Update the entity location
    Vector2i entityPos = entityLoc.getPosition(); // Get the position from the location
    spritePosition = new Vector2f(entityPos.x, entityPos.y); // Update the sprite position
  }
  
  /**
   * Get the animated sprite to draw it.
   * @return The animated sprite.
   */
  public Sprite getSprite() {
    // Set the texture based on the direction and animation frame
    animatedSprite.setTextureRect(getTextureCoords()); 
    // Set the sprite position
    animatedSprite.setPosition(new Vector2f(spritePosition.x * spriteSize.x, 
        (spritePosition.y * spriteSize.x) - (spriteSize.y - spriteSize.x)));
    return animatedSprite;
  }
  
  /**
   * Find the correct image to match the direction and animation frame.
   * @return The coordinates to the proper texture.
   */
  private IntRect getTextureCoords() {
    int topX = animationFrame * 32; // The x coordinate of the image
    int topY = 0; // The top y coordinate of the image
    // Match the correct image to the direction
    switch(entityLoc.getDirection()) {
      case NORTH:
        topY = 144;
        break;
      case SOUTH:
        topY = 0;
        break;
      case EAST:
        topY = 96;
        break;
      case WEST:
        topY = 48;
        break;
    }
    // Create and return an integer rectangle
    IntRect textureCoordsRect = new IntRect(topX, topY, spriteSize.x, spriteSize.y);
    return textureCoordsRect;
  }
  
  /**
   * Get the size of the sprite as a vector.
   * @return An integer vector containing the sprite size.
   */
  public Vector2i getSpriteSize() {
    return spriteSize;
  }
}
