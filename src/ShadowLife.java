import actor.*;
import actor.mobile.*;
import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;
import maplogic.Location;
import java.io.FileNotFoundException;
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
        getInput();
        createSetting();
    }

    // Start of the program
    public static void main(String[] args) {
        new ShadowLife().run();
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
            stopGame(SUCCESS);
        }
        if (tickCount > maxTicks) {
            stopGame(FAILURE);
        }
    }

    // Opens the given worldFile and creates Actors for the game
    private static void createSetting() {
        final int EXPECTED_INPUTS = 3;
        final int TYPE_POS = 0;
        final int X_POS = 1;
        final int Y_POS = 2;
        int lineNumber = 0;

        try (Scanner scanner = new Scanner(new FileReader(worldFile))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String[] input = scanner.nextLine().split(",");
                String type_str;
                double x;
                double y;
                ActorType type;

                // Check whether the Actor type given is valid.
                if (input.length > EXPECTED_INPUTS) {
                    throw new InvalidInputException(worldFile, lineNumber);
                }

                // After splitting, the first value should be the type of Actor, the second
                // and third values should be the x and y coordinates in integer form.
                type_str = input[TYPE_POS];
                type = ActorType.valueOf(type_str.toUpperCase());
                x = Integer.parseInt(input[X_POS]);
                y = Integer.parseInt(input[Y_POS]);

                // Create the Actor instance.
                switch (type) {
                    case TREE:
                    case STOCKPILE:
                    case HOARD:
                        if (Location.isDefinedTile(x, y)) new FruitStorage(type, x, y);
                        break;
                    case GATHERER:
                        if (Location.isDefinedTile(x, y)) new Gatherer(x, y, true);
                        break;
                    case THIEF:
                        if (Location.isDefinedTile(x, y)) new Thief(x, y, true);
                        break;
                    default:
                        new Actor(type, x, y);
                }
            }
        }
        catch (FileNotFoundException e) {
            new InvalidInputException(worldFile).handler();
        }
        catch (InvalidInputException e) {
            e.handler();
        }
        catch (NumberFormatException e) {
            new InvalidInputException(worldFile, lineNumber).handler();
        }
        catch (Exception e) {
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
    private void stopGame(int exitStatus) {
        if (exitStatus == SUCCESS) {
            System.out.println(tickCount + " ticks");
            FruitStorage.tallyHoardsAndStockpiles();
            System.exit(SUCCESS);
        }
        else {
            System.out.println("Timed out");
            System.exit(FAILURE);
        }
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
            new InvalidInputException().handler();
        }

        try {
            tickRate = Integer.parseInt(inputs[TICKRATE_POS]);
            maxTicks = Integer.parseInt(inputs[MAXTICKS_POS]);
            worldFile = inputs[WORLDFILE_POS];
        }
        catch (Exception e) {
            new InvalidInputException().handler();
        }
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