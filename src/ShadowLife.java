import bagel.AbstractGame;
import bagel.Input;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/** This is the main class that runs the program, keeps a high level view over the program.
 * It inherits from AbstractGame in the 'bagel' package and overrides AbstractGame's update
 * method to define the general update logic for the game. It is responsible for getting
 * input from command line, then constructs a World object that will manage the updating
 * and rendering of Actors. Command line inputs are checked for errors (wrong number of
 * args, wrong input types). The timing (keeping ticks) of the game and other general
 * management tasks also happen in here.
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

    // Tick (time) management for the game
    private static int tickRate;
    private static int maxTicks;
    private long lastTick;
    private int tickCount = 0;

    // The World instance that manages all the Actors
    private World world;

    /** Constructs a ShadowLife, which is an instance of AbstractGame. Uses the
     * command line args to get the input world file, while checking for errors.
     * A World instance will be created to store and manage all the Actors of
     * the game.
     */
    public ShadowLife() {
        // The default game dimensions and the name "Shadow Life"
        super(GAME_WIDTH, GAME_HEIGHT, "Shadow Life");
        // The starting time of the program
        lastTick = System.currentTimeMillis();
        try {
            String worldFile = getCommandLineInput();
            world = new World(worldFile);
        }
        catch (InvalidInputException e) { inputExceptionHandler(e); }
    }

    /** This is the start of the program. Creates an instance of ShadowLife and runs it.
     * @param args standard main args
     */
    public static void main(String[] args) { new ShadowLife().run(); }

    /** This method provides AbstractGame with updates to the program. It renders
     * the game constantly, but only provides changes to the game after a full tick.
     * @param input input field required for AbstractGame's update()
     */
    @Override
    public void update(Input input) {
        // Get the current time
        long currentTime = System.currentTimeMillis();
        // Check whether a full tick has passed
        if (currentTime - lastTick > tickRate) {
            world.updateState();
            lastTick = currentTime;
            tickCount++;
        }
        world.renderState();

        // Check whether the game should be ended
        if (!world.isActive()) { stopGame(SUCCESS); }
        if (tickCount > maxTicks) { stopGame(FAILURE); }
    }

    // Stops the game and provides output to stdout depending on whether
    // it is called to exit success or failure.
    private void stopGame(int exitStatus) {
        // If the game successfully finished.
        if (exitStatus == SUCCESS) {
            System.out.println(tickCount + " ticks");
            world.printEndState();
        }
        // If the game hasn't finished, but more than the specified max
        // ticks have passed.
        else { System.out.println("Timed out"); }
        System.exit(exitStatus);
    }

    // Gets input from stdin and ensures that it is in the correct format.
    private static String getCommandLineInput() {
        final int EXPECTED_INPUTS = 3;
        final int TICKRATE_POS = 0;
        final int MAXTICKS_POS = 1;
        final int WORLDFILE_POS = 2;
        String worldFile = null;

        try {
            // The arguments taken from 'args.txt' file, currently pretended to be command line.
            String inputs[] = argsFromFile();
            // Check number of inputs
            if (inputs.length != EXPECTED_INPUTS) { throw new InvalidInputException(); }

            // Assign inputs
            tickRate = Integer.parseInt(inputs[TICKRATE_POS]);
            maxTicks = Integer.parseInt(inputs[MAXTICKS_POS]);
            worldFile = inputs[WORLDFILE_POS];
            // Make sure the input is positive
            if (tickRate < 0 || maxTicks < 0) { throw new InvalidInputException(); }
        }
        catch (InvalidInputException e) { inputExceptionHandler(e); }
        catch (IllegalArgumentException e) {
            inputExceptionHandler(new InvalidInputException());
        }
        catch (Exception e) { e.printStackTrace(); System.exit(FAILURE); }

        return worldFile;
    }

    // Gets the arguments provided in 'args.txt' file, currently pretended to be command line.
    private static String[] argsFromFile() {
        try { return Files.readString(Path.of("args.txt"), Charset.defaultCharset()).split(" "); }
        catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    // This is the standard way to handle InvalidInputExceptions in this program.
    // Print the message, then exit with status -1.
    private static void inputExceptionHandler(InvalidInputException e) {
        System.out.println(e.getMessage());
        System.exit(FAILURE);
    }
}