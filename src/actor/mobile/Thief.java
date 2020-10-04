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

    // Tick logic for Thieves
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

    // Interacting with Fence
    private void interactFence() {
        setActive(false);
        direction.rotateReverse();
        move();
    }

    // Interacting with Mitosis Pool
    private void interactPool() {
        // Create a new Thief and move left
        Thief thief = new Thief(location.getX(), location.getY(), direction.getDirection(), false);
        thief.direction.rotateLeft();
        thief.move();
        // Move right with existing Thief
        direction.rotateRight();
        move();
    }

    // Interacting with Sign
    private void interactSign(Actor actor) {
        direction.setDirection(((Sign) actor).getDirection());
    }

    // Interacting with Pad
    private void interactPad() {
        consuming = true;
    }

    // Interacting with Gatherer
    private void interactGatherer() {
        direction.rotateLeft();
    }

    // Interacting with Golden Tree
    private void interactGoldenTree() {
        if (!isCarrying()) {
            setCarrying(true);
        }
    }

    // Interacting with Tree
    private void interactTree(Actor actor) {
        if (!isCarrying()) {
            FruitStorage tree = (FruitStorage) actor;
            if (tree.getNumFruit() > 0) {
                setCarrying(true);
                tree.decreaseNumFruit();
            }
        }
    }

    // Interacting with Hoard
    private void interactHoard(Actor actor) {
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

    // Interacting with Stockpile
    private void interactStockpile(Actor actor) {
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

