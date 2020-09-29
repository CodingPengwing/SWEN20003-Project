public class Direction {
    public static final int UP = 0;
    public static final int RIGHT = 90;
    public static final int DOWN = 180;
    public static final int LEFT = 270;
    private int direction;

    public Direction(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        if (direction % 90 != 0) {
            System.err.println("Cannot set direction to undefined value");
            System.exit(1);
        }
        direction %= 360;
        if (direction < 0) direction += 360;
        this.direction = direction;
    }

}
