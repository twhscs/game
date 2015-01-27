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
    private final int TILE_SIZE = 32;
    private final int CHUNK_SIZE = 5;
    private final VertexArray[] VERTEX_ARRAYS;
    private final int CHUNK_COUNT;
    private final Player PLAYER;

    Map(int x, int y, Texture TILESHEET, RenderWindow WINDOW, Player PLAYER) {
        this.DIMENSIONS = new Vector2i(x, y);
        //TILE_ARRAY = new int[DIMENSIONS.x][DIMENSIONS.y];
        this.TILESHEET = TILESHEET;
        this.TILESHEET.setSmooth(false);
        this.WINDOW = WINDOW;
        this.TILE_ARRAY = new int[DIMENSIONS.x][DIMENSIONS.y];
        this.PLAYER = PLAYER;
        for (int[] row : TILE_ARRAY) {
            Arrays.fill(row, 0);
        }
        for (int i = 0; i < DIMENSIONS.x; i++) {
            for (int j = 0; j < DIMENSIONS.y; j++) {
                if (j == 5) {
                    TILE_ARRAY[i][j] = 0;
                } else {
                    TILE_ARRAY[i][j] = (int) (Math.random() * 4);
                }

            }
        }
        CHUNK_COUNT = (int) (Math.ceil((double) DIMENSIONS.x / CHUNK_SIZE) * Math.ceil((double) DIMENSIONS.y / CHUNK_SIZE));
        System.out.println(CHUNK_COUNT);
        VERTEX_ARRAYS = new VertexArray[CHUNK_COUNT];
        update();
    }

    public void update() {

        //int xChunks = (int) Math.ceil(DIMENSIONS.x / CHUNK_SIZE);
        int xChunks = (int) Math.ceil((double) DIMENSIONS.x / CHUNK_SIZE);
        int yChunks = (int) Math.ceil((double) DIMENSIONS.y / CHUNK_SIZE) - 1;
        System.out.println("xChunks " + xChunks + " yChunks " + yChunks);
        for (int i = 0; i < CHUNK_COUNT; i++) {
            VERTEX_ARRAYS[i] = new VertexArray(PrimitiveType.QUADS);
            int startX = (i % xChunks) * CHUNK_SIZE;
            int startY = (i / yChunks) * CHUNK_SIZE;
            System.out.println("id: " + i + " x: " + startX + " y: " + startY);
            for (int j = startX; j < startX + CHUNK_SIZE; j++) {
                for (int k = startY; k < startY + CHUNK_SIZE; k++) {

                    if (isValidPosition(new Vector2f(j, k))) {
                        int tile = TILE_ARRAY[j][k];

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

                        VERTEX_ARRAYS[i].add(new Vertex(new Vector2f(j * TILE_SIZE, k * TILE_SIZE), textureCoordinates));
                        VERTEX_ARRAYS[i].add(new Vertex(new Vector2f(j * TILE_SIZE, k * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(0, TILE_SIZE))));
                        VERTEX_ARRAYS[i].add(new Vertex(new Vector2f(j * TILE_SIZE + TILE_SIZE, k * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, TILE_SIZE))));
                        VERTEX_ARRAYS[i].add(new Vertex(new Vector2f(j * TILE_SIZE + TILE_SIZE, k * TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, 0))));
                    }
                }
            }
        }










        /*int tiles = 0;
        Vector2i size = WINDOW.getSize();
        int tilesX = (int) Math.ceil((size.x / TILE_SIZE) / 2.0);
        int tilesY = (int) Math.ceil((size.y / TILE_SIZE) / 2.0);
        System.out.println("tilesX " + tilesX + " tilesY " + tilesY);
        VERTEX_ARRAY.clear();*/
        /*for (int i = (int) (playerPosition.x - tilesX); i < playerPosition.x + tilesX; i++) {
            for (int j = (int) (playerPosition.y - tilesY); j < playerPosition.y + tilesY; j++) {
                if (isValidPosition(new Vector2f(i, j))) {
//                    System.out.println("("+i+","+j+")");
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
                    VERTEX_ARRAY.add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE), textureCoordinates));
                    VERTEX_ARRAY.add(new Vertex(new Vector2f(i * TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(0, TILE_SIZE))));
                    VERTEX_ARRAY.add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE + TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, TILE_SIZE))));
                    VERTEX_ARRAY.add(new Vertex(new Vector2f(i * TILE_SIZE + TILE_SIZE, j * TILE_SIZE), Vector2f.add(textureCoordinates, new Vector2f(TILE_SIZE, 0))));
                    tiles++;
                }
            }
        }*/
//        System.out.println("Tiles Drawn: " + tiles);
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
        RenderStates states = new RenderStates(TILESHEET);
//        VERTEX_ARRAY.draw(renderTarget, states);
        Vector2f playerPosition = PLAYER.getPosition();
        Vector2i windowSize = WINDOW.getSize();
        int tilesX = (int) Math.ceil(windowSize.x / TILE_SIZE);
        int tilesY = (int) Math.ceil(windowSize.y / TILE_SIZE);
        if (tilesX * tilesY > Math.pow(CHUNK_SIZE, 2)) {
            System.out.println("chunks too small");
        } else {
            // get chunk
            /*int xChunks = (int) Math.ceil((double) DIMENSIONS.x / CHUNK_SIZE);
            int yChunks = (int) Math.ceil((double) DIMENSIONS.y / CHUNK_SIZE) - 1;*/
            int chunkId = (int) ((playerPosition.x / CHUNK_SIZE) + ((playerPosition.y / CHUNK_SIZE) * (DIMENSIONS.x / CHUNK_SIZE)));
            System.out.println(chunkId);
            VERTEX_ARRAYS[chunkId].draw(renderTarget, states);
        }

       /*int xChunks = (int) Math.ceil((double) DIMENSIONS.x / CHUNK_SIZE);
        int yChunks = (int) Math.ceil((double) DIMENSIONS.y / CHUNK_SIZE) - 1;
        System.out.println("xChunks " + xChunks + " yChunks " + yChunks);
        for (int i = 0; i < CHUNK_COUNT; i++) {
            VERTEX_ARRAYS[i] = new VertexArray(PrimitiveType.QUADS);
            int startX = (i % xChunks) * CHUNK_SIZE;
            int startY = (i / yChunks) * CHUNK_SIZE;*/
    }

    public boolean isValidPosition(Vector2f position) {
        return (position.x >= 0 && position.y >= 0 && position.x < DIMENSIONS.x && position.y < DIMENSIONS.y);
    }
}
