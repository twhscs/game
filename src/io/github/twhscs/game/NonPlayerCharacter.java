package io.github.twhscs.game;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An NPC class for interactive non-player characters.
 * @author Robert
 *
 */
public class NonPlayerCharacter extends Entity {
  /**
   * The texture for the sprite.
   */
  private final Texture entitySpritesheetTexture = new Texture();
  /**
   * A boolean representing whether or not the NPC is moving.
   */
  private boolean moving = false;
  /**
   * The NPC's name.
   */
  private String name;
  /**
   * A list of the NPC's dialogue.
   * Each string in the list is one "page" of dialogue.
   */
  private ArrayList<String> dialogue = new ArrayList<String>();
  /**
   * A close-up image of the NPC's face, shown during dialogue.
   */
  private final Sprite portrait = new Sprite();
  /**
   * Represents how many pages of dialogue has been shown out of the total.
   */
  private Iterator<String> dialogueProgress;
  
  /**
   * Create a new NPC.
   * @param l The NPC's location.
   * @param n The NPC's name.
   * @param spritesheet The sprite-set and portrait to use.
   */
  public NonPlayerCharacter(Location l, String n, String spritesheet) {
    entityLoc = l; // Set the location
    Texture portraitTex = new Texture(); // Create a new texture for the portrait
    // Load the portrait and sprite-set textures
    try {
      entitySpritesheetTexture.loadFromStream(
          getClass().getClassLoader().getResourceAsStream("images/" + spritesheet + "_sprite.png"));
      portraitTex.loadFromStream(
        getClass().getClassLoader().getResourceAsStream("images/" + spritesheet + "_portrait.png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    Sprite sprite = new Sprite(); // Create a new regular sprite
    sprite.setTexture(entitySpritesheetTexture); // Set the texture
    entitySprite = new AnimatedSprite(sprite, entityLoc); // Create an animated sprite
    portrait.setTexture(portraitTex); // Set the portrait texture
    name = n; // Set the NPC's name
  }
  
  /**
   * Move the NPC in the specified direction.
   * @param d The direction to move towards.
   */
  public void move(Direction d) {
    if (!moving && entitySprite.finishedAnimating()) { // If not moving or animating
      Location newLoc = entityLoc.getRelativeLocation(d); // Find the location in direction d
      entityLoc.setDirection(d); // Update the NPC's direction
      if (parentMap.isValidLocation(newLoc)) { // If the new location is valid
        moving = true; // Start moving
        entitySprite.startAnimation(AnimationType.WALKING); // Start walking animation
      } else {
        entitySprite.startAnimation(AnimationType.STATIONARY_WALK); // Attempt to walk and fail
      }
    }
  }
  
  /**
   * Update the NPC's animation.
   */
  public void update() {
    if (moving && entitySprite.finishedAnimating()) { // If the animation finished
        moving = false; // Stop moving
        entityLoc = entityLoc.getRelativeLocation(entityLoc.getDirection());
        entitySprite.updatePosition(entityLoc); // Update the sprite position
    }
    entitySprite.animate(); // Update the animation
  }
  
  /**
   * Add a line of dialogue.
   * @param s The line of dialogue to add.
   */
  public void addDialogue(String s) {
    dialogue.add(s); // Add the string to the dialogue array list
  }
  
  /**
   * Get the NPC's name.
   * @return The NPC's name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Get the NPC's portrait sprite.
   * @return The portrait sprite.
   */
  public Sprite getPortrait() {
    return portrait;
  }
  
  /**
   * Assign the dialogueProgress iterator to keep track of the dialogue progress.
   */
  public void startTalking() {
    dialogueProgress = dialogue.iterator();
  }
  
  /**
   * Get the next string of dialogue if it exists.
   * @return
   */
  public String getDialogue() {
    if (dialogueProgress.hasNext()) { // See if the next string exists
      return dialogueProgress.next(); // Return it if it does
    } else {
      // Otherwise reset the iterator and return null
      dialogueProgress = null;
      return null;
    }
  }
  
  /**
   * Determine whether this NPC has dialogue.
   * @return Whether or not the NPC can talk.
   */
  public boolean canTalk() {
    return dialogue.size() > 0; // If dialogue is not empty, return true
  }
}
