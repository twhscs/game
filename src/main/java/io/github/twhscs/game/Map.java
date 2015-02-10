package io.github.twhscs.game;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.*;

class Map implements Drawable {
    private final Vector2i DIMENSIONS;
    private final int TILE_SIZE;
    private final float ZOOM;
    private final int CHUNK_SIZE;
    private final Texture TILE_SHEET;
    private final RenderWindow WINDOW;
    private final int[][] TILE_ARRAY;
    private final int TOTAL_CHUNKS;
    private final int X_CHUNKS;
    private final VertexArray[] VERTEX_ARRAYS;
    private final List<Entity> ENTITIES;
    private Player player;


    Map(int x, int y, int TILE_SIZE, float ZOOM, int CHUNK_SIZE, Texture TILE_SHEET, RenderWindow WINDOW) {
        this.DIMENSIONS = new Vector2i(x, y);
        this.TILE_SIZE = TILE_SIZE;
        this.ZOOM = ZOOM;
        this.CHUNK_SIZE = CHUNK_SIZE;
        this.TILE_SHEET = TILE_SHEET;
        this.WINDOW = WINDOW;
        TILE_ARRAY = new int[DIMENSIONS.x][DIMENSIONS.y];
        // Calculate the amount of horizontal chunks.
        X_CHUNKS = (int) Math.ceil((double) DIMENSIONS.x / CHUNK_SIZE);
        // Calculate the amount of vertical chunks.
        int yChunks = (int) Math.ceil((double) DIMENSIONS.y / CHUNK_SIZE);
        // Calculate the total amount of chunks.
        TOTAL_CHUNKS = X_CHUNKS * yChunks;
        VERTEX_ARRAYS = new VertexArray[TOTAL_CHUNKS];
        ENTITIES = new ArrayList<Entity>();
        // Load the tiles into the map.
        load();
    }

    public void setEntity(Entity entity) {
        ENTITIES.add(entity);
        entity.setMap(this);
    }

    public void setPlayer(Player player) {
        this.player = player;
        player.setMap(this);
    }

    private void load() {
        // Initialize each tile with a random number for now.
        // TODO: Add random terrain generation.
        for (int i = 0; i < DIMENSIONS.x; i++) {
            for (int j = 0; j < DIMENSIONS.y; j++) {
                TILE_ARRAY[i][j] = (int) (Math.random() * 4);
            }
        }
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
                        int tile = TILE_ARRAY[i][j];
                        // Get the correct texture for the current tile.
                        Vector2f textureCoordinates;
                        switch (tile) {
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
                                textureCoordinates = new Vector2f(0, 0);
                                break;
                        }
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

    public void updateEntities() {
        for (Entity e : ENTITIES) {
            e.update();
        }
    }

    public void update() {
        updateEntities();
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
        for (Entity e : ENTITIES) {
            WINDOW.draw(e);
        }
    }
}
