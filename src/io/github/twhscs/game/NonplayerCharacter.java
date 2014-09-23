package io.github.twhscs.game;

import org.jsfml.audio.SoundSource;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

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
  
  public NonplayerCharacter(int x, int y, String n, String spritesheet) {
    entityLoc = new Location(x, y);
    try {
      entitySpritesheetTexture.loadFromFile(Paths.get("resources/" + spritesheet + ".png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    Sprite sprite = new Sprite();
    sprite.setTexture(entitySpritesheetTexture);
    entitySprite = new AnimatedSprite(sprite, entityLoc);
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
}
