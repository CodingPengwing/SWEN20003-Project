import actor.Actor;
import actor.ActorType;
import actor.FruitStorage;
import actor.movable.Gatherer;
import actor.movable.MovableActor;
import actor.movable.Thief;
import bagel.Image;
import maplogic.Location;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/** This class manages all Actors in a world, all Actors are stored here. The class is
 * handles the updating and rendering these Actors, while also rendering the background
 * for the game. It creates Actors using the world file given in the constructor, and
 * throws InvalidInputExceptions if the file is not in the correct format.
 */
public class World {
    // Default background image
    private final static Image background = new Image("src/res/images/background.png");

    // Background rendering position
    private final static int BACKGROUND_X = 0;
    private final static int BACKGROUND_Y = 0;

    // To store the current Actors in the game that don't move
    private final static ArrayList<Actor> stationaryActors = new ArrayList<>();
    // To store all the Gatherers in the game
    private final static ArrayList<Gatherer> gatherers = new ArrayList<>();
    // To store all the Thieves in the game
    private final static ArrayList<Thief> thieves = new ArrayList<>();

    /** Constructs a World containing all the Actors of that world given an input
     * world file. If the file is not a csv with 3 input arguments per line, or
     * the second and third inputs are not integers, an error will be thrown.
     * @param worldFile This is the world file where input is taken from.
     * @throws InvalidInputException This is the Exception thrown if the file is
     * not in the correct format.
     */
    public World(String worldFile) throws InvalidInputException { createSetting(worldFile); }

    /** Updates the state of the world by triggering the tick actions for each
     * Gatherer and Thief. As these MovableActors move around, the state of
     * the world also changes through their interaction with other Actors.
     */
    public void updateState() {
        int currentNumGatherers = gatherers.size();
        // Tick all current Gatherers from last tick only
        for (int i = 0; i < currentNumGatherers; i++) {
            Gatherer gatherer = gatherers.get(i);
            Actor newActor = gatherer.tick(stationaryActors, gatherers);
            // If a new Gatherer was created during tick, add to 'gatherers'
            if (newActor != null) gatherers.add((Gatherer) newActor);
        }
        int currentNumThieves = thieves.size();
        // Tick all current Thief's from last tick only
        for (int i = 0; i < currentNumThieves; i++) {
            Thief thief = thieves.get(i);
            Actor newActor = thief.tick(stationaryActors, gatherers);
            // If a new Thief was created during tick, add to 'gatherers'
            if (newActor != null) thieves.add((Thief) newActor);
        }
    }

    /** Renders all Actors and the background in the game's current state. */
    public void renderState() {
        background.drawFromTopLeft(BACKGROUND_X, BACKGROUND_Y);
        for (Actor actor : stationaryActors) actor.render();
        for (Actor actor : gatherers) actor.render();
        for (Actor actor : thieves) actor.render();
    }

    /** Prints the number of fruits at each Hoard and Stockpile to stdout in
     * the order these Actors were created.
     */
    public void printEndState() {
        for (Actor actor : stationaryActors) {
            if (actor.getType() == ActorType.HOARD || actor.getType() == ActorType.STOCKPILE) {
                FruitStorage fs = (FruitStorage) actor;
                System.out.println(fs.getNumFruit());
            }
        }
    }

    /** Returns the current state of the world (active or inactive). Depending
     * on whether there are still active Actors.
     * @return true if still active, false otherwise.
     */
    public boolean isActive() {
        for (MovableActor actor : gatherers) { if (actor.isActive()) return true; }
        for (MovableActor actor : thieves) { if (actor.isActive()) return true; }
        return false;
    }

    // Opens the given worldFile and creates Actors for the game by scanning each line.
    // Will throw InvalidInputException with the specific message for the error if the
    // file is not found or any of the lines are in the wrong format.
    private static void createSetting(String worldFile) throws InvalidInputException {
        final int EXPECTED_INPUTS = 3;
        final int TYPE_POS = 0;
        final int X_POS = 1;
        final int Y_POS = 2;
        int lineNumber = 0;

        // Open the file
        try (Scanner scanner = new Scanner(new FileReader(worldFile))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String[] input = scanner.nextLine().split(",");

                // Check whether the number of inputs given is valid.
                if (input.length > EXPECTED_INPUTS) { throw new InputMismatchException(); }

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
                    Actor newActor;
                    // Create the Actor instance.
                    switch (actorType) {
                        case GATHERER:
                            newActor = new Gatherer(x, y);
                            gatherers.add((Gatherer) newActor);
                            break;
                        case THIEF:
                            newActor = new Thief(x, y);
                            thieves.add((Thief) newActor);
                            break;
                        // If the Actor is a FruitStorage type
                        case TREE: case STOCKPILE: case HOARD:
                            newActor = new FruitStorage(actorType, x, y);
                            stationaryActors.add(newActor); break;
                        // If the Actor is any other type
                        default:
                            newActor = new Actor(actorType, x, y);
                            stationaryActors.add(newActor);
                    }
                }
            }
        }
        // This error is thrown when the world file cannot be found. Re-throws an
        // Exception with the message indicating unknown file.
        catch (FileNotFoundException e) { throw new InvalidInputException(worldFile); }
        // All other errors are thrown when the input given in a line is not well-defined.
        // Re-throws an Exception with the message indicating the line with wrong format.
        catch (Exception e) { throw new InvalidInputException(worldFile, lineNumber); }
    }
}
