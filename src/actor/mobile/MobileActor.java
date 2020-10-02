package actor.mobile;

import actor.Actor;
import maplogic.*;
import java.util.ArrayList;

public abstract class MobileActor extends Actor {
    protected boolean active;
    protected boolean carrying;
    protected Direction direction;

    // To store the current Gatherers and Thieves in the game
    final protected static ArrayList<MobileActor> gatherers = new ArrayList<>();
    final protected static ArrayList<MobileActor> thieves = new ArrayList<>();

    public MobileActor(double x, double y, String imagePath, int direction) {
        super(x, y, imagePath);
        active = true;
        carrying = false;
        this.direction = new Direction(direction);
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

    // Ticks every Actor in gatherers and thieves arrays
    public static void tickMobileActors() {
        for (MobileActor actor : gatherers) actor.tick();
        for (MobileActor actor : thieves) actor.tick();
    }

    // Renders every Actor in gatherers and thieves arrays
    public static void renderMobileActors() {
        for (MobileActor actor : gatherers) actor.render();
        for (MobileActor actor : thieves) actor.render();
    }

    public static boolean actorsActive() {
        for (MobileActor actor : gatherers) {
            if (actor.active) return true;
        }
        for (MobileActor actor : thieves) {
            if (actor.active) return true;
        }
        return false;
    }

    // Every child must implement their own tick method
    abstract void tick();
}
