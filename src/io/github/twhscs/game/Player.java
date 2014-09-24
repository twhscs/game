package io.github.twhscs.game;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.audio.SoundSource;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;

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
  private final SoundBuffer interactSuccessBuffer = new SoundBuffer();
  private final SoundBuffer interactFailureBuffer = new SoundBuffer();
  /**
   * The object that plays the 'stuck' sound.
   */
  private final Sound cannotMove = new Sound();
  
  private final Sound interactSuccess = new Sound();
  
  private final Sound interactFailure = new Sound();
  
  private DialogueUIElement dialogueUI;
  
  /**
   * Creates a new player with a location at x, y.
   * @param x Starting x position.
   * @param y Starting y position.
   */
  public Player(int x, int y, DialogueUIElement d) {
    entityLoc = new Location(x, y); // Create a new location at position x, y
    // Try to load sprite texture and 'stuck' sound
    try {
      //entitySpritesheetTexture.loadFromFile(Paths.get("resources/player.png"));
      entitySpritesheetTexture.loadFromStream(
          getClass().getClassLoader().getResourceAsStream("player.png"));
      cannotMoveBuffer.loadFromStream(getClass().getClassLoader().getResourceAsStream("stuck.wav"));
      interactSuccessBuffer.loadFromStream(
          getClass().getClassLoader().getResourceAsStream("interact_success.wav"));
      interactFailureBuffer.loadFromStream(
          getClass().getClassLoader().getResourceAsStream("interact_failure.wav"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    Sprite sprite = new Sprite(); // Create a new regular sprite
    sprite.setTexture(entitySpritesheetTexture); // Set the texture for the sprite
    // Create a new animated sprite from the regular sprite
    entitySprite = new AnimatedSprite(sprite, entityLoc);
    dialogueUI = d;
    cannotMove.setBuffer(cannotMoveBuffer); // Set the sound object from the buffer (loading)
    interactSuccess.setBuffer(interactSuccessBuffer);
    interactFailure.setBuffer(interactFailureBuffer);
  }
  
  /**
   * Create a new player at (0, 0).
   */
  public Player(DialogueUIElement d) {
    this(0, 0, d);
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
  
  public void interact() {
    Location interactLoc = entityLoc.getRelativeLocation(entityLoc.getDirection());
    Entity e = parentMap.getEntityatLocation(interactLoc);
    if (e != null && e instanceof NonplayerCharacter) {
      NonplayerCharacter npc = (NonplayerCharacter) e;
      if ((currentAction == PlayerAction.NONE || currentAction == PlayerAction.TALKING) 
          && npc.canTalk()) {
        if (interactSuccess.getStatus() == SoundSource.Status.STOPPED 
            && interactFailure.getStatus() == SoundSource.Status.STOPPED) {
          interactSuccess.play();
        }
        talk(npc);
      } else if (interactSuccess.getStatus() == SoundSource.Status.STOPPED 
          && interactFailure.getStatus() == SoundSource.Status.STOPPED) {
        interactFailure.play();
      }
    }
  }
  
  public void talk(NonplayerCharacter n) {
    if (currentAction == PlayerAction.NONE) {
      dialogueUI.show();
      currentAction = PlayerAction.TALKING;
      n.startTalking();
      dialogueUI.setPortrait(n.getPortrait());
      dialogueUI.setText(n.getDialogue());
      dialogueUI.setName(n.getName());
    } else if (currentAction == PlayerAction.TALKING) {
      String t = n.getDialogue();
      if (t != null) {
        dialogueUI.setText(t);
      } else {
        currentAction = PlayerAction.NONE;
        dialogueUI.hide();
      }
    }
  }
  
  public PlayerAction getCurrentAction() {
    return currentAction;
  }
}
