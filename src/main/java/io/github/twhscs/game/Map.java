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
    private final VertexArray VERTEX_ARRAY;

    Map(int x, int y, Texture TILESHEET, RenderWindow WINDOW) {
        this.DIMENSIONS = new Vector2i(x, y);
        TILE_ARRAY = new int[DIMENSIONS.x][DIMENSIONS.y];
        this.TILESHEET = TILESHEET;
        this.WINDOW = WINDOW;
        VERTEX_ARRAY = new VertexArray(PrimitiveType.QUADS);
        for (int[] row : TILE_ARRAY) {
            Arrays.fill(row, 0);
        }
        for(int i = 0; i < DIMENSIONS.x; i++) {
            for(int j = 0; j < DIMENSIONS.y; j++) {
                TILE_ARRAY[i][j] = (int) (Math.random() * 4);
            }
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
        VERTEX_ARRAY.clear();
        final int TILE_SIZE = 32;
        for (int i = 0; i < DIMENSIONS.x; i++) {
            for (int j = 0; j < DIMENSIONS.y; j++) {
                int tile = TILE_ARRAY[i][j];
                Vector2f textureCoordinates;
                switch(tile) {
                    case 0:
                        textureCoordinates = new Vector2f(576, 352);
                        break;
                    case 1:
                        textureCoordinates = new Vector2f(192, 352);
                        break;
                    case 2:
                        textureCoordinates = new Vector2f(480, 544);
                        break;
                    case 3:
                        textureCoordinates = new Vector2f(576, 544);
                        break;
                    default:
                        textureCoordinates = null;
                }
                VERTEX_ARRAY.add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE), textureCoordinates));
                VERTEX_ARRAY.add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(0, TILE_SIZE))));
                VERTEX_ARRAY.add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, TILE_SIZE))));
                VERTEX_ARRAY.add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, 0))));
            }
        }
        RenderStates states = new RenderStates(TILESHEET);
        VERTEX_ARRAY.draw(renderTarget, states);
    }
}
