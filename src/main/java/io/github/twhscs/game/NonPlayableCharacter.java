package io.github.twhscs.game;

import io.github.twhscs.game.util.Direction;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.*;

class NonPlayableCharacter extends Character {

    NonPlayableCharacter(Texture npcTexture, int TILE_SIZE, int ANIMATION_FRAMES, int ANIMATION_SPEED) {
        super(npcTexture, TILE_SIZE, ANIMATION_FRAMES, ANIMATION_SPEED);
        updateSprite();
    }

    @Override
    public String toString() {
        return "NonPlayableCharacter{" +
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

    public void randomlyChooseMove() {
        if ((int) (Math.random() * 10) == 1) {
            for (int i = 0; i < 10; i++) {
                Direction newDirection = Direction.getRandomCardinalDirection();
                if (nextPositionIsValid(newDirection)) {
                    this.move(newDirection);
                }
            }
        }
    }

    public LinkedList<Vector2i> findPath(Vector2f targetPosition) {
        Vector2i[] xyOffsets = {new Vector2i(-1, 0), new Vector2i(1, 0), new Vector2i(0, -1), new Vector2i(0, 1)};
        Vector2i startPos = new Vector2i(position);
        Vector2i endPos = new Vector2i(targetPosition);
        LinkedList<Vector2i> openList = new LinkedList<Vector2i>();
        LinkedList<Vector2i> closedList = new LinkedList<Vector2i>();
        LinkedList<Vector2i> finalList = new LinkedList<Vector2i>();
        HashMap<Vector2i, Vector2i> cameFrom = new HashMap<Vector2i, Vector2i>();
        openList.add(startPos);
        Vector2i currentPos = startPos;
        int loopCount = 0;
        while (!openList.isEmpty() && loopCount < 10000) {
            loopCount++;
            int lowestF = Integer.MAX_VALUE;
            for (Vector2i currentVector : openList) {
                int f = (int) Math.sqrt(Math.pow((startPos.x - endPos.x), 2) + Math.pow((startPos.y - endPos.y), 2));
                if (f < lowestF) {
                    lowestF = f;
                    currentPos = currentVector;
                }
            }
            if (currentPos == endPos) {
                while (currentPos != startPos) {
                    finalList.addFirst(currentPos);
                    currentPos = cameFrom.get(currentPos);
                }
                return finalList;
            }
            openList.remove(currentPos);
            closedList.add(currentPos);
            for (Vector2i xyOffset : xyOffsets) {
                Vector2i y = Vector2i.add(currentPos, xyOffset);
                if (!map.isValidPosition(new Vector2f(y)) && closedList.contains(y)) {
                    continue;
                }
                if (!openList.contains(y)) {
                    openList.add(y);
                    cameFrom.put(y, currentPos);
                }
            }
        }
        return null;
    }

    @Override
    public void update() {
        //This randomly chooses when and where to move
        //We will hopefully replace this with path finding or something more advanced
        randomlyChooseMove();
        super.update();
    }
}
