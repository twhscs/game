package io.github.twhscs.game;

import org.jsfml.system.Vector2i;

abstract class Generator {

    protected final Vector2i dimensions;

    public Generator(Vector2i dimensions) {

        this.dimensions = dimensions;
    }

    public Vector2i getDimensions() {
        return dimensions;
    }

    public abstract Terrain[][] generate();

}
