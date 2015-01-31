package io.github.twhscs.game;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.HashSet;
import java.util.Set;

class Map implements Drawable {
    private final int TILE_SIZE = 32;
    private final int CHUNK_SIZE = 10;
    private final Vector2i DIMENSIONS;
    private final int[][] TILE_ARRAY;
    private final Texture TILE_SHEET;
    private final RenderWindow WINDOW;
    private final Player PLAYER;
    private final int TOTAL_CHUNKS;
    private final int X_CHUNKS;
    private final int Y_CHUNKS;
    private final VertexArray[] VERTEX_ARRAYS;
    private boolean LOADED;


    Map(int x, int y, Texture TILE_SHEET, RenderWindow WINDOW, Player PLAYER) {
        //this.CHUNK_SIZE = (int) Math.max(Math.ceil(WINDOW.getSize().x / TILE_SIZE), Math.ceil(WINDOW.getSize().y / TILE_SIZE)) + 16;
        this.DIMENSIONS = new Vector2i(x, y);
        this.TILE_ARRAY = new int[DIMENSIONS.x][DIMENSIONS.y];
        this.TILE_SHEET = TILE_SHEET;
        this.WINDOW = WINDOW;
        this.PLAYER = PLAYER;
        for (int i = 0; i < DIMENSIONS.x; i++) {
            for (int j = 0; j < DIMENSIONS.y; j++) {
                TILE_ARRAY[i][j] = (int) (Math.random() * 2);
            }
        }
        if (CHUNK_SIZE > DIMENSIONS.x || CHUNK_SIZE > DIMENSIONS.y) {
            throw new IllegalArgumentException("Chunk size must be smaller than map dimensions and greater than 0.");
        }
        X_CHUNKS = (int) Math.ceil((double) DIMENSIONS.x / CHUNK_SIZE);
        Y_CHUNKS = (int) Math.ceil((double) DIMENSIONS.y / CHUNK_SIZE);
        TOTAL_CHUNKS = X_CHUNKS * Y_CHUNKS;
        VERTEX_ARRAYS = new VertexArray[TOTAL_CHUNKS];
        //out.println("Total Chunks: " + TOTAL_CHUNKS + " X: " + X_CHUNKS + " Y: " + Y_CHUNKS);
        LOADED = false;
        update();
    }

    public void update() {
        for (int chunkID = 0; chunkID < TOTAL_CHUNKS; chunkID++) {
            VERTEX_ARRAYS[chunkID] = new VertexArray(PrimitiveType.QUADS);
            int startX = (chunkID % X_CHUNKS) * CHUNK_SIZE;
            int startY = (chunkID / X_CHUNKS) * CHUNK_SIZE;
            if (startY < 0) {
                startY = 0;
            }
            //System.out.println("id: " + chunkID + " x: " + startX + " y: " + startY);
//            System.out.println(chunkID + " out of " + TOTAL_CHUNKS);
            for (int i = startX; i < startX + CHUNK_SIZE; i++) {
                for (int j = startY; j < startY + CHUNK_SIZE; j++) {
                    if (isValidPosition(new Vector2f(i, j))) {
                        int tile = TILE_ARRAY[i][j];
                        Vector2f textureCoordinates = null;
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
                        }
                        textureCoordinates = Vector2f.add(textureCoordinates, new Vector2f(0f, -0.01f)); // Fix for JSFML bug. See: http://en.sfml-dev.org/forums/index.php?topic=15889.0
                        VERTEX_ARRAYS[chunkID].add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE), textureCoordinates));
                        VERTEX_ARRAYS[chunkID].add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(0, TILE_SIZE))));
                        VERTEX_ARRAYS[chunkID].add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, TILE_SIZE))));
                        VERTEX_ARRAYS[chunkID].add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, 0))));
                    }
                }
            }
        }
        LOADED = true;
    }

    private int positionToChunkID(Vector2f position) {
        return (((int) position.x / CHUNK_SIZE) + (((int) position.y / CHUNK_SIZE) * X_CHUNKS));
    }

    private Vector2f chunkIDToPosition(int chunkID) {
        int startX = (chunkID % X_CHUNKS) * CHUNK_SIZE;
        int startY = (chunkID / X_CHUNKS) * CHUNK_SIZE;
        return new Vector2f(startX, startY);
    }

    private boolean isValidDifferentChunk(Vector2f position, Vector2f newPosition, int chunkID) {
        int newChunkID = positionToChunkID(Vector2f.add(position, newPosition));
        if (newChunkID >= 0 && newChunkID < TOTAL_CHUNKS) {
            int abs = Math.abs(newChunkID - chunkID);
            return abs <= 1 || abs <= X_CHUNKS || abs <= X_CHUNKS + 1 || abs <= X_CHUNKS - 1;
        } else {
            return false;
        }
    }

    private boolean isValidChunk(int chunkID) {
        return chunkID >= 0 && chunkID < TOTAL_CHUNKS;
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        if (LOADED) {
            RenderStates states = new RenderStates(TILE_SHEET);
            Vector2f playerPosition = PLAYER.getPosition();
            Vector2i windowSize = WINDOW.getSize();

            // int chunkID = positionToChunkID(playerPosition);
            //final int DRAW_DISTANCE = Math.max(((windowSize.x / TILE_SIZE) / 2), (windowSize.y / TILE_SIZE) / 2);
            //final int radius = Math.max(((windowSize.x / TILE_SIZE) / 2), (windowSize.y / TILE_SIZE) / 2);
            // Vector2f chunkStart = chunkIDToPosition(chunkID);
            // Vector2f chunkEnd = Vector2f.add(chunkStart, new Vector2f(CHUNK_SIZE, CHUNK_SIZE));
            //System.out.println(chunkStart);
            //System.out.println(chunkEnd);

            final int X_RADIUS = (int) Math.ceil((windowSize.x / TILE_SIZE) / 2.0f);
            final int Y_RADIUS = (int) Math.ceil((windowSize.y / TILE_SIZE) / 2.0f);
            //System.out.println(Y_RADIUS);
            //  float west = chunkEnd.x - playerPosition.x;
            //float east = playerPosition.x - chunkStart.x;
            // float north = playerPosition.y - chunkStart.y;
            // float south = chunkEnd.y - playerPosition.y;
            // if(west < X_RADIUS) {
            //   int chunksToRender = (int) (Math.ceil(X_RADIUS / CHUNK_SIZE));
            // System.out.println(chunksToRender);
            //   }
            // System.out.println("START");
            FloatRect visibleArea = new FloatRect(playerPosition.x - X_RADIUS, playerPosition.y - Y_RADIUS, X_RADIUS * 2, Y_RADIUS * 2);
            //System.out.println(visibleArea);
            Set<Integer> rendered = new HashSet<Integer>();
            for (float i = visibleArea.left; i <= visibleArea.left + visibleArea.width; i++) {
                for (float j = visibleArea.top; j <= visibleArea.top + visibleArea.height; j++) {

                    int chunkID = positionToChunkID(new Vector2f(i, j));
                    // System.out.println("X: " + i + " Y: " + j + " ID: " + chunkID);
                    if (isValidChunk(chunkID) && !rendered.contains(chunkID)) {
                        VERTEX_ARRAYS[chunkID].draw(renderTarget, states);
                        rendered.add(chunkID);
                    }

                }
            }
            //System.out.println(visibleArea);

            // VERTEX_ARRAYS[chunkID].draw(renderTarget, states);

            /*Vector2f north = new Vector2f(0, -DRAW_DISTANCE);
            Vector2f south = new Vector2f(0, DRAW_DISTANCE);
            Vector2f west = new Vector2f(-DRAW_DISTANCE, 0);
            Vector2f east = new Vector2f(DRAW_DISTANCE, 0);
            Vector2f northWest = Vector2f.add(north, west);
            Vector2f northEast = Vector2f.add(north, east);
            Vector2f southWest = Vector2f.add(south, west);
            Vector2f southEast = Vector2f.add(south, east);


            int chunksRendering = 0;
            VERTEX_ARRAYS[chunkID].draw(renderTarget, states);
            chunksRendering++;

            if (isValidDifferentChunk(playerPosition, north, chunkID)) {
                VERTEX_ARRAYS[positionToChunkID(Vector2f.add(playerPosition, north))].draw(renderTarget, states);
                chunksRendering++;
            }
            if (isValidDifferentChunk(playerPosition, south, chunkID)) {
//                System.out.println("SOUTH");
                VERTEX_ARRAYS[positionToChunkID(Vector2f.add(playerPosition, south))].draw(renderTarget, states);
                chunksRendering++;
            }
            if (isValidDifferentChunk(playerPosition, west, chunkID)) {
                VERTEX_ARRAYS[positionToChunkID(Vector2f.add(playerPosition, west))].draw(renderTarget, states);
                chunksRendering++;
            }
            if (isValidDifferentChunk(playerPosition, east, chunkID)) {
                //System.out.println("yes");
                VERTEX_ARRAYS[positionToChunkID(Vector2f.add(playerPosition, east))].draw(renderTarget, states);
                chunksRendering++;
            }
            if (isValidDifferentChunk(playerPosition, northWest, chunkID)) {
                VERTEX_ARRAYS[positionToChunkID(Vector2f.add(playerPosition, northWest))].draw(renderTarget, states);
                chunksRendering++;
            }
            if (isValidDifferentChunk(playerPosition, northEast, chunkID)) {
                VERTEX_ARRAYS[positionToChunkID(Vector2f.add(playerPosition, northEast))].draw(renderTarget, states);
                chunksRendering++;
            }
            if (isValidDifferentChunk(playerPosition, southWest, chunkID)) {
                VERTEX_ARRAYS[positionToChunkID(Vector2f.add(playerPosition, southWest))].draw(renderTarget, states);
                chunksRendering++;
            }
            if (isValidDifferentChunk(playerPosition, southEast, chunkID)) {
                VERTEX_ARRAYS[positionToChunkID(Vector2f.add(playerPosition, southEast))].draw(renderTarget, states);
                chunksRendering++;
            }
//            System.out.println(chunksRendering);*/
        }
    }

    public boolean isValidPosition(Vector2f position) {
        return (position.x >= 0 && position.y >= 0 && position.x < DIMENSIONS.x && position.y < DIMENSIONS.y);
    }
}
