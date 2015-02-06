package io.github.twhscs.game.util;

/**
 * Created by Kyle Lee on 2/5/2015.
 */
public class Terrain {
    public static int[][] generateMap(int w, int h, int min, int max, int octaves) {
        float[][] noise = Perlin.getNoise(w, h, octaves);
        int[][] map = new int[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                map[x][y] = (int) (noise[x][y] * (max - min) + min);
            }
        }
        return map;
    }
}
