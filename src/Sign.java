import bagel.Image;
import org.lwjgl.system.CallbackI;

public class Sign extends Actor {
    private Direction direction;
    // Constructor with preset tree image
    public Sign(double x, double y, int direction) {
        super(x, y);
        this.direction = new Direction(direction);
        switch (direction) {
            case Direction.UP:
                image = new Image("res/images/up.png");
                break;
            case Direction.DOWN:
                image = new Image("res/images/down.png");
                break;
            case Direction.RIGHT:
                image = new Image("res/images/right.png");
                break;
            case Direction.LEFT:
                image = new Image("res/images/left.png");
                break;
        }
        stationaryActors.add(this);
    }

    public int getDirection() {
        return direction.getDirection();
    }
}