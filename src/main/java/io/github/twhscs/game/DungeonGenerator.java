package io.github.twhscs.game;

import org.jsfml.system.Vector2i;

class DungeonGenerator implements Generatable {
    private final Vector2i dimensions;

    public DungeonGenerator(Vector2i dimensions) {
        this.dimensions = dimensions;
    }
    
    @Override
    public Terrain[][] generate() {
        return new Terrain[0][];
    }

    @Override
    public Vector2i getDimensions() {
        return dimensions;
    }
}
