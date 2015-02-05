package io.github.twhscs.game.util;

/**
 * Created by Kyle Lee on 2/5/2015.
 */
public class Terrain {
    public static int[][] generateMap (int width, int height, int min, int max, int octaveCount) {
        float[][] baseNoise = Perlin.generateWhiteNoise(width, height);
        float[][] noise = Perlin.generatePerlinNoise(baseNoise, octaveCount);
        int[][] map = new int[width][height];
        int range = max - min;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = (int)(noise[x][y] * range + min);
            }
        }
        return map;
    }
}
