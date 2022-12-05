package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
//    public static final TETile WALL = new TETile('♥', new Color(215, 84, 157), new Color(229, 156, 238), "wall");
//    public static final TETile FLOOR = new TETile(' ', new Color(239, 207, 239, 247),  new Color(239, 207, 239, 247), "floor");
//    public static final TETile NOTHING = new TETile(' ', new Color(162, 236, 225, 210), new Color(162, 236, 225, 210), "nothing");

    //    public static final TETile WALL = new TETile("♥", new Color(69, 54, 48), new Color(96, 73, 65, 255), "wall");
//    public static final TETile FLOOR = new TETile(' ', new Color(239, 207, 239, 247),  new Color(239, 207, 239, 247), "floor");
//    public static final TETile NOTHING = new TETile(' ', new Color(222, 187, 147, 255), new Color(222, 187, 147, 255), "nothing");
    public static final TETile WALL = new TETile('♥', new Color(122, 60, 57), new Color(178, 94, 90), "wall");
    //public static final TETile FLOOR = new TETile('·', new Color(236, 207, 205, 255), new Color(199, 138, 135, 255), "floor");
    public static final TETile FLOOR = new TETile(' ', new Color(199, 138, 135, 255), new Color(199, 138, 135, 255),"floor");

    public static final TETile NOTHING = new TETile(' ', new Color(230, 203, 202, 255), new Color(230, 203, 202, 255), "nothing");

    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀',  new Color(230, 203, 202, 255),  new Color(199, 138, 135, 255), "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile CAT = new TETile('⚘', new Color(222, 187, 147, 255), new Color(222, 187, 147, 255), "crazy frog", "pixel_frog_jumping.png");
}

