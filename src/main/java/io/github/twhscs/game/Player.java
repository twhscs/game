package io.github.twhscs.game;

import io.github.twhscs.game.util.Direction;
import io.github.twhscs.game.util.Position;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2i;

class Player implements Drawable {
    private final Sprite SPRITE;
    private final View GAME_VIEW;
    private final Vector2i SPRITE_SIZE;
    private final int TILE_SIZE;
    private Position position;
    private Direction direction;
    private Map map;
    private int animationFrame;

    Player(Texture playerTexture, View GAME_VIEW, Vector2i SPRITE_SIZE, int TILE_SIZE) {
        this.SPRITE = new Sprite(playerTexture);
        this.GAME_VIEW = GAME_VIEW;
        this.SPRITE_SIZE = SPRITE_SIZE;
        this.TILE_SIZE = TILE_SIZE;
        position = new Position(0.0f, 0.0f);
        direction = Direction.NORTH;
        animationFrame = 0;
        updateSprite();
    }

    public Sprite getSprite() {
        return SPRITE;
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

    private void updateSprite() {
        float positionX = position.getX() * 32 - ((SPRITE_SIZE.x - TILE_SIZE) / 2);
        float positionY = position.getY() * 32 - (SPRITE_SIZE.y - TILE_SIZE);
        SPRITE.setPosition(positionX, positionY);
        SPRITE.setTextureRect(getTextureRect());
        GAME_VIEW.setCenter(SPRITE.getPosition());
    }

    public void move(Direction direction) {
        if (animationFrame == 0) {
            Position newPosition = position.getRelativePosition(direction);
            if (map.isValidPosition(newPosition.getPosition())) {
                position = newPosition;
                this.direction = direction;
                animationFrame++;
                updateSprite();
            }
        }
    }

    private IntRect getTextureRect() {
        switch (direction) {
            case NORTH:
                return new IntRect(animationFrame * SPRITE_SIZE.x, 3 * SPRITE_SIZE.y, SPRITE_SIZE.x, SPRITE_SIZE.y);
            case SOUTH:
                return new IntRect(animationFrame * SPRITE_SIZE.x, 0, SPRITE_SIZE.x, SPRITE_SIZE.y);
            case WEST:
                return new IntRect(animationFrame * SPRITE_SIZE.x, SPRITE_SIZE.y, SPRITE_SIZE.x, SPRITE_SIZE.y);
            case EAST:
                return new IntRect(animationFrame * SPRITE_SIZE.x, 2 * SPRITE_SIZE.y, SPRITE_SIZE.x, SPRITE_SIZE.y);
            default:
                return new IntRect(0, 0, 0, 0);
        }
    }

    public void update() {
        if (animationFrame > 0) {
            System.out.println(getTextureRect());
            if (animationFrame > 3) {
                animationFrame = 0;
                updateSprite();
            } else {
                animationFrame++;
                updateSprite();
            }
        }
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        renderTarget.draw(SPRITE);
    }
}
