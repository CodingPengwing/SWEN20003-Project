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
import java.util.InputMismatchException;
import java.util.Scanner;

public class World {
    // Default background image
    private final static Image background = new Image("src/res/images/background.png");

    // Background rendering position
    private final static int BACKGROUND_X = 0;
    private final static int BACKGROUND_Y = 0;

    public World(String worldFile) throws InvalidInputException { createSetting(worldFile); }

    // Updates the state of the world. The whole implementation is delegated
    // to the MovableActor class. As MovableActors move around, the state of
    // the world also changes through their interaction with other Actors.
    public void updateState() {
        MovableActor.tickMovableActors();
    }

    // Renders all Actors and the background in the game's current state.
    // The rendering of Actors are delegated to Actor and MovableActor
    // classes.
    public void renderState() {
        background.drawFromTopLeft(BACKGROUND_X, BACKGROUND_Y);
        Actor.renderStationaryActors();
        MovableActor.renderMovableActors();
    }

    public void printEndState() { FruitStorage.tallyHoardsAndStockpiles(); }

    public boolean isActive() {
        if (MovableActor.actorsActive()) return true;
        return false;
    }

    // Opens the given worldFile and creates Actors for the game by scanning each line.
    private static void createSetting(String worldFile) throws InvalidInputException {
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
                    // Create the Actor instance.
                    switch (actorType) {
                        case GATHERER: new Gatherer(x, y); break;
                        case THIEF: new Thief(x, y); break;
                        // If the Actor is a FruitStorage type
                        case TREE: case STOCKPILE: case HOARD:
                            new FruitStorage(actorType, x, y); break;
                        // If the Actor is any other type
                        default: new Actor(actorType, x, y);
                    }
                }
            }
        }
        // This error is thrown when the world file cannot be found.
        catch (FileNotFoundException e) { throw new InvalidInputException(worldFile); }
        // All other errors are thrown when the input given in a line is not well-defined.
        catch (Exception e) { throw new InvalidInputException(worldFile, lineNumber); }
    }
}
