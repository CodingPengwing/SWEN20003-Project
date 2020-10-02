package actor;

import maplogic.Direction;

public class Sign extends Actor {
    private Direction direction;
    // Constructor with default Sign images
    public Sign(double x, double y, int direction) {
        super(x, y);
        this.direction = new Direction(direction);

        // Determine which image is needed from the direction given
        switch (direction) {
            case Direction.UP:
                setImage("src/res/images/up.png");
                break;
            case Direction.DOWN:
                setImage("src/res/images/down.png");
                break;
            case Direction.RIGHT:
                setImage("src/res/images/right.png");
                break;
            case Direction.LEFT:
                setImage("src/res/images/left.png");
                break;
        }
        stationaryActors.add(this);
    }

    public int getDirection() {
        return direction.getDirection();
    }
}