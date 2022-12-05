package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * A RoomGenerator class that can decide the size of the rooms inside the containers
 * has a Alist of walls for each room
 * has a Alist of floors for each room
 *
 */

public class RoomGenerator {

    private List<Tile> walls;
    private List<Tile> floors;
    static int h = BSPTree.height;
    static int w = BSPTree.width;

    public RoomGenerator (Container c, TETile[][] world, Random r) {
        walls = new ArrayList<>();
        floors = new ArrayList<>();
        if ((c.w + c.x) <= w && (c.h + c.y) <= h) {
            if (c.w > 0 && c.h > 0) {
                int randomX = GenerateRoomsInsideContainers(c.w, r);
                int randomY = GenerateRoomsInsideContainers(c.h, r);

                int row = c.x + randomX;
                int col = c.y + randomY;

                int rowEnd = (c.x + c.w) - randomX;
                int colEnd = (c.y + c.h) - randomY;

                for (int i = row; i <= rowEnd; i++) {
                    for (int j = col; j <= colEnd; j++) {
                        if (i == row || i == rowEnd || j == col || j == colEnd) {
                            walls.add(new Tile(i, j));
                        } else {
                            floors.add(new Tile(i, j));
                        }
                    }
                }
            }
        }
    }

    public List<Tile> getWalls () {
        return walls;
    }

    public List<Tile> getFloors () {
        return floors;
    }


    static void drawRoom (RoomGenerator r, TETile[][] world) {
        for (Tile t : r.getWalls()) {
            world[t.x][t.y] = Tileset.WALL;
        }

        for (Tile t : r.getFloors()) {
            world[t.x][t.y] = Tileset.FLOOR;
        }
    }


    /**
     * Randomize the rooms size by checking if the size of the room can be halved perfectly.
     * If yes, return 0 and use the original size to split.
     * If no, return a random integer to decide the room size.
     *
     * @param x : pass in either width or height of container
     *
     * @return an integer
     */
    public int GenerateRoomsInsideContainers (int x, Random r) {
        if (x / 2 != 0) {
            return r.nextInt(x / 2);
        } else {
            return 0;
        }
    }

}
