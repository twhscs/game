package io.github.twhscs.game;

import org.jsfml.system.Vector2i;

class Dungeon {
    private final Vector2i DIMENSIONS;
    private final Vector2i ROOMSIZE;
    private final int[][] MAP;
    Dungeon(Vector2i DIMENSIONS, Vector2i ROOMSIZE) {
        this.DIMENSIONS = DIMENSIONS;
        this.ROOMSIZE = ROOMSIZE;
        this.MAP = new int[DIMENSIONS.x][DIMENSIONS.y];
    }
    public void generate() {
        createRoom(Vector2i.div(DIMENSIONS, 2), new Vector2i(1, 1));
    }
    public boolean isInBounds(Vector2i position) {
        return position.x >= 0 && position.y >= 0 && position.x < DIMENSIONS.x && position.y < DIMENSIONS.y;
    }
    public void createRoom(Vector2i start, Vector2i direction) {
        Vector2i size = new Vector2i(
                (int)(Math.random() * (ROOMSIZE.y - ROOMSIZE.x) + ROOMSIZE.x),
                (int)(Math.random() * (ROOMSIZE.y - ROOMSIZE.x) + ROOMSIZE.x)
        );
        Vector2i end;
        do {
            end = Vector2i.add(start, Vector2i.componentwiseMul(size, direction));
            if (end.x < 0 || end.x >= DIMENSIONS.x)
                size = Vector2i.sub(size, new Vector2i(1,0));
            if (end.y < 0 || end.y >= DIMENSIONS.y)
                size = Vector2i.sub(size, new Vector2i(0,1));
        }
        while (!isInBounds(end));
        if (size.x >= ROOMSIZE.x && size.y >= ROOMSIZE.x) {
            fillRoom(start, end);
            Vector2i center = Vector2i.add(start, Vector2i.componentwiseMul(Vector2i.div(size, 2), direction));
            Vector2i side = new Vector2i(
                    (int)(Math.random() * 2 - 1),
                    (int)(Math.random() * 2 - 1)
            );
            Vector2i pathStart = Vector2i.add(center, Vector2i.componentwiseMul(Vector2i.div(size, 2), direction));
            Vector2i pathDirection = new Vector2i(~(side.x ^ side.y), side.x ^ side.y);
            createPath(pathStart, pathDirection);
        }
    }
    public void createPath(Vector2i start, Vector2i direction) {
        Vector2i pathPosition = new Vector2i(0,0);
        for (int i = 0; i < 3; i++) {
            pathPosition = Vector2i.add(start, Vector2i.mul(direction, i));
            System.out.println(pathPosition);
            if (isInBounds(pathPosition)) {
                MAP[pathPosition.x][pathPosition.y] = 1;
            }
        }
        createRoom(pathPosition, direction);
    }
    public void fillRoom(Vector2i start, Vector2i end) {
        if (start.x > end.x) {
            Vector2i temp = start;
            start = end;
            end = temp;
        }
        for (int x = start.x; x <= end.x; x++) {
            for (int y = start.y; y <= end.y; y++) {
                MAP[x][y] = 1;
            }
        }
    }
    public String toString() {
        String returnString = "";
        for (int x = 0; x < DIMENSIONS.x; x++) {
            for (int y = 0; y < DIMENSIONS.y; y++) {
                returnString += MAP[x][y];
            }
            returnString += "\n";
        }
        return returnString;
    }
}
