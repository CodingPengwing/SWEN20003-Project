package actor.mobile;

import actor.*;
import actor.fruitstorage.*;

public class Thief extends MobileActor {
    private boolean consuming;

    // Constructor with default Thief image
    public Thief(double x, double y, int direction, boolean initialActor) {
        super(x, y, "src/res/images/thief.png", direction);
        consuming = false;
        // If the Thief is initially defined at the start of the simulation
        if (initialActor) thieves.add(this);
        // If the Thief is created on the fly during the simulation
        else newMobileActors.add(this);
    }

    @Override
    // Tick logic for Thief's
    protected void tick() {
        if (!isActive()) return;
        move();
        for (Actor actor : stationaryActors) {
            if (actor.locationEquals(this)) {
                if (actor instanceof Fence) { interactFence(); }
                if (actor instanceof MitosisPool) { interactPool(); return; }
                if (actor instanceof Sign) { interactSign(actor); }
                if (actor instanceof Pad) { interactPad(); }
            }
        }
        for (MobileActor gatherer : MobileActor.gatherers) {
            if (gatherer.locationEquals(this)) { interactGatherer(); break; }
        }
        for (Actor actor : stationaryActors) {
            if (actor.locationEquals(this)) {
                if (actor instanceof GoldenTree) { interactGoldenTree(); }
                if (actor instanceof Tree) { interactTree(actor); }
                if (actor instanceof Hoard) { interactHoard(actor); }
                if (actor instanceof Stockpile) { interactStockpile(actor); }
            }
        }
    }

    @Override
    // Interacting with Mitosis Pool
    protected void interactPool() {
        super.interactPool();
        consuming = false;
    }

    @Override
    // Interacting with Pad
    protected void interactPad() {
        consuming = true;
    }

    // Interacting with Gatherer
    private void interactGatherer() {
        direction.rotateLeft();
    }

    @Override
    // Interacting with Hoard
    protected void interactHoard(Actor actor) {
        Hoard hoard = (Hoard) actor;
        if (consuming) {
            consuming = false;
            if (!isCarrying()) {
                if (hoard.getNumFruit() > 0) {
                    setCarrying(true);
                    hoard.decreaseNumFruit();
                }
                else {
                    direction.rotateRight();
                }
            }
        }
        else if (isCarrying()) {
            setCarrying(false);
            hoard.increaseNumFruit();
            direction.rotateRight();
        }
    }

    @Override
    // Interacting with Stockpile
    protected void interactStockpile(Actor actor) {
        Stockpile stockpile = (Stockpile) actor;
        if (!isCarrying()) {
            if (stockpile.getNumFruit() > 0) {
                setCarrying(true);
                consuming = false;
                stockpile.decreaseNumFruit();
                direction.rotateRight();
            }
        }
        else direction.rotateRight();
    }
}

