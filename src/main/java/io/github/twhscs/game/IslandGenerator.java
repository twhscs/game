package io.github.twhscs.game;

import io.github.twhscs.game.util.Perlin;
import org.jsfml.system.Vector2i;

class IslandGenerator implements Generatable {
    private final Vector2i DIMENSIONS;
    private final int OCTAVES;

    public IslandGenerator(Vector2i DIMENSIONS, int OCTAVES) {
        this.DIMENSIONS = DIMENSIONS;
        this.OCTAVES = OCTAVES;
    }

    @Override
    public Terrain[][] generate() {
        float[][] noise = Perlin.getNoise(DIMENSIONS, OCTAVES);
        Terrain[][] map = new Terrain[DIMENSIONS.x][DIMENSIONS.y];
        for (int x = 0; x < DIMENSIONS.x; x++) {
            for (int y = 0; y < DIMENSIONS.y; y++) {
                if (noise[x][y] > 0.8f)
                    map[x][y] = Terrain.WATER;
                else if (noise[x][y] > 0.6f)
                    map[x][y] = Terrain.SAND;
                else
                    map[x][y] = Terrain.GRASS;

            }
        }
        return map;
    }

    public Vector2i getDimensions() {
        return DIMENSIONS;
    }
}
