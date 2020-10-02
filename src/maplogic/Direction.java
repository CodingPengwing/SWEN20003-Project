package maplogic;

public class Direction {
    public static final int UP = 0;
    public static final int RIGHT = 90;
    public static final int DOWN = 180;
    public static final int LEFT = 270;
    public static final int ANGLE_90 = 90;
    public static final int ANGLE_180 = 180;
    public static final int ANGLE_360 = 360;
    private int direction;

    public Direction(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        if (direction % ANGLE_90 != 0) {
            System.err.println("Cannot set direction to undefined value");
            System.exit(1);
        }
        direction %= ANGLE_360;
        if (direction < 0) direction += ANGLE_360;
        this.direction = direction;
    }

    public void rotateLeft() {
        setDirection(direction - ANGLE_90);
    }

    public void rotateRight() {
        setDirection(direction + ANGLE_90);
    }

    public void rotateReverse() {
        setDirection(direction + ANGLE_180);
    }
}