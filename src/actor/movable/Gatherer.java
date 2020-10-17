package actor.movable;

import actor.*;
import maplogic.Direction;
import java.util.ArrayList;

/** This class represents the Gatherer type of Actor in the game. It is managed by
 * MovableActor, meaning once a Gatherer is constructed, it will only be updated
 * through MovableActor. The class overrides interaction logic from MovableActor
 * to interact with stationary Actors during a tick.
 */
public class Gatherer extends MovableActor {
    // To store all the Gatherers in the game
    final static ArrayList<Gatherer> gatherers = new ArrayList<>();

    /** Constructs a Gatherer type Actor at the location (x,y) on the map. Upon
     * creation the default direction is set to LEFT for a Gatherer. All Gatherers
     * created will be added to the default static 'gatherers' array.
     * @param x This is the x position on the map, it is passed on to the MovableActor
     *          constructor.
     * @param y This is the y position on the map, it it passed on to the MovableActor
     *          constructor.
     */
    public Gatherer(double x, double y) {
        super(ActorType.GATHERER, x, y);
        direction.setDirection(Direction.LEFT);
        gatherers.add(this);
    }

    @Override
    // Interaction with Golden Tree
    boolean interactGoldenTree() {
        if (super.interactGoldenTree()) {
            direction.rotateReverse();
            return true;
        }
        return false;
    }

    @Override
    // Interaction with Tree
    boolean interactTree(Actor actor) {
        if (super.interactTree(actor)) {
            direction.rotateReverse();
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
        direction.rotateReverse();
    }

    @Override
    // Interaction with Stockpile
    void interactStockpile(Actor actor) {
        interactHoard(actor);
    }

    // Triggers tick() for all Gatherers
    static void tickGatherers() {
        int currentNumGatherers = gatherers.size();
        for (int i = 0; i < currentNumGatherers; i++) gatherers.get(i).tick();
    }

    // Triggers render() for all Gatherers
    static void renderGatherers() {
        for (Gatherer gatherer : gatherers) gatherer.render();
    }

    // Checks whether any Gatherers are still active
    static boolean gatherersActive() {
        for (MovableActor gatherer : gatherers) {
            if (gatherer.active) return true;
        }
        return false;
    }
}
