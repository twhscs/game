package io.github.twhscs.game;

import org.jsfml.system.Vector2i;

/**
 * 
 * @author Robert
 *
 */

public class Location {
  
  /**
   * x y coordinates storing position
   */
  private Vector2i position = new Vector2i(0, 0);
  
  /**
   * Direction facing
   */
  private Direction facing = Direction.SOUTH;
  
  /**
   * create new location at x y
   * @param x starting position
   * @param y starting position
   */
  public Location(int x, int y) {
    position = new Vector2i(x, y);
  }
  
  /**
   * get x coordinate
   * @return x
   */
  public int getX() {
    return position.x;
  }
  
  /**
   * get y coordinate
   * @return y
   */
  public int getY() {
    return position.y;
  }
  
  /**
   * get direction facing
   * @return direction
   */
  public Direction getDirection() {
    return facing;
  }
  
  /**
   * update position
   * @param x new x
   * @param y new y
   */
  public void setPosition(int x, int y) {
    position = new Vector2i(x, y);
  }
  
  /**
   * update direction
   * @param dir new direction
   */
  public void setDirection(Direction dir) {
    facing = dir;
  }
}
