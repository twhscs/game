package io.github.twhscs.game;

import io.github.twhscs.game.util.Direction;
import io.github.twhscs.game.util.Position;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

class Player implements Drawable {
    private final Sprite SPRITE;
    private final View GAME_VIEW;
    private final Vector2i SPRITE_SIZE;
    private final int TILE_SIZE;
    private final int ANIMATION_FRAMES;
    private final int ANIMATION_SPEED;
    private final float ANIMATION_STEP;
    private Vector2f position;
    private Direction direction;
    private int animationFrame;
    private boolean animating;
    private Map map;

    Player(Texture playerTexture, View GAME_VIEW, int TILE_SIZE, int ANIMATION_FRAMES, int ANIMATION_SPEED) {
        // Create a new sprite with the specified texture.
        this.SPRITE = new Sprite(playerTexture);
        this.GAME_VIEW = GAME_VIEW;
        // Calculate the sprite size by dividing the texture size by the number of animations.
        SPRITE_SIZE = Vector2i.div(playerTexture.getSize(), ANIMATION_FRAMES);
        this.TILE_SIZE = TILE_SIZE;
        this.ANIMATION_FRAMES = ANIMATION_FRAMES;
        this.ANIMATION_SPEED = ANIMATION_SPEED;
        // Calculate the animation step by taking the reciprocal of the frame count times speed.
        ANIMATION_STEP = 1.0f / (ANIMATION_FRAMES * ANIMATION_SPEED);
        position = new Vector2f(0.0f, 0.0f);
        direction = Direction.NORTH;
        animationFrame = 0;
        animating = false;
        // Initialize the sprite.
        updateSprite();
    }

    @Override
    public String toString() {
        return "Player{" +
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

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void updateSprite() {
        // Calculate the sprite position by multiplying the map position by the tile size.
        // Subtract half of the sprite width minus the tile size to center it horizontally.
        // Subtract the sprite height minus the tile size to center it vertically.
        Vector2f spritePosition = new Vector2f(position.x * TILE_SIZE - ((SPRITE_SIZE.x - TILE_SIZE) / 2.0f), position.y * TILE_SIZE - (SPRITE_SIZE.y - TILE_SIZE));
        // Update the sprite's position.
        SPRITE.setPosition(spritePosition);
        // Apply the appropriate texture based on direction and animation.
        SPRITE.setTextureRect(getTextureRect());
        // Add half of the sprite's width and height to the view in order to center the sprite and then round to prevent graphical errors.
        GAME_VIEW.setCenter(Position.round(Vector2f.add(spritePosition, Vector2f.div(new Vector2f(SPRITE_SIZE), 2.0f))));
    }

    public void move(Direction direction) {
        // TODO: Allow for faster movement. (Do not have to wait until current move is finished before initiating next move.)
        // Only move the player if they are not already moving.
        if (!animating) {
            // Calculate the position to move towards.
            Vector2f newPosition = Position.getRelativePosition(position, direction, 1.0f);
            // Make sure the new position is valid.
            if (map.isEmptyPosition(newPosition)) {
                // If it is valid, update the direction and start moving..
                this.direction = direction;
                animating = true;
            }
        }
    }

    public void update() {
        // Check if the player is moving.
        if (animating) {
            // Move the player by the animation step.
            position = Position.getRelativePosition(position, direction, ANIMATION_STEP);
            // Check if it is time to stop moving.
            if (animationFrame + 1 >= ANIMATION_FRAMES * ANIMATION_SPEED) {
                // Reset the animation frame and stop moving.
                animationFrame = 0;
                animating = false;
                // Round the position to prevent float rounding errors.
                position = Position.round(position);
            } else {
                // If it is not time to stop, keep going.
                animationFrame++;
            }
            // Update the sprite.
            updateSprite();
        }
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private IntRect getTextureRect() {
        // Normalize the current frame based on the amount of actual frames.
        int adjustedFrame = Math.round((animationFrame * ANIMATION_FRAMES) / (ANIMATION_FRAMES * ANIMATION_SPEED));
        // Use math to calculate the player's current texture.
        switch (direction) {
            case NORTH:
                return new IntRect(adjustedFrame * SPRITE_SIZE.x, 3 * SPRITE_SIZE.y, SPRITE_SIZE.x, SPRITE_SIZE.y);
            case SOUTH:
                return new IntRect(adjustedFrame * SPRITE_SIZE.x, 0, SPRITE_SIZE.x, SPRITE_SIZE.y);
            case WEST:
                return new IntRect(adjustedFrame * SPRITE_SIZE.x, SPRITE_SIZE.y, SPRITE_SIZE.x, SPRITE_SIZE.y);
            case EAST:
                return new IntRect(adjustedFrame * SPRITE_SIZE.x, 2 * SPRITE_SIZE.y, SPRITE_SIZE.x, SPRITE_SIZE.y);
            default:
                return new IntRect(0, 0, 0, 0);
        }
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        renderTarget.draw(SPRITE);
    }

    public void interpolate(float positionBetweenUpdates) {
        if (animating) {
            // Multiply the animation step by the position between frames (0.0f - 1.0f).
            float interpolationStep = ANIMATION_STEP * positionBetweenUpdates;
            // Get the current position.
            Vector2f currentPosition = position;
            // Temporarily update the position with the interpolation step applied, update the sprite, then revert the position.
            position = Position.getRelativePosition(position, direction, interpolationStep);
            updateSprite();
            position = currentPosition;
        }
    }
}
