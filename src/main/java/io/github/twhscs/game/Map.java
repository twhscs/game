package io.github.twhscs.game;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

class Map implements Drawable {
    private final int TILE_SIZE = 32;
    private final int CHUNK_SIZE = 25;
    private final Vector2i DIMENSIONS;
    private final int[][] TILE_ARRAY;
    private final Texture TILE_SHEET;
    private final RenderWindow WINDOW;
    private final Player PLAYER;
    private final int TOTAL_CHUNKS;
    private final int X_CHUNKS;
    private final int Y_CHUNKS;
    private final VertexArray[] VERTEX_ARRAYS;


    Map(int x, int y, Texture TILE_SHEET, RenderWindow WINDOW, Player PLAYER) {
        this.DIMENSIONS = new Vector2i(x, y);
        this.TILE_ARRAY = new int[DIMENSIONS.x][DIMENSIONS.y];
        this.TILE_SHEET = TILE_SHEET;
        this.WINDOW = WINDOW;
        this.PLAYER = PLAYER;
        for (int i = 0; i < DIMENSIONS.x; i++) {
            for (int j = 0; j < DIMENSIONS.y; j++) {
                TILE_ARRAY[i][j] = (int) (Math.random() * 4);
            }
        }
        if (CHUNK_SIZE > DIMENSIONS.x || CHUNK_SIZE > DIMENSIONS.y || CHUNK_SIZE <= 0) {
            throw new IllegalArgumentException("Chunk size must be smaller than map dimensions and greater than 0.");
        }
        X_CHUNKS = (int) Math.ceil((double) DIMENSIONS.x / CHUNK_SIZE);
        Y_CHUNKS = (int) Math.ceil((double) DIMENSIONS.y / CHUNK_SIZE);
        TOTAL_CHUNKS = X_CHUNKS * Y_CHUNKS;
        VERTEX_ARRAYS = new VertexArray[TOTAL_CHUNKS];
        System.out.println("Total Chunks: " + TOTAL_CHUNKS + " X: " + X_CHUNKS + " Y: " + Y_CHUNKS);
        update();
    }

    public void update() {
        for (int chunkID = 0; chunkID < TOTAL_CHUNKS; chunkID++) {
            VERTEX_ARRAYS[chunkID] = new VertexArray(PrimitiveType.QUADS);
            int startX = (chunkID % X_CHUNKS) * CHUNK_SIZE;
            int startY = (int) ((int) (((double) chunkID) / Y_CHUNKS) * CHUNK_SIZE);
            System.out.println("id: " + chunkID + " x: " + startX + " y: " + startY);
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
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        RenderStates states = new RenderStates(TILE_SHEET);
        Vector2f playerPosition = PLAYER.getPosition();
        Vector2i windowSize = WINDOW.getSize();

        int tilesX = (int) Math.ceil(windowSize.x / TILE_SIZE);
        int tilesY = (int) Math.ceil(windowSize.y / TILE_SIZE);

        /*if (tilesX * tilesY > Math.pow(CHUNK_SIZE, 2)) {
            System.out.println("chunks too small");
        } else {
            // get
            /*int xChunks = (int) Math.ceil((double) DIMENSIONS.x / CHUNK_SIZE);
            int yChunks = (int) Math.ceil((double) DIMENSIONS.y / CHUNK_SIZE) - 1;*/


        /*int chunkId = (int) (playerPosition.x / CHUNK_SIZE) + ((int) (playerPosition.y / CHUNK_SIZE) * X_CHUNKS);
        System.out.println((int) (playerPosition.x / CHUNK_SIZE));
        System.out.println((int) (((playerPosition.y / CHUNK_SIZE) * X_CHUNKS)));
        System.out.println(chunkId);
        VERTEX_ARRAYS[chunkId].draw(renderTarget, states);*/

        for (VertexArray vertexes : VERTEX_ARRAYS) {
            vertexes.draw(renderTarget, states);
        }

        //}
    }

    public boolean isValidPosition(Vector2f position) {
        return (position.x >= 0 && position.y >= 0 && position.x < DIMENSIONS.x && position.y < DIMENSIONS.y);
    }
}
