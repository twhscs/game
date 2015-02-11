package io.github.twhscs.game;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

class Player extends Character {
    private final View GAME_VIEW;

    Player(Texture playerTexture, View GAME_VIEW, int TILE_SIZE, int ANIMATION_FRAMES, int ANIMATION_SPEED) {
        super(playerTexture, TILE_SIZE, ANIMATION_FRAMES, ANIMATION_SPEED);
        this.GAME_VIEW = GAME_VIEW;
        updateSprite();
    }

    @Override
    public String toString() {
        return "Player{" +
                "SPRITE_SIZE=" + super.SPRITE_SIZE +
                ", ANIMATION_FRAMES=" + ANIMATION_FRAMES +
                ", ANIMATION_SPEED=" + ANIMATION_SPEED +
                ", ANIMATION_STEP=" + ANIMATION_STEP +
                ", position=" + super.position +
                ", direction=" + super.direction +
                ", animationFrame=" + animationFrame +
                ", animating=" + animating +
                '}';
    }

    public void updateSprite() {
        super.updateSprite();
        GAME_VIEW.setCenter(Vector2f.add(SPRITE.getPosition(), Vector2f.div(new Vector2f(SPRITE_SIZE), 2.0f)));
    }
}
