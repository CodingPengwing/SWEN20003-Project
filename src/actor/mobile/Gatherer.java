package actor.mobile;

import actor.*;
import actor.fruitstorage.*;

public class Gatherer extends MobileActor {
    // Constructor with default Gatherer image
    public Gatherer(double x, double y, int direction)
    {
        super(x, y, "src/res/images/gatherer.png", direction);
        MobileActor.gatherers.add(this);
    }

    // Tick logic for Gatherers
    protected void tick() {
        if (!active) return;
        move();
        for (Actor actor : Actor.stationaryActors) {
            if (actor.locationMatch(this)) {
                if (actor instanceof Fence) { interactFence(); }
                if (actor instanceof MitosisPool) { interactPool(); }
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
        active = false;
        direction.rotateReverse();
        move();
    }

    // Interacting with Mitosis Pool
    private void interactPool() {
        Gatherer g1 = new Gatherer(location.getX(), location.getY(), direction.getDirection());
        Gatherer g2 = new Gatherer(location.getX(), location.getY(), direction.getDirection());
        g1.direction.rotateRight();
        g2.direction.rotateLeft();
        g1.move();
        g2.move();
        MobileActor.gatherers.remove(this);
    }

    // Interacting with Sign
    private void interactSign(Actor actor) {
        direction.setDirection(((Sign) actor).getDirection());
    }

    // Interacting with Golden Tree
    private void interactGoldenTree() {
        if (!carrying) {
            carrying = true;
            direction.rotateReverse();
        }
    }

    // Interacting with Tree
    private void interactTree(Actor actor) {
        if (!carrying) {
            FruitStorage tree = (FruitStorage) actor;
            if (tree.getNumFruit() > 0) {
                carrying = true;
                direction.rotateReverse();
                tree.decreaseNumFruit();
            }
        }
    }

    // Interacting with Hoard
    private void interactHoard(Actor actor) {
        if (carrying) {
            carrying = false;
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
