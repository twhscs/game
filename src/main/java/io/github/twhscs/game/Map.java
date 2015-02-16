package io.github.twhscs.game;

import io.github.twhscs.game.util.ResourceManager;
import io.github.twhscs.game.util.VectorHelper;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.HashSet;
import java.util.Set;

class Map implements Drawable {

    private static final int CHUNK_SIZE = 25;
    private static final int ANIMATION_FRAMES = 3;
    private static final int ANIMATION_SPEED = 3;
    private final Texture tileSheet;
    private final Vector2i dimensions;
    private final RenderWindow window;
    private final Terrain[][] tiles;
    private final int totalChunks;
    private final int xChunks;
    private final VertexArray[] vertexArrayBase;
    private final VertexArray[][] vertexArrayFrames;
    private final Generator generator;
    private Player player;
    private int animationFrame;

    public Map(RenderWindow window, Generator generator, Player player) {
        this.window = window;
        this.generator = generator;
        this.player = player;
        tileSheet = ResourceManager.getTexture("tiles");
        animationFrame = 0;
        dimensions = generator.getDimensions();
        tiles = generator.generate();
        xChunks = (int) Math.ceil((double) dimensions.x / CHUNK_SIZE);
        int yChunks = (int) Math.ceil((double) dimensions.y / CHUNK_SIZE);
        totalChunks = xChunks * yChunks;
        vertexArrayBase = new VertexArray[totalChunks];
        vertexArrayFrames = new VertexArray[totalChunks][ANIMATION_FRAMES];
        player.setMap(this);
        player.setPosition(VectorHelper.round(Vector2f.div(new Vector2f(dimensions), 2)));
        partition();
    }

    private Vector2f chunkToPosition(int chunk) {
        return new Vector2f(chunk % xChunks * CHUNK_SIZE, chunk / xChunks * CHUNK_SIZE);
    }

    private int positionToChunk(Vector2f position) {
        return ((int) position.x / CHUNK_SIZE) + (((int) position.y / CHUNK_SIZE) * xChunks);
    }

    private Terrain getTile(Vector2i position) {
        if (position.x >= 0 && position.x < dimensions.x && position.y >= 0 && position.y < dimensions.y) {
            return tiles[position.x][position.y];
        } else {
            return null;
        }

    }

    public boolean isEmptyPosition(Vector2i position) {
        return true;
    }

    private void partition() {

        /*

        Partition the map into multiple vertex arrays for rendering. Vertex arrays speed up rendering time. We create
        these arrays only once as opposed to every frame for even greater optimization.

        See: http://www.sfml-dev.org/tutorials/2.0/graphics-vertex-array.php

         */

        for (int currentChunk = 0; currentChunk < totalChunks; currentChunk++) {
            vertexArrayBase[currentChunk] = new VertexArray(PrimitiveType.QUADS);

            Vector2f position = chunkToPosition(currentChunk);

            for (int i = (int) position.x; i < position.x + CHUNK_SIZE; i++) {
                for (int j = (int) position.y; j < position.y + CHUNK_SIZE; j++) {
                    final Terrain tile = getTile(new Vector2i(i, j));
                    if (tile != null && !tile.isAnimated()) {
                        Vector2f textureCoordinates = tile.getTextureCoordinates();
                        Vector2f[] corners = new Vector2f[4];
                        corners[0] = textureCoordinates;
                        corners[1] = Vector2f.add(textureCoordinates, new Vector2f(0, App.TILE_SIZE));
                        corners[2] = Vector2f.add(textureCoordinates, new Vector2f(App.TILE_SIZE, App.TILE_SIZE));
                        corners[3] = Vector2f.add(textureCoordinates, new Vector2f(App.TILE_SIZE, 0));
                        boolean randomized = tile.isRandomized();
                        boolean flipped = false;
                        if (randomized) {
                            int rotations = (int) (Math.random() * 3) + 1;
                            for (int rotation = 0; rotation < rotations; rotation++) {
                                Vector2f temp = corners[3];
                                corners[3] = corners[2];
                                corners[2] = corners[1];
                                corners[1] = corners[0];
                                corners[0] = temp;
                            }

                            flipped = (Math.round(Math.random()) == 0);
                            if (flipped) {
                                Vector2f temp = corners[0];
                                corners[0] = corners[1];
                                corners[1] = temp;
                                temp = corners[2];
                                corners[2] = corners[3];
                                corners[3] = temp;
                            }
                        }
                        if (!randomized || flipped) {
                            for (int corner = 0; corner < 4; corner++) {
                                corners[corner] = Vector2f.add(corners[corner], new Vector2f(0.01f, -0.01f));
                            }
                        }
                        // Create and add a vertex for the bottom left corner of the tile.
                        vertexArrayBase[currentChunk]
                                .add(new Vertex(new Vector2f(i * App.TILE_SIZE, j * App.TILE_SIZE), corners[0]));
                        vertexArrayBase[currentChunk]
                                .add(new Vertex(new Vector2f(i * App.TILE_SIZE, j * App.TILE_SIZE + App.TILE_SIZE),
                                                corners[1]));
                        vertexArrayBase[currentChunk].add(new Vertex(
                                new Vector2f(i * App.TILE_SIZE + App.TILE_SIZE, j * App.TILE_SIZE + App.TILE_SIZE),
                                corners[2]));
                        vertexArrayBase[currentChunk]
                                .add(new Vertex(new Vector2f(i * App.TILE_SIZE + App.TILE_SIZE, j * App.TILE_SIZE),
                                                corners[3]));
                    }
                }
            }
        }

        /*// Loop through each chunk.
        for (int chunkID = 0; chunkID < TOTAL_CHUNKS; chunkID++) {
            // Initialize the chunk's vertex array.
            for (int i = 0; i < ANIMATION_FRAMES; i++) {
                VERTEX_ARRAYS[chunkID][i] = new VertexArray(PrimitiveType.QUADS);
            }
            // Get the top left corner of the current chunk.
            Vector2f position = chunkIDToPosition(chunkID);
            // Loop through the current chunk tile by tile.
            for (int i = (int) position.x; i < position.x + CHUNK_SIZE; i++) {
                for (int j = (int) position.y; j < position.y + CHUNK_SIZE; j++) {
                    // Make sure the current tile is valid.
                    if (isValidPosition(new Vector2f(i, j))) {
                        // Get the current tile.
                        final Terrain tile = TILE_ARRAY[i][j];
                        // Get the correct texture for the current tile.
                        Vector2f textureCoordinates = tile.getTextureCoordinates();
                        for (int frame = 0; frame < ANIMATION_FRAMES; frame++) {
                            Vector2f animatedTexture;
                            if (tile.isAnimated()) {
                                animatedTexture = Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE * frame, 0));
                            } else {
                                animatedTexture = textureCoordinates;
                            }
                            if (tile.isAnimated() || frame == 0) {
                                // Create a vector for each corner of the texture.
                                Vector2f[] positions = new Vector2f[4];
                                // Set each corner.
                                positions[0] = animatedTexture;
                                positions[1] = Vector2f.add(animatedTexture, new Vector2f(0, TILE_SIZE));
                                positions[2] = Vector2f.add(animatedTexture, new Vector2f(TILE_SIZE, TILE_SIZE));
                                positions[3] = Vector2f.add(animatedTexture, new Vector2f(TILE_SIZE, 0));
                                // Determine whether or not the tile is to be randomly rotated.
                                boolean random = tile.isRandomized();
                                boolean flipped = true;
                                if (random) {
                                    // Randomly choose 1 - 3 rotations.
                                    int rotations = (int) (Math.random() * 3) + 1;
                                    // For each rotation shift the coordinates in a circular fashion.
                                    for (int k = 0; k < rotations; k++) {
                                        Vector2f temp;
                                        temp = positions[3];
                                        positions[3] = positions[2];
                                        positions[2] = positions[1];
                                        positions[1] = positions[0];
                                        positions[0] = temp;
                                    }
                                    // Randomly determine whether or not to flip with a 50-50 chance.
                                    flipped = (Math.random() < 0.5);
                                    if (flipped) {
                                        // If flipped, flip the texture coordinates.
                                        Vector2f temp;
                                        temp = positions[0];
                                        positions[0] = positions[1];
                                        positions[1] = temp;
                                        temp = positions[2];
                                        positions[2] = positions[3];
                                        positions[3] = temp;
                                    }
                                }
                                if (!tile.isRandomized() || flipped) {
                                    // Fix for a JSFML bug. See: http://en.sfml-dev.org/forums/index.php?topic=15889.0
                                    for (int k = 0; k < 4; k++) {
                                        positions[k] = Vector2f.add(positions[k], new Vector2f(0.01f, -0.01f));
                                    }
                                }
                                // Create and add a vertex for the bottom left corner of the tile.
                                VERTEX_ARRAYS[chunkID][frame].add(new Vertex(new Vector2f(i * TILE_SIZE, j *
                                                                                                         TILE_SIZE),
                                                                             positions[0]));
                                // Create and add a vertex for the top left corner of the tile.
                                VERTEX_ARRAYS[chunkID][frame].add(new Vertex(new Vector2f(i * TILE_SIZE, j *
                                                                                                         TILE_SIZE +
                                                                                                         TILE_SIZE),
                                                                             positions[1]));
                                // Create and add a vertex for the top right corner of the tile.
                                VERTEX_ARRAYS[chunkID][frame].add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE,
                                                                                          j * TILE_SIZE + TILE_SIZE),
                                                                             positions[2]));
                                // Create and add a vertex for the bottom right corner of the tile.
                                VERTEX_ARRAYS[chunkID][frame].add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE,
                                                                                          j * TILE_SIZE),
                                                                             positions[3]));
                            }
                        }
                    }
                }
            }
        }*/
    }

    public void update() {
        /*animationFrame++;
        if (animationFrame + 1 >= ANIMATION_FRAMES * ANIMATION_SPEED) {
            animationFrame = 0;
        }*/
    }

    /*private int positionToChunkID(Vector2f position) {
        // Use math to convert a position on the map to its corresponding chunk ID
        // Chunk IDs start at 0
        return ((int) position.x / CHUNK_SIZE) + (((int) position.y / CHUNK_SIZE) * X_CHUNKS);
    }

    private boolean isValidChunkID(int chunkID) {
        return chunkID >= 0 && chunkID < TOTAL_CHUNKS;
    }*/

    private boolean isValidChunk(int chunk) {
        return chunk >= 0 && chunk < totalChunks;
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        //int adjustedFrame = Math.round((animationFrame * ANIMATION_FRAMES) / (ANIMATION_FRAMES * ANIMATION_SPEED));
        // TODO: Improve efficiency if required. There is no use in looping through tiles immediately adjacent to the
        // start of the chunk.
        // Apply the tile sheet to the tiles.
        RenderStates states = new RenderStates(tileSheet);
        // Get the player's current position.
        Vector2f playerPosition = player.getPosition();
        // Get the window's current size.
        Vector2i windowSize = window.getSize();

        // Determine how many tiles fit the window horizontally and vertically taking zoom into account, then halve
        // both values.
        int xDistance = (int) Math.ceil(windowSize.x / (App.TILE_SIZE * 2 / App.ZOOM));
        int yDistance = (int) Math.ceil(windowSize.y / (App.TILE_SIZE * 2 / App.ZOOM));
        Vector2f distance = new Vector2f(xDistance + 1, yDistance + 1);

        // Create a rectangle representing the positions currently viewable by the player.
        FloatRect visibleArea =
                new FloatRect(playerPosition.x - distance.x, playerPosition.y - distance.y, distance.x * 2,
                              distance.y * 2);
        // Create a set to keep track of the already rendered chunks.
        Set<Integer> renderedChunks = new HashSet<Integer>();
        // Loop through every position currently in view.
        for (float i = visibleArea.left; i <= visibleArea.left + visibleArea.width; i++) {
            for (float j = visibleArea.top; j <= visibleArea.top + visibleArea.height; j++) {
                // Convert the current position to a chunk ID.
                int chunkID = positionToChunk(new Vector2f(i, j));
                // If the chunk is valid and hasn't been drawn yet, draw it.
                if (isValidChunk(chunkID) && !renderedChunks.contains(chunkID)) {
                    // Draw the chunk vertex array with the tile sheet.
                    vertexArrayBase[chunkID].draw(renderTarget, states);
                    /*if (adjustedFrame > 0) {
                        VERTEX_ARRAYS[chunkID][adjustedFrame].draw(renderTarget, states);
                    }*/
                    // Add the drawn chunk ID to the set to check against in order to save resources by not drawing
                    // it twice.
                    renderedChunks.add(chunkID);
                }
            }
        }
    }
}
