package io.github.twhscs.game;

import org.jsfml.system.Vector2i;

/**
 * Contains a position and direction.
 * @author Robert
 *
 */
public class Location implements Cloneable {
  private Vector2i locPosition = new Vector2i(0, 0);
  private Direction locDirection;
  
  public Location(int x, int y) {
    this(x, y, Direction.SOUTH);
  }
  
  public Location(int x, int y, Direction d) {
    locPosition = new Vector2i(x, y);
    locDirection = d;
  }

  public Vector2i getPosition() {
    return locPosition;
  }

  public Direction getDirection() {
    return locDirection;
  }

  public void setPosition(Vector2i newPosition) {
    locPosition = newPosition;
  }
  
  public void setDirection(Direction newDirection) {
    locDirection = newDirection;
  }
  
  public void addPosition(Vector2i position) {
    locPosition = Vector2i.add(locPosition, position);
  }
  
  public void subtractPosition(Vector2i position) {
    locPosition = Vector2i.sub(locPosition, position);
  }
  
  Location getRelativeLocation(Direction d) {
    Location thisLocation = this;
    Location newLocation = new Location(0, 0);
    try {
      newLocation = thisLocation.clone();
    } catch (CloneNotSupportedException ex) {
      ex.printStackTrace();
    }
    
    switch(d) {
      case NORTH:
        newLocation.subtractPosition(new Vector2i(0, 1));
        break;
      case SOUTH:
        newLocation.addPosition(new Vector2i(0, 1));
        break;
      case EAST:
        newLocation.addPosition(new Vector2i(1, 0));
        break;
      case WEST:
        newLocation.subtractPosition(new Vector2i(1, 0));
        break;
    }
    newLocation.setDirection(d);
    return newLocation;
  }
  
  protected Location clone() throws CloneNotSupportedException {
    return (Location) super.clone();
  }
}
