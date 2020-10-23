package actor.movable;

import actor.Actor;
import actor.ActorType;
import actor.FruitStorage;
import maplogic.*;

import java.util.ArrayList;

/** This is an implementation of the template method pattern; MovableActor specifies
 * the template, all inheriting classes must follow the template. This class extends
 * to all Actors that move around the map (currently Gatherers and Thief's). Hence,
 * all movement logic is defined in this class and available inside this package only.
 */
public abstract class MovableActor extends Actor {
    boolean active;
    boolean carrying;
    Direction direction;

    /** Constructs an instance of MovableActor through the Gatherer or Thief
     * constructors. Upon creation, the direction is set to UP by default.
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
        direction = Direction.UP;
    }

    /** Returns the state of the MovableActor (active or inactive)
     * @return true if active, false otherwise
     */
    public boolean isActive() { return active; }

    // Moves the Actor one tile in the direction they are currently facing
    final void move() {
        switch (direction) {
            case UP: location.moveUp(); break;
            case RIGHT: location.moveRight(); break;
            case DOWN: location.moveDown(); break;
            case LEFT: location.moveLeft(); break;
        }
    }

    /** Tick logic for all MovableActors. If the Actor is active, it moves one tile
     * in the direction it is facing, then carries out all the interaction logic for
     * the tick. This method may return a new Movable actor if a Mitosis Pool was
     * interacted with during the tick, the type of Actor returned is the same as the
     * type of Actor that performs the tick.
     * @param stationaryActors Array of stationary Actors to interact with
     * @param gatherers Array of Gatherers to interact with
     * @return a MovableActor with same type as this Actor if interacted with Mitosis
     * Pool, null if no Pool interaction.
     */
    public final MovableActor tick(ArrayList<Actor> stationaryActors, ArrayList<Gatherer> gatherers) {
        if (!active) return null;
        move();
        MovableActor newActor = null;

        // Check for Fences, Pools, Signs and Pads in the same tile.
        for (Actor actor : stationaryActors) {
            if (this.locationEquals(actor)) {
                switch (actor.getType()) {
                    case FENCE: interactFence(); break;
                    case POOL:
                        newActor = interactPool();
                        break;
                    case SIGNUP: interactSignUp(); break;
                    case SIGNDOWN: interactSignDown(); break;
                    case SIGNLEFT: interactSignLeft(); break;
                    case SIGNRIGHT: interactSignRight(); break;
                    case PAD: interactPad(); break;
                }
            }
        }
        // Check for Gatherers in the same tile.
        for (Gatherer gatherer : gatherers) {
            if (this.locationEquals(gatherer)) { interactGatherer(); break; }
        }
        // Check for Trees, Hoards and Stockpiles in the same tile.
        for (Actor actor : stationaryActors) {
            if (this.locationEquals(actor)) {
                switch (actor.getType()) {
                    case TREE: interactTree(actor); break;
                    case GOLDENTREE: interactGoldenTree(); break;
                    case HOARD: interactHoard(actor); break;
                    case STOCKPILE: interactStockpile(actor); break;
                }
            }
        }
        return newActor;
    }

    // Interaction with Hoard
    abstract void interactHoard(Actor actor);

    // Interaction with Stockpile
    abstract void interactStockpile(Actor actor);

    // Interaction with Sign
    final void interactSignUp() { direction = Direction.UP; }
    final void interactSignDown() { direction = Direction.DOWN; }
    final void interactSignLeft() { direction = Direction.LEFT; }
    final void interactSignRight() { direction = Direction.RIGHT; }

    // Interaction with Pad
    void interactPad() {}

    // Interaction with Gatherer
    void interactGatherer() {}

    // Interaction with Fence
    void interactFence() {
        active = false;
        direction = direction.rotateReverse();
        move();
    }

    // Interaction with Mitosis Pool. This method returns a new Movable actor
    // created during the interaction.
    MovableActor interactPool() {
        // Create a new MovableActor
        MovableActor newActor;
        switch (getType()) {
            case THIEF: newActor = new Thief(getX(), getY()); break;
            default: newActor = new Gatherer(getX(), getY());
        }
        // Move left with the new MovableActor
        newActor.direction = direction.rotateLeft();
        newActor.move();
        // Move right with existing MovableActor
        direction = direction.rotateRight();
        move();
        carrying = false;
        return newActor;
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
