package io.github.twhscs.game;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * A map or level for the player to interact in. Can be created manually or procedurally.
 * @author Robert
 *
 */
public class Map implements Drawable {
  /**
   * The size (length, width) of the map.
   */
  private Vector2i dimensions = new Vector2i(0, 0);
  /**
   * An array containing all of the tiles in the map.
   */
  private Tile[][] tileArray;
  /**
   * The tilesheet texture.
   */
  private final Texture tilesheetTexture = new Texture();
  /**
   * The vertex array for the tile map.
   * This is a streamlined way to draw many sprites at once.
   * By using the vertex array instead of 100s of sprites, performance is greatly improved.
   */
  private final VertexArray vertexArray = new VertexArray();
  /**
   * All of the entities (NPCs) on the map.
   */
  private final ArrayList<Entity> entitiesOnMap = new ArrayList<Entity>();
  
  /**
   * Create a new map of specified size and tile type.
   * @param l Map length.
   * @param w Map width.
   * @param t Default tile to auto-populate map.
   */
  public Map(int l, int w, Tile t) {
    dimensions = new Vector2i(l, w); // Update map dimensions
    tileArray = new Tile[dimensions.x][dimensions.y]; // Create a new tile array with the new size
    // Try to load the tilesheet file
    try {
      tilesheetTexture.loadFromStream(
          getClass().getClassLoader().getResourceAsStream("terrain.png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    // Set the vertex array to use quads because the tiles are square
    vertexArray.setPrimitiveType(PrimitiveType.QUADS); 
    initializeMap(t); // Auto-populate the map with the specified tile
  }
  
  /**
   * Create a new map of specified size that defaults to grass.
   * @param l Map length.
   * @param w Map width.
   */
  public Map(int l, int w) {
    this(l, w, Tile.GRASS);
  }
  
  /**
   * Fill the entire map to the specified tile.
   * @param initialTile The tile to populate the map with.
   */
  public void initializeMap(Tile initialTile) {
    /*
     * Arrays.fill does not work with matrices 
     * Therefore I have to loop through the outer level to fill the inner level
     */
    for (Tile[] row : tileArray) {
      Arrays.fill(row, initialTile); // Set each tile in the row to the specified tile
    }
  }
  
  /**
   * Draw the map onto the window.
   */
  public void draw(RenderTarget target, RenderStates states) {
    vertexArray.clear(); // Empty the vertex array from the previous draw
    final int tileSize = Tile.getSize(); // Grab the tile size
    // Loop through every single tile in the map
    for (int i = 0; i < dimensions.x; i++) {
      for (int j = 0; j < dimensions.y; j++) {
        Tile tileType = tileArray[i][j]; // Grab the current tile in the loop
        // Grab the texture coordinates to render the tile
        Vector2f textureCoords = Tile.getTextureCoords(tileType); 
        
        /*
         * Add each corner of the tile to the vertex array
         * Counter clock-wise motion
         */
        
        vertexArray.add(new Vertex(
            new Vector2f(i * tileSize, j * tileSize), textureCoords)); // Top-left
        
        vertexArray.add(new Vertex(
            new Vector2f(i * tileSize, (j * tileSize) + tileSize), 
                Vector2f.add(textureCoords, new Vector2f(0, tileSize)))); // Bottom-left
        
        vertexArray.add(new Vertex(
            new Vector2f((i * tileSize) + tileSize, (j * tileSize) + tileSize), 
                Vector2f.add(textureCoords, new Vector2f(tileSize, tileSize)))); // Bottom-right
        
        vertexArray.add(new Vertex(
            new Vector2f((i * tileSize) + tileSize, j * tileSize), 
                Vector2f.add(textureCoords, new Vector2f(tileSize, 0)))); // Top-right
      }
    }
    // Apply the texture to the vertex array
    RenderStates newStates = new RenderStates(tilesheetTexture); 
    vertexArray.draw(target, newStates); // Draw the vertex array
  }
  
  /**
   * Return the tile at the specified location.
   * @param l The location of the tile to get.
   * @return The tile at the specified location.
   */
  public Tile getTile(Location l) {
    Vector2i position = l.getPosition(); // Get the position from the location
    Tile t = tileArray[position.x][position.y]; // Get the tile at this position
    return t; // Return tile
  }
  
  /**
   * Determine whether or not the location fits within the map.
   * @param l The location to test.
   * @return If the location is valid.
   */
  public boolean isValidLocation(Location l) {
    Vector2i coordinates = l.getPosition(); // Get the position from the location
    // Return true if the location is greater than 0, 0 and less than l, w
    return ((coordinates.x >= 0) && (coordinates.y >= 0) 
        && (coordinates.x < dimensions.x) && (coordinates.y < dimensions.y) 
        && Tile.getCanWalkOn(getTile(l)) && (getEntityatLocation(l) == null));
  }
  
  public Entity getEntityatLocation(Location l) {
    Iterator<Entity> it = entitiesOnMap.iterator();
    while (it.hasNext()) {
      Entity e = it.next();
      if (e.getLocation().equals(l)) {
        return e;
      }
    }
    return null;
  }
  
  public void addEntity(Entity e) {
    if (getEntityatLocation(e.getLocation()) == null) {
      e.setParentMap(this);
      entitiesOnMap.add(e);
    }
  }
  
  public void drawAllEntities(RenderWindow w) {
    for (Entity e : entitiesOnMap) {
      w.draw(e);
    }
  }
  
  public void updateAllEntities() {
    Collections.sort(entitiesOnMap);
    for (Entity e : entitiesOnMap) {
      e.update();
    }
  }
}
