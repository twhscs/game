package io.github.twhscs.game;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * The base class for all players, NPCs, monsters, items etc.
 * @author chris
 *
 */
public abstract class Entity {
  /**
   * The entity location.
   */
  protected Location entityLoc;
  
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
}