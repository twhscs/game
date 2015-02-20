package io.github.twhscs.game;

import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2i;

import java.util.*;

class DungeonGenerator implements Generatable {
    private final Vector2i dimensions;

    public DungeonGenerator(Vector2i dimensions) {
        this.dimensions = dimensions;
    }
    
    @Override
    public Terrain[][] generate() {
        int attempts = 50;
        int minSize = 5;
        int maxSize = 10;

        Random random = new Random();
        Terrain[][] map = new Terrain[dimensions.x][dimensions.y];
        List<IntRect> roomList = new ArrayList<IntRect>();

        for (int i = 0; i < attempts; i++) {
            IntRect roomRect = new IntRect(
                    random.nextInt(dimensions.x),
                    random.nextInt(dimensions.y),
                    random.nextInt(maxSize - minSize) + minSize,
                    random.nextInt(maxSize - minSize) + minSize
            );
            if (isValid(roomRect, roomList)) {
                roomList.add(roomRect);
                for (int x = roomRect.left; x < roomRect.left + roomRect.width; x++) {
                    for (int y = roomRect.top; y < roomRect.top + roomRect.height; y++) {
                        map[x][y] = Terrain.SNOW;
                    }
                }
            }
        }
        Comparator<IntRect> comparator = new Comparator<IntRect>(){
            public int compare(final IntRect o1, final IntRect o2){
                Vector2i v1 = new Vector2i(o1.left + o1.width/2, o1.top + o1.height/2);
                Vector2i v2 = new Vector2i(o2.left + o2.width/2, o2.top + o2.height/2);
                int dist1 = (v1.x - v2.x) * (v1.x - v2.x) + (v1.y - v2.y) * (v1.y - v2.y);
                int dist2 = (v2.x - v1.x) * (v2.x - v1.x) + (v2.y - v1.y) * (v2.y - v1.y);
                return dist1 - dist2;
            }
        };
        Collections.sort(roomList, comparator);
        for (int i = 0; i < roomList.size() - 1; i++) {
            IntRect roomA = roomList.get(i);
            IntRect roomB = roomList.get(i + 1);
            Vector2i centerA = new Vector2i(roomA.left + roomA.width/2, roomA.top + roomA.height/2);
            Vector2i centerB = new Vector2i(roomB.left + roomB.width/2, roomB.top + roomB.height/2);
            int yDirection = centerA.y > centerB.y ? -1 : 1;
            int xDirection = centerA.x > centerB.x ? -1 : 1;
            for (int j = 0; j < Math.abs(centerA.y - centerB.y); j++) {
                map[centerA.x][centerA.y + (yDirection * j)] = Terrain.SNOW;
            }
            for (int j = 0; j < Math.abs(centerA.x - centerB.x); j++) {
                map[centerA.x + (xDirection * j)][centerB.y] = Terrain.SNOW;
            }
        }
        return map;
    }

    @Override
    public Vector2i getDimensions() {
        return dimensions;
    }

    private boolean roomsCollide(IntRect o1, IntRect o2) {
        int minX = Math.max(Math.min(o1.left, o1.left + o1.width), Math.min(o2.left, o2.left + o2.width));
        int minY = Math.max(Math.min(o1.top, o1.top + o1.height), Math.min(o2.top, o2.top + o2.height));
        int maxX = Math.min(Math.max(o1.left, o1.left + o1.width), Math.max(o2.left, o2.left + o2.width));
        int maxY = Math.min(Math.max(o1.top, o1.top + o1.height), Math.max(o2.top, o2.top + o2.height));
        return minX <= maxX && minY <= maxY;
    }

    private boolean isValid(IntRect intRect, List<IntRect> intRectList) {
        for (IntRect i : intRectList) {
            if (roomsCollide(intRect, i)) {
                return false;
            }
        }
        return intRect.left > 0 &&
               intRect.top > 0 &&
               intRect.left + intRect.width < dimensions.x &&
               intRect.top + intRect.height < dimensions.y;
    }
}
