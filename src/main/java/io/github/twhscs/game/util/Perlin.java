package io.github.twhscs.game.util;
import org.jsfml.system.Vector2i;

public final class Perlin {
    /**
     * See: http://mrl.nyu.edu/~perlin/doc/oscar.html
     *
     * Generates a two-dimensional float matrix of Perlin noise.
     * @param size The size of the matrix
     * @param octaves The number of octaves to account for
     * @return a two-dimensional float array of Perlin noise.
     */
    public static float[][] getNoise(Vector2i size, int octaves) {
        final float[][] NOISE = new float[size.x][size.y];
        final float[][][] SMOOTH_NOISE = new float[octaves][size.x][size.y];
        final float[][] PERLIN_NOISE = new float[size.x][size.y];
        final float PERSISTENCE = 0.05f;
        float amplitude = 1.0f;
        float amplitudeSum = 0.0f;

        //Generate base noise
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                if ((y < size.y / 10 || y > size.y - size.y / 10) || (x < size.x / 10 || x > size.x - size.x / 10))
                    NOISE[x][y] = 0.9999f;
                else
                    NOISE[x][y] = (float) Math.random();
            }
        }

        // For each octave generate smooth noise
        for (int i = 0; i < octaves; i++) {
            int period = 1 << octaves;
            float frequency = 1.0f / period;
            for (int x = 0; x < size.x; x++) {
                int x0 = (x / period) * period;
                int x1 = (x0 + period) % size.x;
                float x_ratio = (x - x0) * frequency;
                for (int y = 0; y < size.x; y++) {
                    int y0 = (y / period) * period;
                    int y1 = (y0 + period) % size.x;
                    float y_ratio = (y - y0) * frequency;
                    float top = interpolate(NOISE[x0][y0], NOISE[x1][y0], x_ratio);
                    float bottom = interpolate(NOISE[x0][y1], NOISE[x1][y1], x_ratio);
                    SMOOTH_NOISE[i][x][y] = interpolate(top, bottom, y_ratio);
                }
            }
        }

        for (int i = 0; i < octaves; i++) {
            amplitude *= PERSISTENCE;
            amplitudeSum += amplitude;
            for (int x = 0; x < size.x; x++) {
                for (int y = 0; y < size.y; y++) {
                    PERLIN_NOISE[x][y] += SMOOTH_NOISE[i][x][y] * amplitude;
                }
            }
        }

        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
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
