package actor.movable;

import actor.*;
import java.util.ArrayList;

/** This class represents the Thief type of Actor in the game. It is managed by
 * MovableActor, meaning once a Thief is constructed, it will only be updated
 * through MovableActor. The class overrides interaction logic from MovableActor
 * to interact with stationary Actors, as well as Gatherers, during a tick.
 */
public class Thief extends MovableActor {
    private boolean consuming;
    // To store all the Thieves in the game
    private final static ArrayList<Thief> thieves = new ArrayList<>();

    /** Constructs a Thief type Actor at the location (x,y) on the map. All Thief's
     * created will be added to the default static 'thieves' array.
     * @param x This is the x position on the map, it is passed on to the MovableActor
     *          constructor.
     * @param y This is the y position on the map, it it passed on to the MovableActor
     *          constructor.
     */
    public Thief(double x, double y) {
        super(ActorType.THIEF, x, y);
        consuming = false;
        thieves.add(this);
    }

    @Override
    // Interaction with Mitosis Pool
    void interactPool() {
        super.interactPool();
        // Reset consuming to "new" state
        consuming = false;
    }

    @Override
    // Interaction with Pad
    void interactPad() {
        consuming = true;
    }

    @Override
    // Interaction with Gatherer
    void interactGatherer() {
        direction = direction.rotateLeft();
    }

    @Override
    // Interaction with Hoard
    void interactHoard(Actor actor) {
        FruitStorage hoard = (FruitStorage) actor;
        if (consuming) {
            consuming = false;
            if (!carrying) {
                if (hoard.getNumFruit() > 0) {
                    carrying = true;
                    hoard.decreaseNumFruit();
                }
                else direction = direction.rotateRight();
            }
        }
        else if (carrying) {
            carrying = false;
            hoard.increaseNumFruit();
            direction = direction.rotateRight();
        }
    }

    @Override
    // Interaction with Stockpile
    void interactStockpile(Actor actor) {
        FruitStorage stockpile = (FruitStorage) actor;
        if (!carrying) {
            if (stockpile.getNumFruit() > 0) {
                carrying = true;
                consuming = false;
                stockpile.decreaseNumFruit();
                direction = direction.rotateRight();
            }
        }
        else direction = direction.rotateRight();
    }

    // Triggers tick() for all Thief's
    static void tickThieves() {
        int currentNumThieves = thieves.size();
        for (int i = 0; i < currentNumThieves; i++) { thieves.get(i).tick(); }
    }

    // Triggers render() for all Thief's
    static void renderThieves() {
        for (Thief thief : thieves) { thief.render(); }
    }

    // Checks whether any Thief's are still active
    static boolean thievesActive() {
        for (MovableActor thief : thieves) { if (thief.active) return true; }
        return false;
    }
}
