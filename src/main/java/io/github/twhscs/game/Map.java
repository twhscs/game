package io.github.twhscs.game;

import io.github.twhscs.game.util.Perlin;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Map implements Drawable {
    private final Vector2i DIMENSIONS;
    private final int TILE_SIZE;
    private final float ZOOM;
    private final int CHUNK_SIZE;
    private final Texture TILE_SHEET;
    private final RenderWindow WINDOW;
    private final Terrain[][] TILE_ARRAY;
    private final int TOTAL_CHUNKS;
    private final int X_CHUNKS;
    private final VertexArray[] VERTEX_ARRAYS;
    private final Terrain GRASS;
    private final Terrain WATER;
    private final Terrain SAND;
    private final Terrain SNOW;
    private Player player;


    Map(int x, int y, int TILE_SIZE, float ZOOM, int CHUNK_SIZE, Texture TILE_SHEET, RenderWindow WINDOW) {
        this.DIMENSIONS = new Vector2i(x, y);
        this.TILE_SIZE = TILE_SIZE;
        this.ZOOM = ZOOM;
        this.CHUNK_SIZE = CHUNK_SIZE;
        this.TILE_SHEET = TILE_SHEET;
        this.WINDOW = WINDOW;
        // Calculate the amount of horizontal chunks.
        X_CHUNKS = (int) Math.ceil((double) DIMENSIONS.x / CHUNK_SIZE);
        // Calculate the amount of vertical chunks.
        int yChunks = (int) Math.ceil((double) DIMENSIONS.y / CHUNK_SIZE);
        // Calculate the total amount of chunks.
        TOTAL_CHUNKS = X_CHUNKS * yChunks;
        GRASS = new Terrain(true, new Vector2f(0, 352), true, false);
        WATER = new Terrain(false, new Vector2f(864, 160), false, true);
        SAND = new Terrain(true, new Vector2f(576, 352), true, false);
        SNOW = new Terrain(true, new Vector2f(576, 544), true, false);
        TILE_ARRAY = generateMap(DIMENSIONS.x, DIMENSIONS.y, 0, 3, 5);
        VERTEX_ARRAYS = new VertexArray[TOTAL_CHUNKS];
        // Load the tiles into the map.
        load();
    }

    public void setPlayer(Player player) {
        this.player = player;
        player.setMap(this);
    }

    private Terrain[][] generateMap(int w, int h, int min, int max, int octaves) {
        float[][] noise = Perlin.getNoise(w, h, octaves);
        Terrain[][] map = new Terrain[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                //map[x][y] = (int) (noise[x][y] * (max - min) + min);
                if (noise[x][y] > 0.8f)
                    map[x][y] = WATER;
                else if (noise[x][y] > 0.6f)
                    map[x][y] = SAND;
                else
                    map[x][y] = GRASS;
            }
        }
        return map;
    }

    private void load() {
        // Divide the map into smaller chunks.
        partition();
    }

    private Vector2f chunkIDToPosition(int chunkID) {
        // Use math to convert a chunkID to its top left position.
        // Chunk IDs start at 0
        return new Vector2f(chunkID % X_CHUNKS * CHUNK_SIZE, chunkID / X_CHUNKS * CHUNK_SIZE);
    }

    @Override
    public String toString() {
        return "Map{" +
                "TILE_SIZE=" + TILE_SIZE +
                ", DIMENSIONS=" + DIMENSIONS +
                ", ZOOM=" + ZOOM +
                ", CHUNK_SIZE=" + CHUNK_SIZE +
                ", TOTAL_CHUNKS=" + TOTAL_CHUNKS +
                ", X_CHUNKS=" + X_CHUNKS +
                ", VERTEX_ARRAYS=" + Arrays.toString(VERTEX_ARRAYS) +
                '}';
    }

    private boolean isValidChunkID(int chunkID) {
        return chunkID >= 0 && chunkID < TOTAL_CHUNKS;
    }

    private int positionToChunkID(Vector2f position) {
        // Use math to convert a position on the map to its corresponding chunk ID
        // Chunk IDs start at 0
        return ((int) position.x / CHUNK_SIZE) + (((int) position.y / CHUNK_SIZE) * X_CHUNKS);
    }

    public boolean isValidPosition(Vector2f position) {
        return position.x >= 0.0f && position.y >= 0.0f && position.x < DIMENSIONS.x && position.y < DIMENSIONS.y;
    }

    private void partition() {
        // TODO: Improve partitioning efficiency. Currently O(n^3).

        /*

        Partition the map into multiple vertex arrays for rendering.
        Vertex arrays speed up rendering time.
        We create these arrays only once as opposed to every frame for even greater optimization.

        See: http://www.sfml-dev.org/tutorials/2.0/graphics-vertex-array.php

         */

        // Loop through each chunk.
        for (int chunkID = 0; chunkID < TOTAL_CHUNKS; chunkID++) {
            // Initialize the chunk's vertex array.
            VERTEX_ARRAYS[chunkID] = new VertexArray(PrimitiveType.QUADS);
            // Get the top left corner of the current chunk.
            Vector2f position = chunkIDToPosition(chunkID);
            // Loop through the current chunk tile by tile.
            for (int i = (int) position.x; i < position.x + CHUNK_SIZE; i++) {
                for (int j = (int) position.y; j < position.y + CHUNK_SIZE; j++) {
                    // Make sure the current tile is valid.
                    if (isValidPosition(new Vector2f(i, j))) {
                        // Get the current tile.
                        final Terrain tile = TILE_ARRAY[i][j];
                        // Get the up index
                        int ui = j > 0 ? j - 1 : 0;
                        // Get the down index
                        int di = j < DIMENSIONS.y - 1 ? j + 1 : DIMENSIONS.y - 1;
                        // Get the left index
                        int li = i > 0 ? i - 1 : 0;
                        // Get the right index
                        int ri = i < DIMENSIONS.x - 1 ? i + 1 : DIMENSIONS.x - 1;
                        // Get the tile above
                        final Terrain up_tile = TILE_ARRAY[i][ui];
                        // Get the tile below
                        final Terrain down_tile = TILE_ARRAY[i][di];
                        // Get the tile to the left
                        final Terrain left_tile = TILE_ARRAY[li][j];
                        // Get the tile to the right
                        final Terrain right_tile = TILE_ARRAY[ri][j];
                        // Get the correct texture for the current tile.
                        Vector2f textureCoordinates = tile.getTextureCoordinates();
                        // Fix for a JSFML bug. See: http://en.sfml-dev.org/forums/index.php?topic=15889.0
                        textureCoordinates = Vector2f.add(textureCoordinates, new Vector2f(0.0f, -0.01f));
                        // Create and add a vertex for the bottom left corner of the tile.
                        VERTEX_ARRAYS[chunkID].add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE), textureCoordinates));
                        // Create and add a vertex for the top left corner of the tile.
                        VERTEX_ARRAYS[chunkID].add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(0, TILE_SIZE))));
                        // Create and add a vertex for the top right corner of the tile.
                        VERTEX_ARRAYS[chunkID].add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, TILE_SIZE))));
                        // Create and add a vertex for the bottom right corner of the tile.
                        VERTEX_ARRAYS[chunkID].add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, 0))));
                    }
                }
            }
        }
    }

    public void update() {
    }


    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        // TODO: Improve efficiency if required. There is no use in looping through tiles immediately adjacent to the start of the chunk.
        // Apply the tile sheet to the tiles.
        RenderStates states = new RenderStates(TILE_SHEET);
        // Get the player's current position.
        Vector2f playerPosition = player.getPosition();
        // Get the window's current size.
        Vector2i windowSize = WINDOW.getSize();

        // Determine how many tiles fit the window horizontally and vertically taking zoom into account, then halve both values.
        int xDistance = (int) Math.ceil(windowSize.x / (TILE_SIZE * 2 / ZOOM));
        int yDistance = (int) Math.ceil(windowSize.y / (TILE_SIZE * 2 / ZOOM));
        Vector2f distance = new Vector2f(xDistance + 1, yDistance + 1);

        // Create a rectangle representing the positions currently viewable by the player.
        FloatRect visibleArea = new FloatRect(playerPosition.x - distance.x, playerPosition.y - distance.y, distance.x * 2, distance.y * 2);
        // Create a set to keep track of the already rendered chunks.
        Set<Integer> renderedChunks = new HashSet<Integer>();
        // Loop through every position currently in view.
        for (float i = visibleArea.left; i <= visibleArea.left + visibleArea.width; i++) {
            for (float j = visibleArea.top; j <= visibleArea.top + visibleArea.height; j++) {
                // Convert the current position to a chunk ID.
                int chunkID = positionToChunkID(new Vector2f(i, j));
                // If the chunk is valid and hasn't been drawn yet, draw it.
                if (isValidChunkID(chunkID) && !renderedChunks.contains(chunkID)) {
                    // Draw the chunk vertex array with the tile sheet.
                    VERTEX_ARRAYS[chunkID].draw(renderTarget, states);
                    // Add the drawn chunk ID to the set to check against in order to save resources by not drawing it twice.
                    renderedChunks.add(chunkID);
                }
            }
        }
    }
}
