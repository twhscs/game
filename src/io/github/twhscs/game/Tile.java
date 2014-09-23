package io.github.twhscs.game;

import org.jsfml.system.Vector2f;

/**
 * Enumeration of tiles comprising maps.
 * @author Robert
 *
 */
public enum Tile { 
  
  WATER, // A wet and wild tile
  SAND, // Can't you feel it between your toes?
  GRASS; // Soft, green, vivid, inviting...
  
  /**
   * The length or width of a map tile.
   * Each tile MUST be a square, therefore the area of the tile is this number squared.
   */
  private static final int tileSize = 32;
  
  /**
   * Get the coordinates of a specific tile from the tile-set image.
   * @param t The tile to get the coordinates of.
   * @return The coordinates of the tile image in the tile-set.
   */
  public static Vector2f getTextureCoords(Tile t) {
    Vector2f textureCoordinates = new Vector2f(0, 0); // Initialize an empty vector
    switch(t) {
      case WATER:
        textureCoordinates = new Vector2f(480, 544); // Water is located at 480, 544
        break;
      case SAND:
        textureCoordinates = new Vector2f(576, 352); // Sand is located at 576, 352
        break;
      case GRASS:
        textureCoordinates =  new Vector2f(448, 352); // Grass is located at 448, 352
        break;
    }
    return textureCoordinates; // Return the coordinates
  }
  
  /**
   * Get the side length of a square tile.
   * The tile area is tileSize^2
   * @return The length of a square map tile.
   */
  public static int getSize() {
    return tileSize;
  }
  
  /**
   * Determine whether or not this tile is safe for the player to walk on.
   * @param t The tile to test.
   * @return Whether or not the player can walk on it.
   */
  public static boolean getCanWalkOn(Tile t) {
    boolean canWalk = false; // Initialize the boolean
    switch(t) {
      case WATER:
        canWalk = false; // Cannot walk on water - Jesus only
        break;
      case SAND:
        canWalk = true; // Can walk on sand
        break;
      case GRASS:
        canWalk = true; // Can walk on grass
        break;
    }
    return canWalk; // Return the boolean
  }
}

