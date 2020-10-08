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

    @Override
    // Tick logic for Gatherer's
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

    @Override
    // Interacting with Golden Tree
    protected boolean interactGoldenTree() {
        if (super.interactGoldenTree()) {
            direction.rotateReverse();
            return true;
        }
        return false;
    }

    @Override
    // Interacting with Tree
    protected boolean interactTree(Actor actor) {
        if (super.interactTree(actor)) {
            direction.rotateReverse();
            return true;
        }
        return false;
    }

    @Override
    protected void interactPad() {}

    @Override
    // Interacting with Hoard
    protected void interactHoard(Actor actor) {
        if (isCarrying()) {
            setCarrying(false);
            FruitStorage storage = (FruitStorage) actor;
            storage.increaseNumFruit();
        }
        direction.rotateReverse();
    }

    @Override
    // Interacting with Stockpile
    protected void interactStockpile(Actor actor) {
        interactHoard(actor);
    }
}
