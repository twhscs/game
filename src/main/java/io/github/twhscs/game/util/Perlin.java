package io.github.twhscs.game.util;

public class Perlin {
    /**
     * Gets a two-dimensional float matrix of Perlin noise.
     * @param w The width of the matrix
     * @param h The height of the matrix
     * @param o The number of octaves to account for
     * @return a two-dimensional float array of Perlin noise.
     */
    public static float[][] getNoise(int w, int h, int o) {
        final float[][] NOISE = new float[w][h];
        final float[][][] SMOOTH_NOISE = new float[o][w][h];
        final float[][] PERLIN_NOISE = new float[w][h];
        final float PERSISTENCE = 0.05f;
        float amplitude = 1.0f;
        float amplitudeSum = 0.0f;

        //Generate base noise
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if ((y < h/10 || y > h - h/10) || (x < w/10 || x > w - w/10))
                    NOISE[x][y] = 0.9999f;
                else
                    NOISE[x][y] = (float) Math.random();
            }
        }

        // For each octave generate smooth noise
        for (int i = 0; i < o; i++) {
            final int period = 1 << o;
            float frequency = 1.0f / period;
            for (int x = 0; x < w; x++) {
                int x0 = (x / period) * period;
                int x1 = (x0 + period) % w;
                float x_ratio = (x - x0) * frequency;
                for (int y = 0; y < h; y++) {
                    int y0 = (y / period) * period;
                    int y1 = (y0 + period) % h;
                    float y_ratio = (y - y0) * frequency;
                    float top = interpolate(NOISE[x0][y0], NOISE[x1][y0], x_ratio);
                    float bottom = interpolate(NOISE[x0][y1], NOISE[x1][y1], x_ratio);
                    SMOOTH_NOISE[i][x][y] = interpolate(top, bottom, y_ratio);
                }
            }
        }

        for (int i = 0; i < o; i++) {
            amplitude *= PERSISTENCE;
            amplitudeSum += amplitude;
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    PERLIN_NOISE[x][y] += SMOOTH_NOISE[i][x][y] * amplitude;
                }
            }
        }

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                PERLIN_NOISE[x][y] /= amplitudeSum;
            }
        }

        return PERLIN_NOISE;
    }

    /**
     * Gets the linear interpolation between two points given a ratio.
     * @param x0 The starting point.
     * @param x1 The ending point.
     * @param m The ratio to interpolate by.
     * @return the linear interpolation between x0 and x1 by m.
     */
    private static float interpolate(float x0, float x1, float m) {
        return x0 + m * (x1 - x0);
    }
}
