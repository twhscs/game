package io.github.twhscs.game.util;

import org.jsfml.system.Vector2i;

public final class Perlin {
    
    /*
    See: http://mrl.nyu.edu/~perlin/doc/oscar.html
     */

    /**
     * Generates a 2D float matrix of Perlin noise.
     *
     * @param dimensions the dimensions of the matrix to generate.
     * @param octaves    the number of octaves.
     * @return a 2D float matrix of Perlin noise.
     */
    public static float[][] noise(Vector2i dimensions, int octaves) {
        final float[][] noise = new float[dimensions.x][dimensions.y];
        final float[][][] smoothNoise = new float[octaves][dimensions.x][dimensions.y];
        final float[][] perlinNoise = new float[dimensions.x][dimensions.y];
        final float persistence = 0.05f;
        float amplitude = 1.0f;
        float amplitudeSum = 0.0f;

        // Generate base noise.
        for (int i = 0; i < dimensions.x; i++) {
            for (int j = 0; j < dimensions.y; j++) {
                if ((j < dimensions.y / 10 || j > dimensions.y - dimensions.y / 10) ||
                    (i < dimensions.x / 10 || i > dimensions.x - dimensions.x / 10)) {
                    noise[i][j] = 0.9999f;
                } else {
                    noise[i][j] = (float) Math.random();
                }
            }
        }

        // Generate smooth noise for each octave.
        for (int i = 0; i < octaves; i++) {
            int period = 1 << octaves;
            float frequency = 1.0f / period;
            for (int j = 0; j < dimensions.x; j++) {
                int x0 = (j / period) * period;
                int x1 = (x0 + period) % dimensions.x;
                float xRatio = (j - x0) * frequency;
                for (int k = 0; k < dimensions.x; k++) {
                    int y0 = (k / period) * period;
                    int y1 = (y0 + period) % dimensions.x;
                    float yRatio = (k - y0) * frequency;
                    float top = lerp(xRatio, noise[x0][y0], noise[x1][y0]);
                    float bottom = lerp(xRatio, noise[x0][y1], noise[x1][y1]);
                    smoothNoise[i][j][k] = lerp(yRatio, top, bottom);
                }
            }
        }

        for (int i = 0; i < octaves; i++) {
            amplitude *= persistence;
            amplitudeSum += amplitude;
            for (int j = 0; j < dimensions.x; j++) {
                for (int k = 0; k < dimensions.y; k++) {
                    perlinNoise[j][k] += smoothNoise[i][j][k] * amplitude;
                }
            }
        }

        for (int i = 0; i < dimensions.x; i++) {
            for (int j = 0; j < dimensions.y; j++) {
                perlinNoise[i][j] /= amplitudeSum;
            }
        }

        return perlinNoise;

    }

    private static float lerp(float t, float a, float b) {
        return a + t * (b - a);
    }

}
