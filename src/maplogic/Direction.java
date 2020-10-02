package maplogic;

public class Direction {
    // Directions represented in degrees relative to direction going up the screen
    public static final int UP = 0;
    public static final int RIGHT = 90;
    public static final int DOWN = 180;
    public static final int LEFT = 270;

    // Angles for calculations
    private static final int ANGLE_90 = 90;
    private static final int ANGLE_180 = 180;
    private static final int ANGLE_360 = 360;

    private int direction;

    public Direction(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        if (direction % ANGLE_90 != 0) {
            System.err.println("Cannot set direction to value: " + direction);
            System.exit(-1);
        }
        // Keep the direction within the 0-270 range
        direction %= ANGLE_360;
        if (direction < 0) direction += ANGLE_360;
        this.direction = direction;
    }

    // Turns the direction 90 degrees anticlockwise
    public void rotateLeft() {
        setDirection(direction - ANGLE_90);
    }

    // Turns the direction 90 degrees clockwise
    public void rotateRight() {
        setDirection(direction + ANGLE_90);
    }

    // Turns the direction 180 degrees
    public void rotateReverse() {
        setDirection(direction + ANGLE_180);
    }
}