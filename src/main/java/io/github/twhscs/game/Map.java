package io.github.twhscs.game;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.Arrays;

class Map implements Drawable {
    private final int[][] TILE_ARRAY;
    private final Texture TILESHEET;
    private final Vector2i DIMENSIONS;
    private final RenderWindow WINDOW;

    Map(int x, int y, Texture TILESHEET, RenderWindow WINDOW) {
        this.DIMENSIONS = new Vector2i(x, y);
        TILE_ARRAY = new int[DIMENSIONS.x][DIMENSIONS.y];
        this.TILESHEET = TILESHEET;
        this.WINDOW = WINDOW;
        for (int[] row : TILE_ARRAY) {
            Arrays.fill(row, 0);
        }
    }

    @Override
    public String toString() {
        String string = "Map " + DIMENSIONS.x + "x" + DIMENSIONS.y + "\n";
        for (int[] row : TILE_ARRAY) {
            for (int tile : row) {
                string += tile + " ";
            }
            string += "\n";
        }
        return string;
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        VertexArray vertexArray = new VertexArray(PrimitiveType.QUADS);
        final int TILE_SIZE = 32;
        for (int i = 0; i < DIMENSIONS.x; i++) {
            for (int j = 0; j < DIMENSIONS.y; j++) {
                int tile = TILE_ARRAY[i][j];
                Vector2f textureCoordinates = new Vector2f(416, 576);
                vertexArray.add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE), textureCoordinates));
                vertexArray.add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(0, TILE_SIZE))));
                vertexArray.add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, TILE_SIZE))));
                vertexArray.add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, 0))));
            }
        }
        RenderStates states = new RenderStates(TILESHEET);
        vertexArray.draw(renderTarget, states);
    }
}
