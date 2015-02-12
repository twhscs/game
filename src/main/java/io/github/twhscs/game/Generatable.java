package io.github.twhscs.game;

import org.jsfml.system.Vector2i;

interface Generatable {
    public Terrain[][] generate();
    public Vector2i getDimensions();
}
