package io.github.twhscs.game;

import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

class DungeonGenerator implements Generatable {
    private final Vector2i dimensions;

    public DungeonGenerator(Vector2i dimensions) {
        this.dimensions = dimensions;
    }
    
    @Override
    public Terrain[][] generate() {
        Terrain[][] map = new Terrain[dimensions.x][dimensions.y];
        int attempts = 50;
        int minSize = 5;
        int maxSize = 10;
        Random random = new Random();
        ArrayList<IntRect> roomList = new ArrayList<IntRect>();
        for (int i = 0; i < attempts; i++) {
            IntRect roomRect = new IntRect(
                    random.nextInt(dimensions.x),
                    random.nextInt(dimensions.y),
                    random.nextInt(maxSize - minSize) + minSize,
                    random.nextInt(maxSize - minSize) + minSize
            );
            if (!isInvalid(roomRect, roomList)) {
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
        for (int j = 0; j < roomList.size() - 1; j++) {
            IntRect roomA = roomList.get(j);
            IntRect roomB = roomList.get(j + 1);
            Vector2i centerA = new Vector2i(roomA.left + roomA.width/2, roomA.top + roomA.height/2);
            Vector2i centerB = new Vector2i(roomB.left + roomB.width/2, roomB.top + roomB.height/2);
            int yDirection = centerA.y > centerB.y ? -1 : 1;
            int xDirection = centerA.x > centerB.x ? -1 : 1;
            for (int i = 0; i < Math.abs(centerA.y - centerB.y); i++) {
                map[centerA.x][centerA.y + (yDirection * i)] = Terrain.SNOW;
            }
            for (int i = 0; i < Math.abs(centerA.x - centerB.x); i++) {
                map[centerA.x + (xDirection * i)][centerB.y] = Terrain.SNOW;
            }
        }
        return map;
    }

    @Override
    public Vector2i getDimensions() {
        return dimensions;
    }

    private boolean isInBounds(IntRect intRect) {
        return intRect.left > 0 &&
               intRect.top > 0 &&
               intRect.left + intRect.width < dimensions.x &&
               intRect.top + intRect.height < dimensions.y;
    }

    private IntRect intersection(IntRect o1, IntRect o2) {
        int var2 = Math.min(o1.left, o1.left + o1.width);
        int var3 = Math.max(o1.left, o1.left + o1.width);
        int var4 = Math.min(o1.top, o1.top + o1.height);
        int var5 = Math.max(o1.top, o1.top + o1.height);
        int var6 = Math.min(o2.left, o2.left + o2.width);
        int var7 = Math.max(o2.left, o2.left + o2.width);
        int var8 = Math.min(o2.top, o2.top + o2.height);
        int var9 = Math.max(o2.top, o2.top + o2.height);
        int var10 = Math.max(var2, var6);
        int var11 = Math.max(var4, var8);
        int var12 = Math.min(var3, var7);
        int var13 = Math.min(var5, var9);
        return var10 <= var12 && var11 <= var13 ? new IntRect(var10, var11, var12 - var10, var13 - var11) : null;
    }

    private boolean isInvalid(IntRect intRect, ArrayList<IntRect> intRectList) {
        for (IntRect i : intRectList) {
            if (i != null && intersection(intRect, i) != null) {
                return true;
            }
        }
        return !isInBounds(intRect);
    }
}
