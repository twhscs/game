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
   * Update all entity logic on fixed timestep.
   */
  public void update() {
    // Update the animation, if there is no current animation this call will be ignored
    entitySprite.animate();
  }
  
  public void setParentMap(Map m) {
    parentMap = m;
  }
  
  public Map getParentMap() {
    return parentMap;
  }
  
  public int compareTo(Entity e) {
    return entityLoc.compareTo(e.getLocation());
  }
}
