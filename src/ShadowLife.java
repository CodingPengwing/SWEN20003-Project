import actor.*;
import actor.mobile.*;
import maplogic.*;
import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ShadowLife extends AbstractGame {
    private static final int SUCCESS = 0;
    private static final int FAILURE = -1;
    private static final int GAME_HEIGHT = 768;
    private static final int GAME_WIDTH = 1024;
    private static final int BACKGROUND_X = 0;
    private static final int BACKGROUND_Y = 0;
    private static int tickRate;
    private static int maxTicks;
    private static String worldFile;
    private long lastTick;
    private int tickCount;
    private final Image background = new Image("src/res/images/background.png");

    public ShadowLife() {
        super(GAME_WIDTH, GAME_HEIGHT, "Shadow Life");
        tickCount = 0;
        lastTick = System.currentTimeMillis();
    }

    // Start of the program
    public static void main(String[] args) {
        ShadowLife game = new ShadowLife();
        getInput();
        createSetting();
        game.run();
    }

    // Renders the game constantly, provides the game with updates every tick
    @Override
    public void update(Input input) {
        long currentTime = System.currentTimeMillis();

        // Update the state of the game every tick
        if (currentTime - lastTick > tickRate) {
            updateState();
            lastTick = currentTime;
            tickCount++;
        }
        renderState();

        // Check if the game has ended
        if (!MobileActor.actorsActive()) {
            stopGame();
        }
        if (tickCount > maxTicks) {
            System.out.println("Timed out");
            System.exit(FAILURE);
        }
    }

    // Opens the given worldFile and creates Actors for the game
    private static void createSetting() {
        final int EXPECTED_INPUTS = 3;
        final int TYPE_POS = 0;
        final int X_POS = 1;
        final int Y_POS = 2;
        int lineNumber = 1;

        try (Scanner scanner = new Scanner(new FileReader(worldFile))) {
            while (scanner.hasNextLine()) {
                String[] input = scanner.nextLine().split(",");

                // Error checking the input
                if (input.length != EXPECTED_INPUTS) {
                    System.out.println("error: in file \"" + worldFile + "\" at line " + lineNumber);
                    System.exit(FAILURE);
                }

                //
                for (char c : (input[X_POS]+input[Y_POS]).toCharArray()) {
                    if (!Character.isDigit(c)) {
                        System.out.println("error: in file \"" + worldFile + "\" at line " + lineNumber);
                        System.exit(FAILURE);
                    }
                }

                // After splitting, the first value should be the type of Actor,
                // the second and third values should be the x and y coordinates.
                String type = input[TYPE_POS];
                double x = Double.parseDouble(input[X_POS]);
                double y = Double.parseDouble(input[Y_POS]);

                System.out.println("MAKING TYPE "  + type);
                // Create the Actor instance.
                // Check whether the Actor type given is valid.
                switch (type) {

                    // Location is checked after type checking to detect undefined types first
                    case Actor.GOLDEN_TREE:
                    case Actor.PAD:
                    case Actor.FENCE:
                    case Actor.SIGN_UP:
                    case Actor.SIGN_DOWN:
                    case Actor.SIGN_LEFT:
                    case Actor.SIGN_RIGHT:
                    case Actor.POOL:
                        if (Location.isWellDefined(x, y)) new Actor(type, x, y);
                        break;
                    case Actor.TREE:
                    case Actor.STOCKPILE:
                    case Actor.HOARD:
                        if (Location.isWellDefined(x, y)) new FruitStorage(type, x, y);
                        break;
                    case Actor.GATHERER:
                        // Initial Gatherer's have a default direction heading left
                        if (Location.isWellDefined(x, y)) new Gatherer(x, y, true);
                        break;
                    case Actor.THIEF:
                        // Initial Thief's have a default direction heading up
                        if (Location.isWellDefined(x, y)) new Thief(x, y, true);
                        break;
                    default:
                        System.out.println("error: in file \"" + worldFile + "\" at line " + lineNumber);
                        System.exit(FAILURE);
                }
                lineNumber++;
            }
        }
        catch (Exception e) {
            System.out.println("error: file \"" + worldFile + "\" not found");
            System.exit(FAILURE);
            e.printStackTrace();
        }
    }

    // Draws all Actors and the background in the game's current state
    private void renderState() {
        background.drawFromTopLeft(BACKGROUND_X, BACKGROUND_Y);
        Actor.renderStationaryActors();
        MobileActor.renderMobileActors();
    }

    // Update the state of the game
    private void updateState() {
        MobileActor.tickMobileActors();
    }

    // Stops the game and provides output to stdout
    private void stopGame() {
        System.out.println(tickCount + " ticks");
        FruitStorage.tallyHoardsAndStockpiles();
        System.exit(SUCCESS);
    }

    // Gets input from stdin
    private static void getInput() {
        final int EXPECTED_INPUTS = 3;
        final int TICKRATE_POS = 0;
        final int MAXTICKS_POS = 1;
        final int WORLDFILE_POS = 2;

        String inputs[] = argsFromFile();

        // Check number of inputs
        if (inputs.length != EXPECTED_INPUTS) {
            System.out.println("usage: ShadowLife <tick rate> <max ticks> <world file>");
            System.exit(FAILURE);
        }

        // Check for non integer input
        for (char c : (inputs[TICKRATE_POS]+inputs[MAXTICKS_POS]).toCharArray()) {
            if (!Character.isDigit(c)) {
                System.out.println("usage: ShadowLife <tick rate> <max ticks> <world file>");
                System.exit(FAILURE);
            }
        }

        tickRate = Integer.parseInt(inputs[TICKRATE_POS]);
        maxTicks = Integer.parseInt(inputs[MAXTICKS_POS]);
        worldFile = inputs[WORLDFILE_POS];
    }

    private static String[] argsFromFile() {
        try {
            return Files.readString(Path.of("args.txt"), Charset.defaultCharset()).split(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}