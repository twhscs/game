package io.github.twhscs.game;

import io.github.twhscs.game.util.Direction;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

abstract class Entity implements Drawable {
    protected Sprite SPRITE;
    protected Vector2i SPRITE_SIZE;
    protected int TILE_SIZE;
    protected Vector2f position;
    protected Direction direction;
    protected Map map;

    Entity(Texture entityTexture, int TILE_SIZE) {
        this.SPRITE = new Sprite(entityTexture);
        this.TILE_SIZE = TILE_SIZE;
        SPRITE_SIZE = new Vector2i(0,0);
        position = new Vector2f(0.0f, 0.0f);
        direction = Direction.NORTH;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void update() {}

    public void updateSprite() {}

    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        renderTarget.draw(SPRITE);
    }

    public String toString() {
        return "Entity{" +
                "SPRITE_SIZE=" + SPRITE_SIZE +
                ", position=" + position +
                ", direction=" + direction +
                '}';
    }
}
