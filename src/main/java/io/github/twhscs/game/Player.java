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
    private Position position;
    private Direction direction;
    private Map map;
    private int animationFrame;
    private boolean animating;

    Player(Texture playerTexture, View GAME_VIEW, Vector2i SPRITE_SIZE, int TILE_SIZE, int ANIMATION_FRAMES, int ANIMATION_SPEED) {
        this.SPRITE = new Sprite(playerTexture);
        this.GAME_VIEW = GAME_VIEW;
        this.SPRITE_SIZE = SPRITE_SIZE;
        this.TILE_SIZE = TILE_SIZE;
        this.ANIMATION_FRAMES = ANIMATION_FRAMES;
        this.ANIMATION_SPEED = ANIMATION_SPEED;
        ANIMATION_STEP = 1.0f / (ANIMATION_FRAMES * ANIMATION_SPEED);
        position = new Position(0.0f, 0.0f);
        direction = Direction.NORTH;
        animationFrame = 0;
        animating = false;
        updateSprite();
    }

    private void updateSprite() {
        float positionX = position.getX() * 32 - ((SPRITE_SIZE.x - TILE_SIZE) / 2);
        float positionY = position.getY() * 32 - (SPRITE_SIZE.y - TILE_SIZE);
        positionX = (float) Math.round(positionX);
        positionY = (float) Math.round(positionY);
        SPRITE.setPosition(positionX, positionY);
        SPRITE.setTextureRect(getTextureRect());
        GAME_VIEW.setCenter(SPRITE.getPosition());
    }

    public void move(Direction direction) {
        if (!animating /*|| direction != this.direction*/) {
            Position newPosition = position.getRelativePosition(direction, 1.0f);
            if (map.isValidPosition(newPosition.getPosition())) {

                //position = position.getRelativePosition(direction, ANIMATION_STEP);
                //animationFrame++;
                //updateSprite();
                if (direction != this.direction) {
                    Position currentPosition = position;
                    Vector2f roundedPosition = new Vector2f(Math.round(currentPosition.getX()), Math.round(currentPosition.getY()));
                    position.setPosition(roundedPosition);
                    System.out.println(roundedPosition);
                    updateSprite();
                }
                this.direction = direction;
                animating = true;
            }
        }
    }

    public void update() {
        if (animating) {
            //System.out.println(getTextureRect());
            if (animationFrame >= (ANIMATION_FRAMES * ANIMATION_SPEED) - 1) {
                animationFrame = 0;
                animating = false;
            } else {
                animationFrame++;
            }
            position = position.getRelativePosition(direction, ANIMATION_STEP);
            updateSprite();
        }
    }

    public Sprite getSprite() {
        return SPRITE;
    }

    public int getAnimationFrame() {
        return animationFrame;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private IntRect getTextureRect() {
        int adjustedFrame = Math.round((animationFrame * ANIMATION_FRAMES) / (ANIMATION_FRAMES * ANIMATION_SPEED));
        //System.out.println(adjustedFrame);
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

    public void interpolate(float betweenUpdates) {
        /*if(animationFrame / 2 > 0) {
            float step = (1.0f / (ANIMATION_FRAMES * ANIMATION_SPEED)) * betweenUpdates;
            float positionX = position.getX() * 32 - ((SPRITE_SIZE.x - TILE_SIZE) / 2);
            float positionY = position.getY() * 32 - (SPRITE_SIZE.y - TILE_SIZE);
            switch (direction) {
                case NORTH:
                    positionY = (position.getY() - step) * 32 - (SPRITE_SIZE.y - TILE_SIZE);
                    break;
                case SOUTH:
                    positionY = (position.getY() + step) * 32 - (SPRITE_SIZE.y - TILE_SIZE);
                    break;
                case WEST:
                    positionX = (position.getX() - step) * 32 - ((SPRITE_SIZE.x - TILE_SIZE) / 2);
                    break;
                case EAST:
                    positionX = (position.getX() + step) * 32 - ((SPRITE_SIZE.x - TILE_SIZE) / 2);
                    break;
            }

            SPRITE.setPosition(positionX, positionY);
            SPRITE.setTextureRect(getTextureRect());
            GAME_VIEW.setCenter(SPRITE.getPosition());
        }*/
        if (animating) {
            float step = ANIMATION_STEP * betweenUpdates;
            Position currentPosition = position;
            Position newPosition = position.getRelativePosition(direction, step);
            position = newPosition;
            updateSprite();
            position = currentPosition;
        }
    }
}
