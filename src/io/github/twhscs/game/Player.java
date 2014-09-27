package io.github.twhscs.game;

import org.jsfml.audio.SoundSource;

import io.github.twhscs.game.ui.DialogueUIElement;
import io.github.twhscs.game.util.SoundResource;
import io.github.twhscs.game.util.SpriteResource;

/**
 * Holds everything pertaining to the player (user) playing the game.
 * @author Robert
 *
 */
public class Player extends Entity {
  /**
   * The action the player is currently performing.
   */
  private PlayerAction currentAction = PlayerAction.NONE;
  private final SoundResource cannotMove = new SoundResource("stuck");
  private final SoundResource interactSuccess = new SoundResource("interact_success");
  private final SoundResource interactFailure = new SoundResource("interact_failure");
  private DialogueUIElement dialogueUI;
  /**
   * Create and set the max health to default value.
   */
  private double maxHealth = 100;
  /**
   * Create and set the current health to max health.
   */
  private double curHealth = maxHealth;
  /**
   * Create and set the current XP to default value.
   */
  private double curXP = 0;
  /**
   * Create and set the current level to default value.
   */
  private int curLevel = 1;
  
  /**
   * Create a new player at the specified location.
   * @param l The location to create the player at.
   * @param d A reference to the main dialogue ui.
   */
  public Player(Location l, DialogueUIElement d) {
    entityLoc = l; // Create a new location at position x, y
    // Create a new animated sprite from the regular sprite
    SpriteResource sprite = new SpriteResource("player");
    entitySprite = new AnimatedSprite(sprite, entityLoc);
    dialogueUI = d;
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
      } else if (cannotMove.getSound().getStatus() == SoundSource.Status.STOPPED) {
        /*
         *  Play the stationary walk animation.
         *  It looks like the player is trying to move but they cannot
         */
        entitySprite.startAnimation(AnimationType.STATIONARY_WALK); 
        cannotMove.getSound().play(); // Play an annoying sound effect
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
        if (interactSuccess.getSound().getStatus() == SoundSource.Status.STOPPED 
            && interactFailure.getSound().getStatus() == SoundSource.Status.STOPPED) {
          interactSuccess.getSound().play();
        }
        // Turn the NPC to face the player
        e.getLocation().setDirection(Direction.getInverse(entityLoc.getDirection()));
        talk(npc); // Talk to the NPC
      } else if (interactSuccess.getSound().getStatus() == SoundSource.Status.STOPPED 
          && interactFailure.getSound().getStatus() == SoundSource.Status.STOPPED) {
        interactFailure.getSound().play();
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

  /**
   * Get the player's current health.
   * @return The player's current health.
   */
  public double getHealth() {
    return curHealth;
  }
  /**
   * Sets the player's health.
   * @param h The health to set as player's health.
   */
  public void setHealth(double h) {
    curHealth = h;
  }
  /**
   * Add health to the player's health.
   * @param h Amount of health to add to the player's health.
   */
  public void addHealth(double h) {
    curHealth += h;
  }
  /**
   * Subtract health to the player's health.
   * @param h Amount of health to subtract from the player's health.
   */
  public void subtractHealth(double h) {
    curHealth -= h;
  }
  /**
   * Get the player's maximum health.
   * @return The player's maximum health.
   */
  public double getMaxHealth() {
    return maxHealth;
  }
  /**
   * Sets the player's current health to the player's maximum health.
   */
  public void heal() {
    curHealth = maxHealth;
  }
  /**
   * Get the player's current XP.
   * @return The player's current XP.
   */
  public double getXP() {
    return curXP;
  }
  /**
   * Sets the player's XP.
   * @param x The health to set as player's XP.
   */
  public void setXP(double x) {
    curHealth = x;
  }
  /**
   * Add XP to the player's XP.
   * @param x Amount of XP to add to the player's XP.
   */
  public void addXP(double x) {
    curHealth += x;
  }
  /**
   * Subtract XP to the player's XP.
   * @param x Amount of XP to subtract from the player's XP.
   */
  public void subtractXP(double x) {
    curHealth -= x;
  }
  /**
   * Get the player's current level.
   * @return The player's current level.
   */
  public double getLevel() {
    return curLevel;
  }
  /**
   * Set the player's current level.
   * @param l The level to set the player's current level to.
   */
  public void setLevel(int l) {
    curLevel = l;
  }
  /**
   * Add one to the player's current level.
   */
  public void levelUp() {
    curLevel++;
  }
  /**
   * Get the next XP needed to level up.
   * @return The next XP needed to level up.
   */
  public double getNextLevelXP() {
    return Math.floor(curLevel * 10 + Math.pow(curLevel + 1, 3));
  }
}
