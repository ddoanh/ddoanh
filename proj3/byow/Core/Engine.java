package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.awt.*;

public class Engine {
    public enum Direction {UP, RIGHT, DOWN, LEFT};
    public enum Status {START, SEED, PLAY};
    private static final int WIDTH = BSPTree.width;
    private static final int HEIGHT = BSPTree.height;
    private TERenderer ter;
    private StringBuilder inputs;
    private Status status;
    private StringBuilder seedProcessing;
    private long seed;
    private static final Font SUBTITLE_FONT = new Font("TIMES", Font.BOLD, 20);
    private BSPTree world;

    public Engine() {
        ter = new TERenderer();
    }

    /**
     * Method used for exploring a fresh world. This method handles all inputs.
     */
    public void interactWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        while (true) {
            initialize();
            InputSource inputSource = new KeyboardInputSource();
            drawMenu();
            while (status != Status.PLAY) {
                char ch = Character.toUpperCase(inputSource.getNextKey());
                parseMenuChoice(ch, true);
            }
            drawWorld();
            while (status == Status.PLAY) {
                char ch = Character.toUpperCase(inputSource.getNextKey());
                parseMovement(ch, true);
            }
            inputSource.getNextKey();
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        initialize();
        InputSource inputSource = new StringInputSource(input);
        while (status != Status.PLAY && inputSource.possibleNextInput()) {
            char ch = Character.toUpperCase(inputSource.getNextKey());
            parseMenuChoice(ch, false);
        }
        while (status == Status.PLAY && inputSource.possibleNextInput()) {
            char ch = Character.toUpperCase(inputSource.getNextKey());
            parseMovement(ch, false);
        }
        return world.worldframe();
    }

    /**
     * Handles inputs from the main menu and the prompt menu.
     *
     * @param ch the character
     * @param draw if it's needed to draw
     */
    private void parseMenuChoice(char ch, boolean draw) {
        if (status == Status.START && ch == 'L') {
            load();
//            drawWorld();
        }
        else if (status == Status.START && ch == 'N') {
            status = Status.SEED;
            inputs.append(ch);
            if (draw) {
                drawSeedScreen();
            }
        }
        else if (status == Status.SEED && Character.isDigit(ch)) {
            seedProcessing.append(ch);
            inputs.append(ch);
            if (draw) {
                drawSeedScreen();
            }
        }
        else if (status == Status.SEED && ch == 'S') {
            if (seedProcessing.length() > 25) {
                seed = Long.parseLong(seedProcessing.substring(0, 25));
            }
            else {
                seed = Long.parseLong(seedProcessing.toString());
            }
            inputs.append(ch);
            world = new BSPTree(String.valueOf(seed));
            status = Status.PLAY;
        }
        else if (ch == 'Q') {
            System.exit(0);
        }
    }

    private void parseMovement(char ch, boolean draw) {
        switch (ch) {
            case 'A': status = world.movePlayer(Direction.LEFT);
                inputs.append(ch);
                break;
            case 'W': status = world.movePlayer(Direction.UP);
                inputs.append(ch);
                break;
            case 'D': status = world.movePlayer(Direction.RIGHT);
                inputs.append(ch);
                break;
            case 'S': status = world.movePlayer(Direction.DOWN);
                inputs.append(ch);
                break;
            case 'P' : world.light_on = true;
                inputs.append(ch);
                break;
            case 'O' : world.light_on = false;
                inputs.append(ch);
                break;
            case ':': save();
                break;
            case 'Q': System.exit(0);
        }
        if (draw) {
            drawWorld();
        }
    }

    private void initialize() {
        inputs = new StringBuilder("");
        status = Status.START;
        seedProcessing = new StringBuilder("");
        seed = -1;
        world = null;
    }

    private void drawMenu() {
        StdDraw.clear(new Color(195, 127, 108, 255));
        StdDraw.setPenColor(new Color(61, 14, 9, 255));
        StdDraw.setPenRadius();
        Font titleFONT = new Font("TIMES", Font.BOLD, 50);
        StdDraw.setFont(titleFONT);
        StdDraw.text(WIDTH / 2, HEIGHT - 14, "CS 61B: THE GAME");
        StdDraw.setFont(SUBTITLE_FONT);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "NEW GAME (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 4, "LOAD GAME (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 8, "QUIT (Q)");
        StdDraw.show();
    }

    private void drawSeedScreen() {
        String input = inputs.toString();
        input = input.substring(input.indexOf('N') + 1);

        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.setPenRadius();
        Font titleFONT = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setFont(titleFONT);
        StdDraw.text(WIDTH / 2, HEIGHT - 18, "NEW GAME");
        Font seedFont = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(seedFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "ENTER SEED & PRESS \"S\".");
        StdDraw.setPenColor(Color.pink);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 4, input);
        StdDraw.show();
    }

//    private void addInstruction() {
//        Font denote = new Font("Times", Font.BOLD, 30);
//        StdDraw.setFont(denote);
//        StdDraw.setPenColor(new Color(0, 27, 220, 255));
//        StdDraw.textLeft(0, HEIGHT - 1, "O:Turn the light on");
//        StdDraw.textLeft(0, HEIGHT - 3, "O:Turn the light off");
//    }

    private void drawWorld() {
        TETile[][] currWorld = world.worldframe();
        hud(currWorld);
        ter.renderFrame(currWorld, world.player, world);
        StdDraw.show();
    }

//    private void addNotation() {
//        StdDraw.setPenColor(Color.yellow);
//        StdDraw.textRight(WIDTH - 1, HEIGHT - 1, "\":\"O: Turn light off");
//        StdDraw.textRight(WIDTH - 2, HEIGHT - 2, "\":\"P: Turn light on");
//    }

    private double mouseX;
    private double mouseY;
    private void hud(TETile[][] thisWorld) {
        while (!StdDraw.hasNextKeyTyped()) {
            int pointX = (int) Math.floor(StdDraw.mouseX());
            int pointY = (int) Math.floor(StdDraw.mouseY());
            if (mouseChangesCheck(StdDraw.mouseX(), StdDraw.mouseY())) {
                pointX = (int) Math.floor(StdDraw.mouseX());
                pointY = (int) Math.floor(StdDraw.mouseY());
                ter.renderFrame(thisWorld, world.player, world);
            }
            ter.renderFrame(thisWorld, world.player, world);
            StdDraw.setPenColor(Color.yellow);
            Font hudFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(hudFont);
            //StdDraw.textLeft(1, HEIGHT - 1, thisWorld[pointX][pointY].description());
            StdDraw.textRight(WIDTH - 1, HEIGHT - 1, thisWorld[pointX][pointY].description());
            StdDraw.show();
        }
    }


    private boolean mouseChangesCheck(double x, double y) {
        if (x == mouseX || y == mouseY) return false;
        else {
            mouseX = x;
            mouseY = y;
            return true;
        }
    }
    /**
     * Saves the current inputs to a text file.
     */
    private void save() {
        Out out = new Out("./out/production/proj3/byow/save_data.txt");
        out.println(inputs.toString());
        out.close();
    }

    /**
     * Reads the saved text file and handles the inputs in the file.
     */
    private void load() {
        In in = new In("./out/production/proj3/byow/save_data.txt");
        if (in.exists()) {
            String s = in.readAll();
            interactWithInputString(s);
        } else {
            System.exit(0);
        }
    }

}