package io.github.twhscs.game;

import org.jsfml.system.Vector2f;
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

    }
    public void createRoom(Vector2i start, Vector2i direction) {
        Vector2i size = new Vector2i(
                (int)(Math.random() * (ROOMSIZE.y - ROOMSIZE.x) + ROOMSIZE.x),
                (int)(Math.random() * (ROOMSIZE.y - ROOMSIZE.x) + ROOMSIZE.x)
        );
        Vector2i end;
        do {
            end = Vector2i.add(start, Vector2i.componentwiseMul(size, direction));
            if (end.x < 0 || end.x > DIMENSIONS.x)
                Vector2i.sub(size, new Vector2i(1,0));
            if (end.y < 0 || end.y > DIMENSIONS.y)
                Vector2i.sub(size, new Vector2i(0,1));
        }
        while (end.x < 0 || end.y < 0 || end.x > DIMENSIONS.x || end.y > DIMENSIONS.y);
        if (size.x > 3 || size.y > 3) {
            fillRoom(start, end);
            Vector2i center = Vector2i.add(start, Vector2i.componentwiseMul(Vector2i.div(size, 2), direction));
            Vector2i side = new Vector2i(
                    (int)(Math.random() * 2 - 1),
                    (int)(Math.random() * 2 - 1)
            );
            Vector2i pathStart = Vector2i.add(center, Vector2i.componentwiseMul(Vector2i.div(size, 2), direction));
            Vector2i pathDirection = new Vector2i(~(pathStart.x ^ pathStart.y), pathStart.x ^ pathStart.y);
            createPath(pathStart, side);
        }
    }
    public void createPath(Vector2i start, Vector2i direction) {

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
}
