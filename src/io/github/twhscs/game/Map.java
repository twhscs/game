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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.github.twhscs.game.util.ImageResource;
import io.github.twhscs.game.util.Random;

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
  private final ImageResource tilesheet = new ImageResource("images/terrain");
  private final Texture tilesheetTex = tilesheet.getTexture();
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
    RenderStates newStates = new RenderStates(tilesheetTex); 
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
  
  /**
   * Get the entity at the specified location.
   * @param l The location to check.
   * @return The entity at the location.
   */
  public Entity getEntityatLocation(Location l) {
    Iterator<Entity> it = entitiesOnMap.iterator(); // Iterate through entities
    while (it.hasNext()) {
      Entity e = it.next(); // Get each entity
      if (e.getLocation().equals(l)) { // Check its location for a match
        return e;
      }
    }
    return null; // Return null if no entity is found
  }
  
  /**
   * Add an entity to the map.
   * @param e The entity to add.
   */
  public void addEntity(Entity e) {
    // Check the entity location to make sure it is open
    if (getEntityatLocation(e.getLocation()) == null) {
      e.setParentMap(this); // Set the entity parent map to this map
      entitiesOnMap.add(e); // Add the entity to the array list
    }
  }
  
  /**
   * Draw each entity to the window.
   * @param w The window to draw to.
   */
  public void drawAllEntities(RenderWindow w) {
    for (Entity e : entitiesOnMap) {
      w.draw(e); // Loop through and draw each entity
    }
  }
  
  /**
   * Sort and update each entity.
   */
  public void updateAllEntities() {
    Collections.sort(entitiesOnMap); // Sort entities by y value for proper rendering
    for (Entity e : entitiesOnMap) {
      e.update(); // Call each entity update method
    }
  }
  
  /**
   * Get a random location on the map with a valid tile and no entity.
   * @return The valid, random location.
   */
  public Location getRandomValidLocation() {
    Location randLoc = getRandomLocation(); // Get a random location on the map
    while (!isValidLocation(randLoc)) {
      randLoc = getRandomLocation(); // Keep generating new locations until valid
    }
    // Store all possible directions in a list
    List<Direction> directions = Arrays.asList(Direction.values());
    Collections.shuffle(directions); // Shuffle the list
    randLoc.setDirection(directions.get(0)); // Get the first (random) element in the list
    return randLoc; // Return the new location with both a random position and direction
  }
  
  /**
   * Return a random (potentially non-valid) location on the map.
   * @return The random location.
   */
  private Location getRandomLocation() {
    // Generate a random integer between 0 and the map width
    int randomX = Random.intRange(0, dimensions.x);
    // Generate a random integer between 0 and the map height
    int randomY = Random.intRange(0, dimensions.y);
    return new Location(randomX, randomY); // Return the new random location
  }
}
