package actor.mobile;

import actor.Actor;
import actor.ActorType;
import actor.FruitStorage;
import maplogic.*;

public abstract class MobileActor extends Actor {
    private final int DEFAULT_DIRECTION = Direction.UP;
    private boolean active;
    private boolean carrying;
    private Direction direction;

    public MobileActor(ActorType type, double x, double y) {
        super(type, x, y);
        active = true;
        carrying = false;
        direction = new Direction(DEFAULT_DIRECTION);
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

    final protected Direction getDirection() {
        return direction;
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
    abstract protected void tick();

    // Interacting with Pad
    abstract protected void interactPad();

    // Interacting with Hoard
    abstract protected void interactHoard(Actor actor);

    // Interacting with Stockpile
    abstract protected void interactStockpile(Actor actor);

    protected void interactGatherer() {}

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
        switch (this.getType()) {
            case GATHERER:
                newActor = new Gatherer(location.getX(), location.getY(), false);
                break;
            default:
                newActor = new Thief(location.getX(), location.getY(), false);
        }

        newActor.direction.setDirection(direction.getDirection());
        newActor.direction.rotateLeft();
        newActor.move();
        // Move right with existing MobileActor
        direction.rotateRight();
        move();
        setCarrying(false);
    }


    // Interacting with Sign
    protected void interactSignUp() {
        direction.setDirection(Direction.UP);
    }

    protected void interactSignDown() {
        direction.setDirection(Direction.DOWN);
    }

    protected void interactSignLeft() {
        direction.setDirection(Direction.LEFT);
    }

    protected void interactSignRight() {
        direction.setDirection(Direction.RIGHT);
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

    // Ticks every Actor in 'gatherers' and 'thieves' arrays
    public static void tickMobileActors() {
        Gatherer.tickGatherers();
        Thief.tickThieves();
    }

    // Renders every Actor in 'gatherers' and 'thieves' arrays
    public static void renderMobileActors() {
        for (Gatherer actor : Gatherer.gatherers) actor.render();
        for (Thief actor : Thief.thieves) actor.render();
    }

    // Checks if there are still active Actors
    public static boolean actorsActive() {
        for (MobileActor actor : Gatherer.gatherers) {
            if (actor.active) return true;
        }
        for (MobileActor actor : Thief.thieves) {
            if (actor.active) return true;
        }
        return false;
    }
}
