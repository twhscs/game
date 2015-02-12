package io.github.twhscs.game;

import io.github.twhscs.game.util.Perlin;
import org.jsfml.system.Vector2i;

class IslandGenerator implements Generatable {
    private final Vector2i dimensions;
    private final int octaves;

    public IslandGenerator(Vector2i dimensions, int octaves) {
        this.dimensions = dimensions;
        this.octaves = octaves;
    }

    @Override
    public Terrain[][] generate() {
        float[][] noise = Perlin.getNoise(dimensions, octaves);
        Terrain[][] map = new Terrain[dimensions.x][dimensions.y];
        for (int x = 0; x < dimensions.x; x++) {
            for (int y = 0; y < dimensions.y; y++) {
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
        return dimensions;
    }
}
