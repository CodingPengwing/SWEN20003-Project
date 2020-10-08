package actor.mobile;

import actor.Actor;
import actor.Sign;
import actor.fruitstorage.FruitStorage;
import maplogic.*;
import java.util.ArrayList;

public abstract class MobileActor extends Actor {
    private boolean active;
    private boolean carrying;
    protected Direction direction;

    // To store all the Gatherer's and Thief's in the game
    final protected static ArrayList<MobileActor> gatherers = new ArrayList<>();
    final protected static ArrayList<MobileActor> thieves = new ArrayList<>();

    // To store new MobileActor's created during game execution
    final protected static ArrayList<MobileActor> newMobileActors = new ArrayList<>();

    public MobileActor(double x, double y, String imagePath, int direction) {
        super(x, y, imagePath);
        active = true;
        carrying = false;
        this.direction = new Direction(direction);
    }

    final protected boolean isActive() {
        return active;
    }

    final protected void setActive(boolean active) {
        this.active = active;
    }

    final protected boolean isCarrying() {
        return carrying;
    }

    final protected void setCarrying(boolean carrying) {
        this.carrying = carrying;
    }

    // Moves the Actor one tile in the direction they are currently facing
    final protected void move() {
        switch (direction.getDirection()) {
            case Direction.UP:
                location.moveUp();
                break;
            case Direction.RIGHT:
                location.moveRight();
                break;
            case Direction.DOWN:
                location.moveDown();
                break;
            case Direction.LEFT:
                location.moveLeft();
                break;
        }
    }

    // Every child must implement their own tick method
    protected abstract void tick();

    // Interacting with Fence
    protected void interactFence() {
        setActive(false);
        direction.rotateReverse();
        move();
    }

    // Interacting with Mitosis Pool
    protected void interactPool() {
        // Create a new MobileActor and move left
        MobileActor newActor;
        if (this instanceof Gatherer) {
            newActor = new Gatherer(location.getX(), location.getY(), direction.getDirection(), false);
        } else {
            newActor = new Thief(location.getX(), location.getY(), direction.getDirection(), false);
        }
        newActor.direction.rotateLeft();
        newActor.move();
        // Move right with existing MobileActor
        direction.rotateRight();
        move();
        setCarrying(false);
    }


    // Interacting with Sign
    protected void interactSign(Actor actor) {
        direction.setDirection(((Sign) actor).getDirection());
    }

    // Interacting with Golden Tree
    protected boolean interactGoldenTree() {
        if (!isCarrying()) {
            setCarrying(true);
            return true;
        }
        return false;
    }

    // Interacting with Tree
    protected boolean interactTree(Actor actor) {
        if (!isCarrying()) {
            FruitStorage tree = (FruitStorage) actor;
            if (tree.getNumFruit() > 0) {
                setCarrying(true);
                tree.decreaseNumFruit();
                return true;
            }
        }
        return false;
    }

    // Interacting with Pad
    protected abstract void interactPad();

    // Interacting with Hoard
    protected abstract void interactHoard(Actor actor);

    // Interacting with Stockpile
    protected abstract void interactStockpile(Actor actor);

    // Ticks every Actor in 'gatherers' and 'thieves' arrays
    public static void tickMobileActors() {
        for (MobileActor actor : gatherers) actor.tick();
        for (MobileActor actor : thieves) actor.tick();

        // If new Actors have been created during the tick,
        // add those Actors into their respective arrays
        // and clear out the newMobileActors array
        if (newMobileActors.size() > 0) {
            for (MobileActor actor : newMobileActors) {
                if (actor instanceof Gatherer) {
                    gatherers.add(actor);
                }
                if (actor instanceof Thief) {
                    thieves.add(actor);
                }
            }
            newMobileActors.clear();
        }
    }

    // Renders every Actor in 'gatherers' and 'thieves' arrays
    public static void renderMobileActors() {
        for (MobileActor actor : gatherers) actor.render();
        for (MobileActor actor : thieves) actor.render();
    }

    // Checks if there are still active Actors
    public static boolean actorsActive() {
        for (MobileActor actor : gatherers) {
            if (actor.active) return true;
        }
        for (MobileActor actor : thieves) {
            if (actor.active) return true;
        }
        return false;
    }
}
