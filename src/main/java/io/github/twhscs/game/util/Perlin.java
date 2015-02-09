package io.github.twhscs.game.util;

public class Perlin {
    public static float interpolate(float x0, float x1, float m) {
        return x0 + m * (x1 - x0);
    }
    /*
    w = width
    h = height
    o = number of octaves
     */
    public static float[][] getNoise(int w, int h, int o) {
        float[][] noise = new float[w][h];
        float[][][] smoothNoise = new float[o][w][h];
        float persistence = 0.05f;
        //Generate base noise
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if ((y < h/10 || y > h - h/10) || (x < w/10 || x > w - w/10))
                    noise[x][y] = 0.9999f;
                else
                    noise[x][y] = (float) Math.random();
            }
        }
        // For each octave generate smooth noise
        for (int i = 0; i < o; i++) {
            int period = 1 << o;
            float frequency = 1.0f / period;
            for (int x = 0; x < w; x++) {
                int x0 = (x / period) * period;
                int x1 = (x0 + period) % w;
                float x_ratio = (x - x0) * frequency;
                for (int y = 0; y < h; y++) {
                    int y0 = (y / period) * period;
                    int y1 = (y0 + period) % h;
                    float y_ratio = (y - y0) * frequency;
                    float top = interpolate(noise[x0][y0], noise[x1][y0], x_ratio);
                    float bottom = interpolate(noise[x0][y1], noise[x1][y1], x_ratio);
                    smoothNoise[i][x][y] = interpolate(top, bottom, y_ratio);
                }
            }
        }

        float[][] perlinNoise = new float[w][h];
        float amplitude = 1.0f;
        float amplitudeSum = 0.0f;
        for (int i = 0; i < o; i++) {
            amplitude *= persistence;
            amplitudeSum += amplitude;
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    perlinNoise[x][y] += smoothNoise[i][x][y] * amplitude;
                }
            }
        }

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                perlinNoise[x][y] /= amplitudeSum;
            }
        }

        return perlinNoise;
    }
}
