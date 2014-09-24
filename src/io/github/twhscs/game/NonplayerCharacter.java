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
public class NonplayerCharacter extends Entity {
  /**
   * The texture for the sprite.
   */
  private final Texture entitySpritesheetTexture = new Texture();
  
  private boolean moving = false;
  
  private String name;
  
  private ArrayList<String> dialogue = new ArrayList<String>();
  
  private final Sprite portrait = new Sprite();
  
  private Iterator<String> dialogueProgress;
  
  public NonplayerCharacter(int x, int y, String n, String spritesheet) {
    entityLoc = new Location(x, y);
    Texture portraitTex = new Texture();
    try {
      entitySpritesheetTexture.loadFromStream(
          getClass().getClassLoader().getResourceAsStream(spritesheet + "_sprite.png"));
      portraitTex.loadFromStream(
          getClass().getClassLoader().getResourceAsStream(spritesheet + "_portrait.png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    Sprite sprite = new Sprite();
    sprite.setTexture(entitySpritesheetTexture);
    entitySprite = new AnimatedSprite(sprite, entityLoc);
    portrait.setTexture(portraitTex);
    name = n;
  }
  
  public void move(Direction d) {
    if (!moving && entitySprite.finishedAnimating()) {
      Location newLoc = entityLoc.getRelativeLocation(d);
      entityLoc.setDirection(d);
      if (parentMap.isValidLocation(newLoc)) {
        moving = true;
        entitySprite.startAnimation(AnimationType.WALKING);
      } else {
        entitySprite.startAnimation(AnimationType.STATIONARY_WALK); 
      }
    }
  }
  
  public void update() {
    if (moving) {
      if (entitySprite.finishedAnimating()) {
        moving = false;
        entityLoc = entityLoc.getRelativeLocation(entityLoc.getDirection());
        entitySprite.updatePosition(entityLoc);
      }
    }
    entitySprite.animate();
  }
  
  public void addDialogue(String s) {
    dialogue.add(s);
  }
  
  public String getName() {
    return name;
  }
  
  public Sprite getPortrait() {
    return portrait;
  }
  
  public void startTalking() {
    dialogueProgress = dialogue.iterator();
  }
  
  public String getDialogue() {
    if (dialogueProgress.hasNext()) {
      return dialogueProgress.next();
    } else {
      dialogueProgress = null;
      return null;
    }
  }
  
  public boolean canTalk() {
    return dialogue.size() > 0;
  }
}
