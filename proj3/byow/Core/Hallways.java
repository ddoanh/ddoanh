package byow.Core;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * A Hallways class that takes in two containers and the world generated
 * and connect the center of each together
 */

public class Hallways {
    public static void connectHallways (Container c, TETile[][] world) {
        if (c.left != null && c.right != null) {
            drawHallways(c.left, c.right, world);
            connectHallways(c.left, world);
            connectHallways(c.right, world);
        }
    }

    /**
     * drawHallways first check if the containers were originally splitted horizontally or vertically
     * if vertical (or centers at x-axis are equal), then connect horizontally
     * else, connect vertically
     *
     * @param c1 : container 1
     * @param c2 : container 2
     * @param world : world just generated
     *
     */

    public static void drawHallways (Container c1, Container c2, TETile[][] world) {
        if (c1.center.x == c2.center.x) {
            drawVerticalHalls(c1.center.x, c1.center.y, c2.center.y, world);
        } else if (c1.center.y == c2.center.y) {
            drawHorizontalHalls(c1.center.y, c1.center.x, c2.center.x, world);
        }
    }

    /**
     * to connect vertically, traverse from the same center point at x, downward from cy1 to cy2
     * depending on whether cy1 > cy2 or not
     * loop over from the smaller center point at y to the larger one
     * for each loop, check if
     * 1) the tile right of the hall == floor, if not, set == wall
     *
     * 2) the tile left of the hall == floor, if not, set == wall
     *
     * 3) the tile in the hall == floor, if not, check for
     *
     * @param cx
     * @param cy1
     * @param cy2
     * @param world
     */

    public static void drawVerticalHalls (int cx, int cy1, int cy2, TETile[][] world) {
        if (cy1 > cy2) {
            for (int i = cy2; i < cy1 + 1; i++) {
                if (!(world[cx+1][i].description().equals("floor"))) {
                    world[cx+1][i] = Tileset.WALL;
                }
                if (!(world[cx-1][i].description().equals("floor"))) {
                    world[cx-1][i] = Tileset.WALL;
                }
                if (!(world[cx][i].description().equals("floor"))) {
                    if (i == cy1 || i == cy2) {
                        world[cx][i] = Tileset.WALL;
                    } else {
                        world[cx][i] = Tileset.FLOOR;
                    }
                }
            }
        } else {
            for (int i = cy1; i < cy2 + 1; i++) {
                if (!(world[cx+1][i].description().equals("floor"))) {
                    world[cx+1][i] = Tileset.WALL;
                }
                if (!(world[cx-1][i].description().equals("floor"))) {
                    world[cx-1][i] = Tileset.WALL;
                }
                if (!(world[cx][i].description().equals("floor"))) {
                    if (i == cy1 || i == cy2) {
                        world[cx][i] = Tileset.WALL;
                    } else {
                        world[cx][i] = Tileset.FLOOR;
                    }
                }
            }
        }
    }
    public static void drawHorizontalHalls (int cy, int cx1, int cx2, TETile[][] world) {
        if (cx1 > cx2) {
            for (int i = cx2; i < cx1 + 1; i++) {
                if (!(world[i][cy+1].description().equals("floor"))) {
                    world[i][cy+1] = Tileset.WALL;
                }
                if (!(world[i][cy-1].description().equals("floor"))) {
                    world[i][cy-1] = Tileset.WALL;
                }
                if (!(world[i][cy].description().equals("floor"))) {
                    if (i == cx1 || i == cx2) {
                        world[i][cy] = Tileset.WALL;
                    } else {
                        world[i][cy] = Tileset.FLOOR;
                    }
                }
            }
        } else {
            for (int i = cx1; i < cx2 + 1; i++) {
                if (!(world[i][cy+1].description().equals("floor"))) {
                    world[i][cy+1] = Tileset.WALL;
                }
                if (!(world[i][cy-1].description().equals("floor"))) {
                    world[i][cy-1] = Tileset.WALL;
                }
                if (!(world[i][cy].description().equals("floor"))) {
                    if (i == cx1 || i == cx2) {
                        world[i][cy] = Tileset.WALL;
                    } else {
                        world[i][cy] = Tileset.FLOOR;
                    }
                }
            }
        }
    }
}
