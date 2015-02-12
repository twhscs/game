package io.github.twhscs.game;

import org.jsfml.system.Vector2i;

public interface Generatable {
    public Terrain[][] generate();
    public Vector2i getDimensions();
}
