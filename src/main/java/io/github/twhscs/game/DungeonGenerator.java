package io.github.twhscs.game;

import org.jsfml.graphics.IntRect;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        ArrayList<IntRect> rooms = new ArrayList<IntRect>();
        for (int i = 0; i < attempts; i++) {
            IntRect room = new IntRect(
                    (int)(Math.random() * dimensions.x),
                    (int)(Math.random() * dimensions.y),
                    (int)(Math.random() * (maxSize - minSize) + minSize),
                    (int)(Math.random() * (maxSize - minSize) + minSize)
            );
            if (!hasConflicts(room, rooms)) {
                rooms.add(room);
                for (int x = room.left; x < room.left + room.width; x++) {
                    for (int y = room.top; y < room.top + room.height; y++) {
                        map[x][y] = Terrain.SNOW;
                    }
                }
            }
            Comparator<IntRect> comparator = new Comparator<IntRect>(){
                @Override
                public int compare(final IntRect o1, final IntRect o2){
                    Vector2i p = new Vector2i(o1.left + o1.width/2, o1.top + o1.height/2);
                    Vector2i q = new Vector2i(o2.left + o2.width/2, o2.top + o2.height/2);
                    int dist1 = (p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y);
                    int dist2 = (q.x - p.x) * (q.x - p.x) + (q.y - p.y) * (q.y - p.y);
                    return dist1 - dist2;
                }
            };
            Collections.sort(rooms, comparator);
            for (int j = 0; j < rooms.size() - 1; j++) {
                IntRect room1 = rooms.get(j);
                IntRect room2 = rooms.get(j + 1);
                Vector2i center1 = new Vector2i(room1.left + room1.width/2, room1.top + room1.height/2);
                Vector2i center2 = new Vector2i(room2.left + room2.width/2, room2.top + room2.height/2);
                if (center1.y > center2.y) {
                    for (int y = center1.y; y >= center2.y; y--) {
                        map[center1.x][y] = Terrain.SNOW;
                    }
                } else {
                    for (int y = center1.y; y < center2.y; y++) {
                        map[center1.x][y] = Terrain.SNOW;
                    }
                }
                if (center1.x > center2.x) {
                    for (int x = center1.x; x >= center2.x; x--) {
                        map[x][center2.y] = Terrain.SNOW;
                    }
                } else {
                    for (int x = center1.x; x < center2.x; x++) {
                        map[x][center2.y] = Terrain.SNOW;
                    }
                }
            }
        }
        return map;
    }

    @Override
    public Vector2i getDimensions() {
        return dimensions;
    }

    private boolean isInBounds(IntRect room) {
        return room.left > 0 && room.top > 0 && room.left + room.width < dimensions.x && room.top + room.height < dimensions.y;
    }

    private IntRect intersection(IntRect i1, IntRect i2) {
        int var2 = Math.min(i1.left, i1.left + i1.width);
        int var3 = Math.max(i1.left, i1.left + i1.width);
        int var4 = Math.min(i1.top, i1.top + i1.height);
        int var5 = Math.max(i1.top, i1.top + i1.height);
        int var6 = Math.min(i2.left, i2.left + i2.width);
        int var7 = Math.max(i2.left, i2.left + i2.width);
        int var8 = Math.min(i2.top, i2.top + i2.height);
        int var9 = Math.max(i2.top, i2.top + i2.height);
        int var10 = Math.max(var2, var6);
        int var11 = Math.max(var4, var8);
        int var12 = Math.min(var3, var7);
        int var13 = Math.min(var5, var9);
        return var10 <= var12 && var11 <= var13?new IntRect(var10, var11, var12 - var10, var13 - var11):null;
    }

    private boolean hasConflicts(IntRect room, ArrayList<IntRect> rooms) {
        for (IntRect r : rooms) {
            if (r != null && intersection(room, r) != null) {
                return true;
            }
        }
        return !isInBounds(room);
    }
}
