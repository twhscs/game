package io.github.twhscs.game;

import org.jsfml.system.Vector2f;

class Terrain {
    /*
    
    See: http://gameprogrammingpatterns.com/flyweight.html
    
     */
    private final boolean TRAVERSABLE;
    private final Vector2f TEXTURE_COORDINATES;
    private final boolean RANDOMIZED;
    private final boolean ANIMATED;

    public final static Terrain GRASS = new Terrain(true, new Vector2f(0, 352), true, false);
    public final static Terrain WATER = new Terrain(false, new Vector2f(864, 160), false, true);
    public final static Terrain SAND = new Terrain(true, new Vector2f(576, 352), true, false);
    public final static Terrain SNOW = new Terrain(true, new Vector2f(576, 544), true, false);

    public Terrain(boolean TRAVERSABLE, Vector2f TEXTURE_COORDINATES, boolean RANDOMIZED, boolean ANIMATED) {
        this.TRAVERSABLE = TRAVERSABLE;
        this.TEXTURE_COORDINATES = TEXTURE_COORDINATES;
        this.RANDOMIZED = RANDOMIZED;
        this.ANIMATED = ANIMATED;
    }

    public Vector2f getTextureCoordinates() {
        return TEXTURE_COORDINATES;
    }

    public boolean isTraversable() {
        return TRAVERSABLE;
    }

    public boolean isRandomized() {
        return RANDOMIZED;
    }

    public boolean isAnimated() {
        return ANIMATED;
    }
}
