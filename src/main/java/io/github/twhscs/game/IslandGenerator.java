package io.github.twhscs.game;

import io.github.twhscs.game.util.Perlin;
import org.jsfml.system.Vector2i;

class IslandGenerator extends Generator {

    private final int octaves;

    public IslandGenerator(Vector2i dimensions, int octaves) {
        super(dimensions);
        this.octaves = octaves;
    }

    @Override
    public Terrain[][] generate() {
        float[][] noise = Perlin.noise(dimensions, octaves);
        Terrain[][] map = new Terrain[dimensions.x][dimensions.y];
        for (int i = 0; i < dimensions.x; i++) {
            for (int j = 0; j < dimensions.y; j++) {
                if (noise[i][j] > 0.8f) {
                    map[i][j] = Terrain.WATER;
                } else if (noise[i][j] > 0.6f) {
                    map[i][j] = Terrain.SAND;
                } else {
                    map[i][j] = Terrain.GRASS;
                }
            }
        }
        return map;
    }

}
