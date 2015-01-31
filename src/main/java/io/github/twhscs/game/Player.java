package io.github.twhscs.game;

import io.github.twhscs.game.util.Direction;
import io.github.twhscs.game.util.Position;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

class Player implements Drawable {
    private final Sprite SPRITE;
    private final View GAME_VIEW;
    private Map MAP;
    private Position position;
    private Direction direction;

    Player(Texture playerTexture, View GAME_VIEW) {
        position = new Position(0, 0);
        this.SPRITE = new Sprite();
        SPRITE.setTexture(playerTexture);
        SPRITE.setTextureRect(new IntRect(0, 0, 32, 48));
        this.GAME_VIEW = GAME_VIEW;
        direction = Direction.NORTH;
        //this.MAP = MAP;
        SPRITE.setPosition(Vector2f.sub(Vector2f.mul(position.getPosition(), 32), new Vector2f(0, 16)));
        GAME_VIEW.setCenter(SPRITE.getPosition());
    }

    public Sprite getSPRITE() {
        return SPRITE;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setMap(Map map) {
        this.MAP = map;
    }

    public void move(Direction direction) {
        Vector2f newPosition = null;
        // System.out.println(direction);
        switch (direction) {
            case NORTH:
                newPosition = Vector2f.sub(position.getPosition(), new Vector2f(0, 1));
                break;
            case SOUTH:
                newPosition = Vector2f.add(position.getPosition(), new Vector2f(0, 1));
                break;
            case WEST:
                newPosition = Vector2f.sub(position.getPosition(), new Vector2f(1, 0));
                break;
            case EAST:
                newPosition = Vector2f.add(position.getPosition(), new Vector2f(1, 0));
                break;
        }
        if (MAP.isValidPosition(newPosition)) {
            position.setPosition(newPosition);
            this.direction = direction;
            SPRITE.setPosition(Vector2f.sub(Vector2f.mul(position.getPosition(), 32), new Vector2f(0, 16)));
            GAME_VIEW.setCenter(SPRITE.getPosition());
            //MAP.update(new Vector2f((int) Math.floor(position.x), (int) Math.floor(position.y)));
            //System.out.println(position);
        }

        // GAME_VIEW.setCenter(SPRITE.getPosition());
//        MAP.update((int) Math.floor(position.x), (int) Math.floor(position.y));
    }

    public void update() {

    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {
        renderTarget.draw(SPRITE);
    }
}
