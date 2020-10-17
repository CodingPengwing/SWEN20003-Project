package maplogic;

/** This class defines the direction logic to help with the movement logic in
 * the game. The only four directions currently defined are UP, DOWN, LEFT, RIGHT.
 * The directions are based on the 360-degree logic that are common in cardinal
 * directions. Only directions that are multiples of 90 degrees will be accepted.
 */
public class Direction {
    /** The preset UP direction (can be thought of as North). */
    public final static int UP = 0;
    /** The preset RIGHT direction (can be thought of as East). */
    public final static int RIGHT = 90;
    /** The preset DOWN direction (can be thought of as SOUTH). */
    public final static int DOWN = 180;
    /** The preset LEFT direction (can be thought of as WEST). */
    public final static int LEFT = 270;

    // Angles used in calculations to determine a new direction.
    private final static int ANGLE_90 = 90;
    private final static int ANGLE_180 = 180;
    private final static int ANGLE_360 = 360;

    private int degree;

    /** Constructs a new Direction instance based on the given integer degree.
     * @param degree This is the degree
     */
    public Direction(int degree) {
        if (isValid(degree))
            this.degree = degree;
        else System.err.println("Cannot set direction's degree to value: " + degree);
    }

    /** Returns the degree of the Direction.
     * @return This Direction's degree
     */
    public int getDirection() {
        return degree;
    }

    /** Sets the degree of the Direction to a new value, only accepts multiples of 90.
     * @param degree This is the new integer value for degree.
     */
    public void setDirection(int degree) {
        if (isValid(degree)) {
            // Keep the direction within the 0-270 range
            degree %= ANGLE_360;
            if (degree < 0) degree += ANGLE_360;
            this.degree = degree;
        }
        else System.err.println("Cannot set direction's degree to value: " + degree);
    }

    /** Turns the direction 90 degrees anticlockwise */
    public void rotateLeft() {
        setDirection(degree - ANGLE_90);
    }

    /** Turns the direction 90 degrees clockwise */
    public void rotateRight() {
        setDirection(degree + ANGLE_90);
    }

    /** Turns the direction 180 degrees */
    public void rotateReverse() {
        setDirection(degree + ANGLE_180);
    }

    private boolean isValid(int degree) {
        if (degree % ANGLE_90 == 0) return true;
        return false;
    }
}