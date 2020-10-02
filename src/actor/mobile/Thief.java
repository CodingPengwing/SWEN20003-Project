package actor.mobile;

import actor.*;
import actor.fruitstorage.*;

public class Thief extends MobileActor {
    private boolean consuming;

    // Constructor with default gatherer image
    public Thief(double x, double y, int direction)
    {
        super(x, y, "src/res/images/thief.png", direction);
        consuming = false;
        thieves.add(this);
    }

    protected void tick() {
        if (!active) return;
        move();
        for (Actor actor : stationaryActors) {
            if (actor.locationMatch(this)) {
                if (actor instanceof Fence) { interactFence(); }
                if (actor instanceof MitosisPool) { interactPool(); }
                if (actor instanceof Sign) { interactSign(actor); }
                if (actor instanceof Pad) { interactPad(); }
            }
        }
        for (MobileActor gatherer : MobileActor.gatherers) {
            if (gatherer.locationMatch(this)) { interactGatherer(); break; }
        }
        for (Actor actor : stationaryActors) {
            if (actor.locationMatch(this)) {
                if (actor instanceof GoldenTree) { interactGoldenTree(); }
                if (actor instanceof Tree) { interactTree(actor); }
                if (actor instanceof Hoard) { interactHoard(actor); }
                if (actor instanceof Stockpile) { interactStockpile(actor); }
            }
        }
    }

    private void interactFence() {
        active = false;
        direction.rotateReverse();
        move();
    }

    private void interactPool() {
        Thief t1 = new Thief(location.getX(), location.getY(), direction.getDirection());
        Thief t2 = new Thief(location.getX(), location.getY(), direction.getDirection());
        t1.direction.rotateLeft();
        t2.direction.rotateRight();
        t1.move();
        t2.move();
        thieves.remove(this);
    }

    private void interactSign(Actor actor) {
        direction.setDirection(((Sign) actor).getDirection());
    }

    private void interactPad() {
        consuming = true;
    }

    private void interactGatherer() {
        direction.rotateLeft();
    }

    private void interactGoldenTree() {
        if (!carrying) {
            carrying = true;
        }
    }

    private void interactTree(Actor actor) {
        if (!carrying) {
            FruitStorage tree = (FruitStorage) actor;
            if (tree.getNumFruit() > 0) {
                carrying = true;
                tree.decreaseNumFruit();
            }
        }
    }

    private void interactHoard(Actor actor) {
        Hoard hoard = (Hoard) actor;
        if (consuming) {
            consuming = false;
            if (!carrying) {
                if (hoard.getNumFruit() > 0) {
                    carrying = true;
                    hoard.decreaseNumFruit();
                }
                else {
                    direction.rotateRight();
                }
            }
        }
        else if (carrying) {
            carrying = false;
            hoard.increaseNumFruit();
            direction.rotateRight();
        }
    }

    private void interactStockpile(Actor actor) {
        Stockpile stockpile = (Stockpile) actor;
        if (!carrying) {
            if (stockpile.getNumFruit() > 0) {
                carrying = true;
                consuming = false;
                stockpile.decreaseNumFruit();
                direction.rotateRight();
            }
        }
        else direction.rotateRight();
    }
}

