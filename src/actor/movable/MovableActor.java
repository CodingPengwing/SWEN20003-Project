package actor.movable;

import actor.Actor;
import actor.ActorType;
import actor.FruitStorage;
import maplogic.*;
import java.util.ArrayList;

/** This is an implementation of the template method pattern; MovableActor sets
 * the template, Gatherer and Thief follow the template. This class manages all
 * Actors that move around the map (currently Gatherers and Thief's). Hence, all
 * movement logic is defined in this class and available inside this package only.
 */
public abstract class MovableActor extends Actor {
    private final static int DEFAULT_DIRECTION = Direction.UP;
    boolean active;
    boolean carrying;
    Direction direction;

    /** Constructs an instance of MovableActor through the Gatherer or Thief
     * constructors. Upon call, the direction is set to UP by default.
     * @param type This is the ActorType instance, it is passed on to the Actor
     *            constructor to store.
     * @param x This is the x position on the map, it is passed on to the Actor
     *          constructor to initialise Location.
     * @param y This is the y position on the map, it is passed on to the Actor
     *          constructor to initialise Location.
     */
    public MovableActor(ActorType type, double x, double y) {
        super(type, x, y);
        active = true;
        carrying = false;
        direction = new Direction(DEFAULT_DIRECTION);
    }

    /** This method triggers the tick() for all MovableActors. As these Actors
     * move around and interact with other Actors, the game state is updated.
     */
    public static void tickMovableActors() {
        Gatherer.tickGatherers();
        Thief.tickThieves();
    }

    /** This method renders all MovableActors onto the screen.
     */
    public static void renderMovableActors() {
        Gatherer.renderGatherers();
        Thief.renderThieves();
    }

    /** This method checks all MovableActors to see whether there are still
     * any active Actors in the game.
     * @return true if there is at least one active Actor. false if none.
     */
    public static boolean actorsActive() {
        if (Gatherer.gatherersActive() || Thief.thievesActive()) return true;
        return false;
    }

    // Moves the Actor one tile in the direction they are currently facing
    final void move() {
        switch (direction.getDirection()) {
            case Direction.UP:
                location.moveUp(); break;
            case Direction.RIGHT:
                location.moveRight(); break;
            case Direction.DOWN:
                location.moveDown(); break;
            case Direction.LEFT:
                location.moveLeft(); break;
        }
    }

    // Tick logic for all MovableActors.
    final void tick() {
        if (!active) return;
        move();
        // Find all stationary Actors that the current MovableActor is standing on.
        ArrayList<Actor> actorsStandingOn = getStationaryActorsAtLocation(getX(), getY());

        // Check for Fences, Pools, Signs and Pads in the same tile.
        for (Actor actor : actorsStandingOn) {
            switch (actor.getType()) {
                case FENCE:
                    interactFence(); break;
                case POOL:
                    interactPool(); break;
                case SIGNUP:
                    interactSignUp(); break;
                case SIGNDOWN:
                    interactSignDown(); break;
                case SIGNLEFT:
                    interactSignLeft(); break;
                case SIGNRIGHT:
                    interactSignRight(); break;
                case PAD:
                    interactPad(); break;
            }
        }
        // Check for Gatherers in the same tile.
        for (MovableActor gatherer : Gatherer.gatherers) {
            if (gatherer.locationEquals(this)) {
                interactGatherer(); break;
            }
        }
        // Check for Trees, Hoards and Stockpiles in the same tile.
        for (Actor actor : actorsStandingOn) {
            switch (actor.getType()) {
                case TREE:
                    interactTree(actor); break;
                case GOLDENTREE:
                    interactGoldenTree(); break;
                case HOARD:
                    interactHoard(actor); break;
                case STOCKPILE:
                    interactStockpile(actor); break;
            }
        }
    }

    // Interaction with Hoard
    abstract void interactHoard(Actor actor);

    // Interaction with Stockpile
    abstract void interactStockpile(Actor actor);

    // Interaction with Sign
    final void interactSignUp() { direction.setDirection(Direction.UP); }

    final void interactSignDown() { direction.setDirection(Direction.DOWN); }

    final void interactSignLeft() { direction.setDirection(Direction.LEFT); }

    final void interactSignRight() { direction.setDirection(Direction.RIGHT); }

    // Interaction with Pad
    void interactPad() {}

    // Interaction with Gatherer
    void interactGatherer() {}

    // Interaction with Fence
    void interactFence() {
        active = false;
        direction.rotateReverse();
        move();
    }

    // Interaction with Mitosis Pool
    void interactPool() {
        // Create a new MovableActor
        MovableActor newActor;
        switch (getType()) {
            case GATHERER:
                newActor = new Gatherer(getX(), getY()); break;
            default:
                newActor = new Thief(getX(), getY());
        }
        // Move left with the new MovableActor
        newActor.direction.setDirection(direction.getDirection());
        newActor.direction.rotateLeft();
        newActor.move();
        // Move right with existing MovableActor
        direction.rotateRight();
        move();
        carrying = false;
    }

    // Interaction with GoldenTree
    boolean interactGoldenTree() {
        if (!carrying) {
            carrying = true;
            return true;
        }
        return false;
    }

    // Interaction with Tree
    boolean interactTree(Actor actor) {
        if (!carrying) {
            FruitStorage tree = (FruitStorage) actor;
            if (tree.getNumFruit() > 0) {
                carrying = true;
                tree.decreaseNumFruit();
                return true;
            }
        }
        return false;
    }
}
