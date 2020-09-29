import java.util.ArrayList;

public abstract class MobileActor extends Actor {
    protected boolean active;
    protected boolean carrying;
    protected Direction direction;

    public static ArrayList<MobileActor> mobileActors = new ArrayList<>();

    public MobileActor(double x, double y, String imagePath, int direction) {
        super(x, y, imagePath);
        active = true;
        carrying = false;
        this.direction = new Direction(direction);
    }

    // Moves the Actor one tile in the direction they are currently heading towards
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
        for (MobileActor actor : mobileActors) {
            if (actor instanceof Gatherer) {
                Gatherer gatherer = (Gatherer) actor;
                gatherer.tick();
            }
            else if (actor instanceof Thief) {
                Thief thief = (Thief) actor;
                thief.tick();
            }
        }
    }

    public static boolean actorsActive() {
        for (MobileActor actor : mobileActors) {
            if (actor.active) return true;
        }
        return false;
    }

    abstract void tick();
}
