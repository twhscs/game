package io.github.twhscs.game;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

/**
 * The base class for all players, NPCs, monsters, items etc.
 * @author chris
 *
 */
public abstract class Entity implements Drawable, Comparable<Entity> {
  /**
   * The entity location.
   */
  protected Location entityLoc;
  /**
   * The animated sprite representing the entity.
   */
  protected AnimatedSprite entitySprite;
  /**
   * The map this entity currently resides on.
   */
  protected Map parentMap;
  
  /**
   * Get the entity location.
   * @return The entity location.
   */
  public Location getLocation() {
    return entityLoc;
  }
  
  /**
   * Set the entity location.
   * @param l The new location.
   */
  public void setLocation(Location l) {
    entityLoc = l;
  }
  
  /**
   * Draw the animated sprite.
   */
  public void draw(RenderTarget target, RenderStates states) {
    entitySprite.getSprite().draw(target, states);
  }
  
  /**
   * Update all entity logic on fixed time-step.
   */
  public void update() {
    // Update the animation, if there is no current animation this call will be ignored
    entitySprite.animate();
  }
  
  /**
   * Update the map this entity resides on.
   * @param m The new map the entity is on.
   */
  public void setParentMap(Map m) {
    parentMap = m;
  }
  
  /**
   * Get the map this entity resides on.
   * @return The current map the entity is on.
   */
  public Map getParentMap() {
    return parentMap;
  }
  
  /**
   * Compare entities based on their location.
   * Used to correctly draw multiple entities of varying y values.
   */
  public int compareTo(Entity e) {
    return entityLoc.compareTo(e.getLocation());
  }
}
