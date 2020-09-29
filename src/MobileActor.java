import java.util.ArrayList;

public abstract class MobileActor extends Actor {
    protected boolean active;
    protected boolean carrying;
    protected Direction direction;

    public MobileActor(double x, double y, String imagePath, int direction) {
        super(x, y, imagePath);
        active = true;
        carrying = false;
        this.direction = new Direction(direction);
    }

    // Moves the Actor one tile in the direction they are currently facing
    public void move() {
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

    public static void updateActors() {
        for (Gatherer gatherer : Gatherer.gatherers) gatherer.tick();
        for (Thief thief : Thief.thieves) thief.tick();
    }

    public static boolean actorsActive() {
        for (MobileActor actor : Gatherer.gatherers) {
            if (actor.active) return true;
        }
        for (MobileActor actor : Thief.thieves) {
            if (actor.active) return true;
        }
        return false;
    }

    abstract void tick();
}
