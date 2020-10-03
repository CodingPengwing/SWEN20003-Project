package actor.mobile;

import actor.Actor;
import maplogic.*;
import java.util.ArrayList;

public abstract class MobileActor extends Actor {
    private boolean active;
    private boolean carrying;
    protected Direction direction;

    // To store all the Gatherers and Thieves in the game
    final protected static ArrayList<MobileActor> gatherers = new ArrayList<>();
    final protected static ArrayList<MobileActor> thieves = new ArrayList<>();

    // To store new MobileActors created during game execution
    final protected static ArrayList<MobileActor> newMobileActors = new ArrayList<>();

    public MobileActor(double x, double y, String imagePath, int direction) {
        super(x, y, imagePath);
        active = true;
        carrying = false;
        this.direction = new Direction(direction);
    }

    // Every child must implement their own tick method
    abstract void tick();

    protected boolean isActive() {
        return active;
    }

    protected void setActive(boolean active) {
        this.active = active;
    }

    protected boolean isCarrying() {
        return carrying;
    }

    protected void setCarrying(boolean carrying) {
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
