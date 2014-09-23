package io.github.twhscs.game;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Contains a position and direction.
 * @author Robert
 *
 */
public class Location implements Cloneable, Comparable<Location> {
  /**
   * The vector position (x, y).
   */
  private Vector2i locPosition = new Vector2i(0, 0);
  /**
   * The cardinal direction (N, S, E, W).
   */
  private Direction locDirection;
  
  /**
   * Create a new location at x, y facing south.
   * @param x New x-coordinate.
   * @param y New y-coordinate.
   */
  public Location(int x, int y) {
    this(x, y, Direction.SOUTH);
  }
  
  /**
   * Create a new location at x, y with the specified direction.
   * @param x New x-coordinate.
   * @param y New y-coordinate.
   * @param d New direction.
   */
  public Location(int x, int y, Direction d) {
    locPosition = new Vector2i(x, y); // Set the new position
    locDirection = d; // Set the new direction
  }
  
  /**
   * Get the position.
   * @return Integer vector of position.
   */
  public Vector2i getPosition() {
    return locPosition;
  }
  
  /**
   * Get the direction.
   * @return The direction.
   */
  public Direction getDirection() {
    return locDirection;
  }

  /**
   * Set the position.
   * @param newPosition The new position vector.
   */
  public void setPosition(Vector2i newPosition) {
    locPosition = newPosition;
  }
  
  /**
   * Set the direction.
   * @param newDirection The new direction.
   */
  public void setDirection(Direction newDirection) {
    locDirection = newDirection;
  }
  
  /**
   * Add another position with the current position.
   * @param position The position to add with the current position.
   */
  public void addPosition(Vector2i position) {
    locPosition = Vector2i.add(locPosition, position); // Add the two positions
  }
  
  /**
   * Subtract another position from the current position.
   * @param position The position to subtract from the current position.
   */
  public void subtractPosition(Vector2i position) {
    locPosition = Vector2i.sub(locPosition, position); // Subtract the two positions
  }
  
  /**
   * Get the location in the direction from the current position.
   * @param d The relative direction.
   * @return The position in the relative direction.
   */
  Location getRelativeLocation(Direction d) {
    Location thisLocation = this; // The current location
    Location newLocation = new Location(0, 0); // The new location
    // Clone (copy) the current location
    try {
      newLocation = thisLocation.clone();
    } catch (CloneNotSupportedException ex) {
      ex.printStackTrace();
    }
    // Modify the new location based on the direction
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
    newLocation.setDirection(d); // Apply the new direction
    return newLocation; // Return the new location
  }
  
  /**
   * Return a position in-between two locations.
   * @param speed The size of the steps.
   * @param step The current step.
   * @return The vector position in-between two locations.
   */
  public Vector2f interpolate(float speed, int step) {
    Vector2f interpolateAddition = new Vector2f(0, 0); // Initialize a new vector
    /*
     * 1.0f / speed calculates the step increments
     * Multiplying it by step calculates the current increment
     */
    float additionAmount = (1.0f / speed) * step; // Current increment
    // Apply the increment to the vector based on the direction
    switch(locDirection) {
      case NORTH:
        interpolateAddition = new Vector2f(0, -additionAmount);
        break;
      case SOUTH:
        interpolateAddition = new Vector2f(0, additionAmount);
        break;
      case EAST:
        interpolateAddition = new Vector2f(additionAmount, 0);
        break;
      case WEST:
        interpolateAddition = new Vector2f(-additionAmount, 0);
        break;
    }
    Vector2i currentPos = getPosition();
    // Add the increment to the base vector
    return Vector2f.add(new Vector2f(currentPos.x, currentPos.y), interpolateAddition);
  }
  
  /**
   * Allows the Location to be cloned (copied).
   */
  protected Location clone() throws CloneNotSupportedException {
    return (Location) super.clone();
  }
  
  public boolean equals(Location l) {
      return locPosition.equals(l.getPosition());
  }
  
  public String toString() {
    return "[" + locPosition.x + ", " + locPosition.y + "] Facing: " + locDirection;
  }
  
  public int compareTo(Location l) {
    return locPosition.y - l.getPosition().y;
  }
}
