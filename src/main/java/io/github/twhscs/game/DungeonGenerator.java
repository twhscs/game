package io.github.twhscs.game;

import org.jsfml.system.Vector2i;

class DungeonGenerator extends Generator {

    public DungeonGenerator(Vector2i dimensions) {
        super(dimensions);
    }

    @Override
    public Terrain[][] generate() {
        return new Terrain[0][];
    }

}
