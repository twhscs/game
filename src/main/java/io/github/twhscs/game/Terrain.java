package io.github.twhscs.game;

import org.jsfml.system.Vector2f;

// http://gameprogrammingpatterns.com/flyweight.html
class Terrain {
    private final boolean TRAVERSABLE;
    private final Vector2f TEXTURE_COORDINATES;
    private final boolean RANDOMIZED;

    public Terrain(boolean TRAVERSABLE, Vector2f TEXTURE_COORDINATES, boolean RANDOMIZED) {
        this.TRAVERSABLE = TRAVERSABLE;
        this.TEXTURE_COORDINATES = TEXTURE_COORDINATES;
        this.RANDOMIZED = RANDOMIZED;
    }

    public Vector2f getTEXTURE_COORDINATES() {
        return TEXTURE_COORDINATES;
    }

    public boolean isTRAVERSABLE() {
        return TRAVERSABLE;
    }

    public boolean isRANDOMIZED() {
        return RANDOMIZED;
    }
}
