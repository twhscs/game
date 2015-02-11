package io.github.twhscs.game;

import io.github.twhscs.game.util.Direction;
import io.github.twhscs.game.util.Position;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

class Character extends Entity {
    protected int ANIMATION_FRAMES;
    protected int ANIMATION_SPEED;
    protected float ANIMATION_STEP;
    protected int animationFrame;
    protected boolean animating;

    Character(Texture charTexture, int TILE_SIZE, int ANIMATION_FRAMES, int ANIMATION_SPEED) {
        super(charTexture, TILE_SIZE);
        super.SPRITE_SIZE = Vector2i.div(charTexture.getSize(), ANIMATION_FRAMES);
        this.ANIMATION_FRAMES = ANIMATION_FRAMES;
        this.ANIMATION_SPEED = ANIMATION_SPEED;
        this.ANIMATION_STEP = 1.0f / (ANIMATION_FRAMES * ANIMATION_SPEED);
        animationFrame = 0;
        animating = false;
    }

    @Override
    public String toString() {
        return "Character{" +
                "SPRITE_SIZE=" + SPRITE_SIZE +
                ", ANIMATION_FRAMES=" + ANIMATION_FRAMES +
                ", ANIMATION_SPEED=" + ANIMATION_SPEED +
                ", ANIMATION_STEP=" + ANIMATION_STEP +
                ", position=" + position +
                ", direction=" + direction +
                ", animationFrame=" + animationFrame +
                ", animating=" + animating +
                '}';
    }

    @Override
    public void updateSprite() {
        // Calculate the sprite position by multiplying the map position by the tile size.
        // Subtract half of the sprite width minus the tile size to center it horizontally.
        // Subtract the sprite height minus the tile size to center it vertically.
        Vector2f spritePosition = new Vector2f(position.x * TILE_SIZE - ((SPRITE_SIZE.x - TILE_SIZE) / 2.0f), position.y * TILE_SIZE - (SPRITE_SIZE.y - TILE_SIZE));
        // Round the position to prevent graphical errors.
        spritePosition = Position.round(spritePosition);
        // Update the sprite's position.
        SPRITE.setPosition(spritePosition);
        // Apply the appropriate texture based on direction and animation.
        SPRITE.setTextureRect(getTextureRect());
    }

    public void move(Direction direction) {
        // TODO: Allow for faster movement. (Do not have to wait until current move is finished before initiating next move.)
        // Only move the player if they are not already moving.
        if (!animating) {
            // Calculate the position to move towards.
            Vector2f newPosition = Position.getRelativePosition(position, direction, 1.0f);
            // Make sure the new position is valid.
            if (map.isValidPosition(newPosition)) {
                // If it is valid, update the direction and start moving..
                super.direction = direction;
                animating = true;
            }
        }
    }

    @Override
    public void update() {
        // Check if the player is moving.
        if (animating) {
            // Move the player by the animation step.
            super.position = Position.getRelativePosition(position, direction, ANIMATION_STEP);
            // Check if it is time to stop moving.
            if (animationFrame + 1 >= ANIMATION_FRAMES * ANIMATION_SPEED) {
                // Reset the animation frame and stop moving.
                animationFrame = 0;
                animating = false;
                // Round the position to prevent float rounding errors.
                super.position = Position.round(position);
            } else {
                // If it is not time to stop, keep going.
                animationFrame++;
            }
            // Update the sprite.
            updateSprite();
        }
    }

    private IntRect getTextureRect() {
        // Normalize the current frame based on the amount of actual frames.
        int adjustedFrame = Math.round((animationFrame * ANIMATION_FRAMES) / (ANIMATION_FRAMES * ANIMATION_SPEED));
        // Use math to calculate the player's current texture.
        switch (super.direction) {
            case NORTH:
                return new IntRect(adjustedFrame * super.SPRITE_SIZE.x, 3 * super.SPRITE_SIZE.y, super.SPRITE_SIZE.x, super.SPRITE_SIZE.y);
            case SOUTH:
                return new IntRect(adjustedFrame * super.SPRITE_SIZE.x, 0, super.SPRITE_SIZE.x, super.SPRITE_SIZE.y);
            case WEST:
                return new IntRect(adjustedFrame * super.SPRITE_SIZE.x, super.SPRITE_SIZE.y, super.SPRITE_SIZE.x, super.SPRITE_SIZE.y);
            case EAST:
                return new IntRect(adjustedFrame * super.SPRITE_SIZE.x, 2 * super.SPRITE_SIZE.y, super.SPRITE_SIZE.x, super.SPRITE_SIZE.y);
            default:
                return new IntRect(0, 0, 0, 0);
        }
    }

    public void interpolate(float positionBetweenUpdates) {
        if (animating) {
            // Multiply the animation step by the position between frames (0.0f - 1.0f).
            float interpolationStep = ANIMATION_STEP * positionBetweenUpdates;
            // Get the current position.
            Vector2f currentPosition = super.position;
            // Temporarily update the position with the interpolation step applied, update the sprite, then revert the position.
            super.position = Position.getRelativePosition(super.position, super.direction, interpolationStep);
            updateSprite();
            super.position = currentPosition;
        }
    }
}
