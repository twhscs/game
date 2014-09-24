package io.github.twhscs.game;

/**
 * The cardinal directions, north, south, east, west.
 * @author Robert
 *
 */
public enum Direction { 
  
  NORTH, 
  SOUTH,
  EAST, 
  WEST;
  
  public static Direction getInverse(Direction d) {
    switch(d) {
      case NORTH:
        return SOUTH;
      case SOUTH:
        return NORTH;
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      default:
        return SOUTH;
    }
  }
}
