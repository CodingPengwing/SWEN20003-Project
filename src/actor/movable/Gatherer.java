package actor.movable;

import actor.*;
import maplogic.Direction;

/** This class represents the Gatherer type of Actor in the game. It follows the template
 * set out by MovableActor. It overrides interaction logic from MovableActor to implement
 * some of its own interaction with stationary Actors.
 */
public class Gatherer extends MovableActor {

    /** Constructs a Gatherer type Actor at the location (x,y) on the map. Upon
     * creation the default direction is set to LEFT for a Gatherer.
     * @param x This is the x position on the map, it is passed on to the MovableActor
     *          constructor.
     * @param y This is the y position on the map, it it passed on to the MovableActor
     *          constructor.
     */
    public Gatherer(double x, double y) {
        super(ActorType.GATHERER, x, y);
        // Default direction for Gatherer is left
        direction = Direction.LEFT;
    }

    @Override
    // Interaction with Golden Tree
    boolean interactGoldenTree() {
        if (super.interactGoldenTree()) {
            direction = direction.rotateReverse();
            return true;
        }
        return false;
    }

    @Override
    // Interaction with Tree
    boolean interactTree(Actor actor) {
        if (super.interactTree(actor)) {
            direction = direction.rotateReverse();
            return true;
        }
        return false;
    }

    @Override
    // Interaction with Hoard
    void interactHoard(Actor actor) {
        if (carrying) {
            carrying = false;
            FruitStorage storage = (FruitStorage) actor;
            storage.increaseNumFruit();
        }
        direction = direction.rotateReverse();
    }

    @Override
    // Interaction with Stockpile
    void interactStockpile(Actor actor) { interactHoard(actor); }
}
