package io.github.twhscs.game;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class Map implements Drawable {
  private Vector2i mapDimensions = new Vector2i(0, 0);
  private Tile[][] tileArray;
  private Texture mapTilesheetTexture = new Texture();
  private VertexArray mapVertexArray = new VertexArray();
  
  public Map(int l, int w, Tile t) {
    mapDimensions = new Vector2i(l, w);
    tileArray = new Tile[mapDimensions.x][mapDimensions.y];
    try {
      mapTilesheetTexture.loadFromFile(Paths.get("resources/terrain.png"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    mapVertexArray.setPrimitiveType(PrimitiveType.QUADS);
    initializeMap(t);
  }
  
  public Map(int l, int w) {
    this(l, w, Tile.GRASS);
  }

  public void initializeMap(Tile initialTile) {
    for (Tile[] row : tileArray) {
      Arrays.fill(row, initialTile);
    }
  }
  
  public void draw(RenderTarget target, RenderStates states) {
    final int tileSize = Tile.getSize();
    for (int i = 0; i < mapDimensions.x; i++) {
      for (int j = 0; j < mapDimensions.y; j++) {
        Tile tileType = tileArray[i][j];
        Vector2f textureCoords = Tile.getTextureCoords(tileType);
        
        mapVertexArray.add(new Vertex(
            new Vector2f(i * tileSize, j * tileSize), textureCoords)); // top left
        
        mapVertexArray.add(new Vertex(
            new Vector2f(i * tileSize, (j * tileSize) + tileSize), 
                Vector2f.add(textureCoords, new Vector2f(0, tileSize)))); // bottom left
        
        mapVertexArray.add(new Vertex(
            new Vector2f((i * tileSize) + tileSize, (j * tileSize) + tileSize), 
                Vector2f.add(textureCoords, new Vector2f(tileSize, tileSize)))); // bottom right
        
        mapVertexArray.add(new Vertex(
            new Vector2f((i * tileSize) + tileSize, j * tileSize), 
                Vector2f.add(textureCoords, new Vector2f(tileSize, 0)))); // top right
      }
    }
    RenderStates newStates = new RenderStates(mapTilesheetTexture);
    mapVertexArray.draw(target, newStates);
  }
  
  public boolean isValidLocation(Location l) {
    Vector2i coordinates = l.getPosition();
    return ((coordinates.x >= 0) && (coordinates.y >= 0) 
        && (coordinates.x < mapDimensions.x) && (coordinates.y < mapDimensions.y));
  }
}
