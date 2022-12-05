package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Random;

public class BSPTree {
    Container root;
    ArrayDeque<Container> containerTree;
    List<RoomGenerator> rooms;
    static int height = 50;
    // 50 works for most seeds
    static int width = 80;
    static int splitMin = 10;
    static int numberOfIterations = 7;
    private Random RANDOM;
    public boolean light_on;
    private TETile[][] world;
    public Tile player;

    public BSPTree(String seed) {
        light_on = false;
        root = new Container(0, 0, width, height);
        containerTree = new ArrayDeque<>();
        containerTree.add(root);

        rooms = new ArrayList<>();

        String seedStr = "";
        int position = seed.length() - 1;
        if (seed.charAt(0) != 'n' && seed.charAt(0) != 'N') {
            if (seed.charAt(position) == 's' && seed.charAt(position) == 'S') {
                System.exit(0);
            }
        }
        for (int i = 1; i < seed.length(); i++) {
            if (seed.charAt(i) != 's' && seed.charAt(i) != 'S') {
                seedStr += seed.charAt(i);
            }
        }
        Long theseed = Long.parseLong(seedStr);
        RANDOM = new Random(theseed);
        generateWorld();
    }

    public TETile[][] worldframe() {return world;}

    public TETile[][] generateWorld() {
        world = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        while (numberOfIterations > 0) {
            Container curr = containerTree.remove();
            if (split(curr) == true) {
                containerTree.add(curr.left);
                containerTree.add(curr.right);
                numberOfIterations -= 1;
            }
        }

        createRooms(root, world);

        for (RoomGenerator r: rooms) {
            RoomGenerator.drawRoom(r, world);
        }

        Hallways.connectHallways(root, world);

        int rand = RANDOM.nextInt(rooms.size());
        RoomGenerator playerPos = rooms.get(rand);
        player = new Tile(playerPos.w / 2, playerPos.h / 2);

        if (!world[player.x][player.y].description().equals(Tileset.FLOOR)) {
            world[player.x][player.y] = Tileset.CAT;
        }

        return world;
    }
    public Engine.Status movePlayer(Engine.Direction d) {
        Tile target = target(d);
        TETile t = world[target.x][target.y];
        if (!t.equals(Tileset.WALL)) {
            if (!world[player.x][player.y].description().equals(Tileset.FLOOR)) {
                world[player.x][player.y] = Tileset.FLOWER;
                world[target.x][target.y] = Tileset.CAT;
            }
            player = target;
        }
        return Engine.Status.PLAY;
    }

    private Tile target(Engine.Direction d) {
        switch(d) {
            case UP: return new Tile (player.x, player.y + 1);
            case RIGHT: return new Tile (player.x + 1, player.y);
            case DOWN: return new Tile (player.x, player.y - 1);
            case LEFT: return new Tile (player.x - 1, player.y);
            default: return player;
        }
    }

    private void createRooms(Container c, TETile[][] world) {
        if (c != null) {
            if (c.left == null && c.right == null) {
                rooms.add(new RoomGenerator(c, world, RANDOM));
            }
            createRooms(c.left, world);
            createRooms(c.right, world);
        }
    }

    public boolean split (Container c) {
        Container leftRoom, rightRoom;

        // container is too small to split
        if (c.w < splitMin * 2 || c.h < splitMin * 2) {
            return false;
        }

        if (c.w > c.h) {
            int splitSize = RANDOM.nextInt(c.w - splitMin * 2 + 1) + splitMin;
            leftRoom = new Container (c.x, c.y, splitSize, c.h);
            rightRoom = new Container (c.x + leftRoom.w - 1, c.y, c.w - splitSize, c.h);
        } else {
            int splitSize = RANDOM.nextInt(c.h - splitMin * 2 + 1) + splitMin;
            leftRoom = new Container (c.x, c.y, c.w, splitSize);
            rightRoom = new Container (c.x, c.y + leftRoom.h - 1, c.w, c.h - splitSize);
        }
        c.left = leftRoom;
        c.right = rightRoom;
        return true;
    }
}

