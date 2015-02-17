package io.github.twhscs.game;

import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2i;

import java.util.Random;

class DungeonGenerator implements Generatable {
    private final Vector2i dimensions;

    public DungeonGenerator(Vector2i dimensions) {
        this.dimensions = dimensions;
    }
    
    @Override
    public Terrain[][] generate() {
        Terrain[][] map = new Terrain[dimensions.x][dimensions.y];
        int attempts = 500;
        int minSize = 5;
        int maxSize = 10;
        IntRect[] rooms = new IntRect[attempts];
        for (int i = 0; i < attempts; i++) {
            IntRect room = new IntRect(
                    (int)(Math.random() * dimensions.x),
                    (int)(Math.random() * dimensions.y),
                    (int)(Math.random() * (maxSize - minSize) + minSize),
                    (int)(Math.random() * (maxSize - minSize) + minSize)
                    );
        }
        return new Terrain[0][];
    }

    @Override
    public Vector2i getDimensions() {
        return dimensions;
    }

    private boolean isInBounds(IntRect room) {
        return room.left > 0 && room.top > 0 && room.left + room.width < dimensions.x && room.top + room.height <
                                                                                         dimensions.y;
    }

    private boolean intersects(IntRect room1, IntRect room2) {
        Vector2i halfRoom1 = new Vector2i(room1.width/2, room1.height/2);
        Vector2i halfRoom2 = new Vector2i(room2.width/2, room2.height/2);
        Vector2i roomCenter1 = new Vector2i(room1.left + halfRoom1.x, room1.top + halfRoom1.y);
        Vector2i roomCenter2 = new Vector2i(room2.left + halfRoom2.x, room2.top + halfRoom2.y);
        return Math.abs(roomCenter2.x - roomCenter1.x) > halfRoom1.x + halfRoom2.x && Math.abs(roomCenter2.y -
                                                                                               roomCenter1.y) >
                                                                                      halfRoom1.y + halfRoom2.y;
    }

    private boolean hasConflicts(IntRect room, IntRect[] rooms) {
        for (IntRect r : rooms) {
            if (intersects(room, r)) {
                return true;
            }
        }
        return !isInBounds(room);
    }
}
