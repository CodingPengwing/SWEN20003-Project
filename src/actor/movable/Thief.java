package actor.movable;

import actor.*;

/** This class represents the Thief type of Actor in the game. It follows the template
 * set out by MovableActor. It overrides interaction logic from MovableActor to implement
 * some of its own interaction with stationary Actors as well as Gatherers.
 */
public class Thief extends MovableActor {
    private boolean consuming;

    /** Constructs a Thief type Actor at the location (x,y) on the map.
     * @param x This is the x position on the map, it is passed on to the MovableActor
     *          constructor.
     * @param y This is the y position on the map, it it passed on to the MovableActor
     *          constructor.
     */
    public Thief(double x, double y) {
        super(ActorType.THIEF, x, y);
        consuming = false;
    }

    @Override
    // Interaction with Mitosis Pool. This method returns a new Movable actor
    // created during the interaction.
    MovableActor interactPool() {
        // Reset consuming to "new" state
        consuming = false;
        return super.interactPool();
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
}
