package io.github.twhscs.game;

import org.jsfml.system.Vector2f;

class Terrain {
    /*
    
    See: http://gameprogrammingpatterns.com/flyweight.html
    
     */
    private final boolean TRAVERSABLE;
    private final Vector2f[] TEXTURE_COORDINATES;
    private final boolean RANDOMIZED;

    public Terrain(boolean TRAVERSABLE, Vector2f[] TEXTURE_COORDINATES, boolean RANDOMIZED) {
        this.TRAVERSABLE = TRAVERSABLE;
        this.TEXTURE_COORDINATES = TEXTURE_COORDINATES;
        this.RANDOMIZED = RANDOMIZED;
    }

    public Terrain(boolean TRAVERSABLE, Vector2f TEXTURE_COORDINATES, boolean RANDOMIZED) {
        this(TRAVERSABLE, new Vector2f[]{TEXTURE_COORDINATES}, RANDOMIZED);
    }

    public Vector2f[] getTextureCoordinates() {
        return TEXTURE_COORDINATES;
    }

    public boolean isTraversable() {
        return TRAVERSABLE;
    }

    public boolean isRandomized() {
        return RANDOMIZED;
    }

    public int getTextureCount() {
        return TEXTURE_COORDINATES.length;
    }
}
