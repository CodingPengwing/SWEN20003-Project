import actor.*;
import actor.movable.*;
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

/** This is the main class that runs the program, it inherits from AbstractGame in
 * the 'bagel' package. It overrides its parent's update method to define the general
 * update logic for the game. It is responsible for getting input from command line
 * as well as the world file provided. All inputs will be checked and errors handled
 * primarily using InvalidInputException. The timing (keeping ticks) of the game and
 * other general management tasks also happen in here. This class does not handle
 * Actors, such tasks are entirely delegated to the Actor and MovableActor classes.
 * Instead, this class keeps a higher level view over the program.
 *
 * The documentation for AbstractGame (as part of the bagel package) is provided here:
 * https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/
 */
public class ShadowLife extends AbstractGame {
    // Exit statuses for the program
    private final static int SUCCESS = 0;
    private final static int FAILURE = -1;

    // Default game dimensions
    private final static int GAME_HEIGHT = 768;
    private final static int GAME_WIDTH = 1024;

    // Background rendering position
    private final static int BACKGROUND_X = 0;
    private final static int BACKGROUND_Y = 0;

    // Tick (time) management for the game
    private static int tickRate;
    private static int maxTicks;
    private long lastTick;
    private int tickCount = 0;

    // Input world file
    private static String worldFile;
    // Default background image
    private final Image background = new Image("src/res/images/background.png");

    /** Constructs a ShadowLife, which is an instance of AbstractGame.
     */
    public ShadowLife() {
        // The default game dimensions and the name "Shadow Life"
        super(GAME_WIDTH, GAME_HEIGHT, "Shadow Life");
        // The starting time of the program
        lastTick = System.currentTimeMillis();
        // This method gets input from command line
        getInput();
        // This method gets input from the world file
        createSetting();
    }

    /** This is the start of the program. Creates an instance of ShadowLife and runs it.
     * @param args main method's arguments
     */
    public static void main(String[] args) {
        new ShadowLife().run();
    }

    /** This method provides AbstractGame with updates to the program. It renders
     * the game constantly, but only provides changes to the game after a full tick.
     * @param input This is the same input as defined in AbstractGame
     */
    @Override
    public void update(Input input) {
        // Get the current time
        long currentTime = System.currentTimeMillis();

        // Check whether a full tick has passed
        if (currentTime - lastTick > tickRate) {
            updateState();
            lastTick = currentTime;
            tickCount++;
        }
        renderState();

        // Check whether the game should be ended
        if (!MovableActor.actorsActive()) { stopGame(SUCCESS); }
        if (tickCount > maxTicks) { stopGame(FAILURE); }
    }

    // Updates the state of the game. The whole implementation is delegated
    // to the MovableActor class. As MovableActors move around, the state of
    // the world also changes through their interaction with other Actors.
    private void updateState() { MovableActor.tickMovableActors(); }

    // Renders all Actors and the background in the game's current state.
    // The rendering of Actors are delegated to Actor and MovableActor
    // classes.
    private void renderState() {
        background.drawFromTopLeft(BACKGROUND_X, BACKGROUND_Y);
        Actor.renderStationaryActors();
        MovableActor.renderMovableActors();
    }

    // Stops the game and provides output to stdout depending on whether
    // it is called to exit success or failure.
    private void stopGame(int exitStatus) {
        // If the game successfully finished and Actors are all inactive.
        if (exitStatus == SUCCESS) {
            System.out.println(tickCount + " ticks");
            FruitStorage.tallyHoardsAndStockpiles();
        }
        // If the game hasn't finished, but more than the specified max
        // ticks have passed.
        else {
            System.out.println("Timed out");
        }
        System.exit(exitStatus);
    }

    // Opens the given worldFile and creates Actors for the game by scanning each line.
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

                // Check whether the number of inputs given is valid.
                if (input.length > EXPECTED_INPUTS) {
                    throw new InvalidInputException(worldFile, lineNumber);
                }

                // After splitting, the first value should be the type of Actor, the
                // second and third values should be the x and y coordinates in integer
                // form. This will throw an error if x and y are not valid integers.
                String typeString = input[TYPE_POS];
                double x = Integer.parseInt(input[X_POS]);
                double y = Integer.parseInt(input[Y_POS]);

                // Convert String into ActorType variable, this will throw an error
                // if the actor type is undefined.
                ActorType actorType = ActorType.valueOf(typeString.toUpperCase());

                // Check whether the location given is well-defined.
                if (Location.isDefinedTile(x, y)) {
                    // Create the Actor instance.
                    switch (actorType) {
                        case GATHERER:
                            new Gatherer(x, y);
                            break;
                        case THIEF:
                            new Thief(x, y);
                            break;
                        // If the Actor is a FruitStorage type
                        case TREE:
                        case STOCKPILE:
                        case HOARD:
                            new FruitStorage(actorType, x, y);
                            break;
                        // If the Actor is any other type
                        default:
                            new Actor(actorType, x, y);
                    }
                }
            }
        }
        // This error is thrown when the world file cannot be found.
        catch (FileNotFoundException e) {
            exceptionHandler(new InvalidInputException(worldFile)); }
        // This error is thrown when the input given in a line is not well-defined.
        catch (InvalidInputException e) {
            exceptionHandler(e); }
        // This error is also thrown when the input given in a line is not well-defined.
        catch (IllegalArgumentException e) {
            exceptionHandler(new InvalidInputException(worldFile, lineNumber)); }
        // In case any other Exceptions come up.
        catch (Exception e) {
            e.printStackTrace();
            System.exit(FAILURE);
        }
    }

    // Gets input from stdin and ensures that it is in the correct format.
    private static void getInput() {
        final int EXPECTED_INPUTS = 3;
        final int TICKRATE_POS = 0;
        final int MAXTICKS_POS = 1;
        final int WORLDFILE_POS = 2;
        // The arguments taken from 'args.txt' file, currently pretended to be command line.
        String inputs[] = argsFromFile();
        // Check number of inputs
        if (inputs.length != EXPECTED_INPUTS) { exceptionHandler(new InvalidInputException()); }
        // Assign inputs
        try {
            tickRate = Integer.parseInt(inputs[TICKRATE_POS]);
            maxTicks = Integer.parseInt(inputs[MAXTICKS_POS]);
            worldFile = inputs[WORLDFILE_POS];
        }
        catch (Exception e) { exceptionHandler(new InvalidInputException()); }
    }

    // Gets the arguments provided in 'args.txt' file, currently pretended to be command line.
    private static String[] argsFromFile() {
        try {
            return Files.readString(Path.of("args.txt"), Charset.defaultCharset()).split(" ");
        }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    // This is the standard way to handle exceptions in this program.
    // Print the message, then exit with status -1.
    private static void exceptionHandler(Exception e) {
        System.out.println(e.getMessage());
        System.exit(FAILURE);
    }
}