package io.github.twhscs.game;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.audio.SoundSource;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;

import io.github.twhscs.game.ui.DialogueUIElement;

/**
 * Holds everything pertaining to the player (user) playing the game.
 * @author Robert
 *
 */
public class Player extends Entity {
  /**
   * The texture for the sprite.
   */
  private final Texture entitySpritesheetTexture = new Texture();
  /**
   * The action the player is currently performing.
   */
  private PlayerAction currentAction = PlayerAction.NONE;
  /**
   * The buffer that allows the 'stuck' sound to be loaded.
   */
  private final SoundBuffer cannotMoveBuffer = new SoundBuffer();
  /**
   * The buffer that allows the 'interact' sound to be loaded.
   */
  private final SoundBuffer interactSuccessBuffer = new SoundBuffer();
  /**
   * The buffer that allows the 'interact fail' sound to be loaded.
   */
  private final SoundBuffer interactFailureBuffer = new SoundBuffer();
  /**
   * The object that plays the 'stuck' sound.
   */
  private final Sound cannotMove = new Sound();
  /**
   * The object that plays the 'interact' sound.
   */
  private final Sound interactSuccess = new Sound();
  /**
   * The object that plays the 'interact fail' sound.
   */
  private final Sound interactFailure = new Sound();
  /**
   * A reference to the main dialogue ui object.
   */
  private DialogueUIElement dialogueUI;
  
  /**
   * Create a new player at the specified location.
   * @param l The location to create the player at.
   * @param d A reference to the main dialogue ui.
   */
  public Player(Location l, DialogueUIElement d) {
    entityLoc = l; // Create a new location at position x, y
    // Try to load sprite texture and 'stuck' sound
    try {
      //entitySpritesheetTexture.loadFromFile(Paths.get("resources/player.png"));
      entitySpritesheetTexture.loadFromStream(
          getClass().getClassLoader().getResourceAsStream("images/player.png"));
      cannotMoveBuffer.loadFromStream(
          getClass().getClassLoader().getResourceAsStream("sounds/stuck.wav"));
      interactSuccessBuffer.loadFromStream(
          getClass().getClassLoader().getResourceAsStream("sounds/interact_success.wav"));
      interactFailureBuffer.loadFromStream(
          getClass().getClassLoader().getResourceAsStream("sounds/interact_failure.wav"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    Sprite sprite = new Sprite(); // Create a new regular sprite
    sprite.setTexture(entitySpritesheetTexture); // Set the texture for the sprite
    // Create a new animated sprite from the regular sprite
    entitySprite = new AnimatedSprite(sprite, entityLoc);
    dialogueUI = d;
    // Set the sound object from the buffer (loading)
    cannotMove.setBuffer(cannotMoveBuffer); 
    interactSuccess.setBuffer(interactSuccessBuffer);
    interactFailure.setBuffer(interactFailureBuffer);
  }
  
  /**
   * Create a new player at (0, 0).
   */
  public Player(DialogueUIElement d) {
    this(new Location(0, 0), d);
  }
  
  /**
   * Move the player in the specified direction.
   * @param d The direction to move the player.
   */
  public void move(Direction d) {
    // If the player is not doing anything and no animation is playing
    if (currentAction == PlayerAction.NONE && entitySprite.finishedAnimating()) {
      /*
       * Get the location relative to the current location
       * For example NORTH would return the location above the current location
       */
      Location newLoc = entityLoc.getRelativeLocation(d);
      entityLoc.setDirection(d); // Change player direction to new direction
      if (parentMap.isValidLocation(newLoc)) { // Check if location exists
        currentAction = PlayerAction.MOVING; // Change player action to moving
        entitySprite.startAnimation(AnimationType.WALKING); // Begin animating the sprite
        // If the location is invalid, play a sound
      } else if (cannotMove.getStatus() == SoundSource.Status.STOPPED) {
        /*
         *  Play the stationary walk animation.
         *  It looks like the player is trying to move but they cannot
         */
        entitySprite.startAnimation(AnimationType.STATIONARY_WALK); 
        cannotMove.play(); // Play an annoying sound effect
      }
    }
  }
  
  /**
   * Update the animation progress.
   */
  public void update() {
    if (currentAction == PlayerAction.MOVING) { // If the player is moving
      if (entitySprite.finishedAnimating()) { // If the animation is complete
        currentAction = PlayerAction.NONE; // Stop moving
        // Actually move the location
        entityLoc = entityLoc.getRelativeLocation(entityLoc.getDirection());
        // Update the sprite with the new location
        entitySprite.updatePosition(entityLoc);
      }
    }
    // Update the animation, if there is no current animation this call will be ignored
    entitySprite.animate();
  }
  
  /**
   * Get animated sprite.
   * @return Animated sprite.
   */
  public AnimatedSprite getAnimatedSprite() {
    return entitySprite;
  }
  
  /**
   * Have the player interact with nearby objects.
   */
  public void interact() {
    // Get the location in front of the player
    Location interactLoc = entityLoc.getRelativeLocation(entityLoc.getDirection());
    // Get the entity in that location
    Entity e = parentMap.getEntityatLocation(interactLoc);
    // Check if there actually is an entity there and if it is an NPC
    if (e != null && e instanceof NonPlayerCharacter) {
      // Cast the entity to an NPC
      NonPlayerCharacter npc = (NonPlayerCharacter) e;
      // If the player is doing nothing or talking and the NPC can talk
      if ((currentAction == PlayerAction.NONE || currentAction == PlayerAction.TALKING) 
          && npc.canTalk()) {
        // Play the interact sound
        if (interactSuccess.getStatus() == SoundSource.Status.STOPPED 
            && interactFailure.getStatus() == SoundSource.Status.STOPPED) {
          interactSuccess.play();
        }
        // Turn the NPC to face the player
        e.getLocation().setDirection(Direction.getInverse(entityLoc.getDirection()));
        talk(npc); // Talk to the NPC
      } else if (interactSuccess.getStatus() == SoundSource.Status.STOPPED 
          && interactFailure.getStatus() == SoundSource.Status.STOPPED) {
        interactFailure.play();
        // Play the interact failure sound
      }
    }
  }
  
  /**
   * Initiate dialogue between the player and an NPC.
   * @param n The NPC to talk to.
   */
  public void talk(NonPlayerCharacter n) {
    if (currentAction == PlayerAction.NONE) { // If the player isn't doing anything
      dialogueUI.show(); // Show the dialogue
      currentAction = PlayerAction.TALKING; // Change the player's action to talking
      n.startTalking(); // Set the NPC to prepare to talk
      dialogueUI.setPortrait(n.getPortrait()); // Update the UI with the NPC's portrait
      dialogueUI.setText(n.getDialogue()); // Update the UI with the NPC's dialogue
      dialogueUI.setName(n.getName()); // Update the UI with the NPC's name
    } else if (currentAction == PlayerAction.TALKING) { // If the player is already talking
      String t = n.getDialogue(); // Get the next line of dialogue
      if (t != null) { // If the next line actually exists
        dialogueUI.setText(t); // Update the UI
      } else {
        currentAction = PlayerAction.NONE; // Make the player stop talking
        dialogueUI.hide(); // Hide the UI
      }
    }
  }
  
  /**
   * Get the player's current action.
   * @return The player's current action.
   */
  public PlayerAction getCurrentAction() {
    return currentAction;
  }
  
  public void setCurrentAction(PlayerAction a) {
	  currentAction = a;
  }
}
