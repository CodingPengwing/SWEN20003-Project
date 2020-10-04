package actor.mobile;

import actor.*;
import actor.fruitstorage.*;

public class Gatherer extends MobileActor {
    // Constructor with default Gatherer image
    public Gatherer(double x, double y, int direction, boolean initialActor)
    {
        super(x, y, "src/res/images/gatherer.png", direction);
        // If the Gatherer is initially defined at the start of the simulation
        if (initialActor) gatherers.add(this);
        // If the Gatherer is created on the fly during the simulation
        else newMobileActors.add(this);
    }

    // Tick logic for Gatherers
    protected void tick() {
        if (!isActive()) return;
        move();
        for (Actor actor : Actor.stationaryActors) {
            if (actor.locationEquals(this)) {
                if (actor instanceof Fence) { interactFence(); }
                if (actor instanceof MitosisPool) { interactPool(); return; }
                if (actor instanceof Sign) { interactSign(actor); }
                if (actor instanceof GoldenTree) { interactGoldenTree(); }
                if (actor instanceof Tree) { interactTree(actor); }
                if (actor instanceof Hoard) { interactHoard(actor); }
                if (actor instanceof Stockpile) { interactStockpile(actor); }
            }
        }
    }

    // Interacting with Fence
    private void interactFence() {
        setActive(false);
        direction.rotateReverse();
        move();
    }

    // Interacting with Mitosis Pool
    private void interactPool() {
        // Create a new Gatherer and move left
        Gatherer g1 = new Gatherer(location.getX(), location.getY(), direction.getDirection(), false);
        g1.direction.rotateRight();
        g1.move();
        // Move right with existing Gatherer
        direction.rotateLeft();
        move();
    }

    // Interacting with Sign
    private void interactSign(Actor actor) {
        direction.setDirection(((Sign) actor).getDirection());
    }

    // Interacting with Golden Tree
    private void interactGoldenTree() {
        if (!isCarrying()) {
            setCarrying(true);
            direction.rotateReverse();
        }
    }

    // Interacting with Tree
    private void interactTree(Actor actor) {
        if (!isCarrying()) {
            FruitStorage tree = (FruitStorage) actor;
            if (tree.getNumFruit() > 0) {
                setCarrying(true);
                direction.rotateReverse();
                tree.decreaseNumFruit();
            }
        }
    }

    // Interacting with Hoard
    private void interactHoard(Actor actor) {
        if (isCarrying()) {
            setCarrying(false);
            FruitStorage storage = (FruitStorage) actor;
            storage.increaseNumFruit();
        }
        direction.rotateReverse();
    }

    // Interacting with Stockpile
    private void interactStockpile(Actor actor) {
        interactHoard(actor);
    }
}
