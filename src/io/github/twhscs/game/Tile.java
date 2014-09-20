package io.github.twhscs.game;

import org.jsfml.system.Vector2f;

/**
 * Map tiles for the Map class.
 * @author Robert
 *
 */
public enum Tile { 
  WATER, SAND, GRASS;
  
  private static final int tileSize = 32;
  
  public static Vector2f getTextureCoords(Tile t) {
    Vector2f textureCoordinates = new Vector2f(0, 0);
    switch(t) {
      case WATER:
        textureCoordinates = new Vector2f(480, 544);
        break;
      case SAND:
        textureCoordinates = new Vector2f(576, 352);
        break;
      case GRASS:
        textureCoordinates =  new Vector2f(448, 352);
        break;
    }
    return textureCoordinates;
  }
  
  public static int getSize() {
    return tileSize;
  }
}

