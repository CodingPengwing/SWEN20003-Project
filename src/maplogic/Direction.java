package maplogic;

/** This class defines the direction logic to help with the movement logic in
 * the game. The only four directions currently defined are UP, DOWN, LEFT, RIGHT.
 */
public enum Direction {
    // The directions are purposefully declared in clockwise order like so:
    // UP -> RIGHT -> DOWN -> LEFT
    // This allows for computations of any angle when turned 90 degrees left,
    // 90 degrees right or 180 degrees backward.

    /** The preset UP direction (can be thought of as North). */
    UP,
    /** The preset RIGHT direction (can be thought of as East). */
    RIGHT,
    /** The preset DOWN direction (can be thought of as South). */
    DOWN,
    /** The preset LEFT direction (can be thought of as West). */
    LEFT;

    // Array of all the defined Directions
    private final static Direction[] directions = values();

    /** Returns the current direction rotated 90 degrees anticlockwise */
    public Direction rotateLeft() {
        // Find the Direction value in the previous slot of the directions array
        int newDirectionPosition = (this.ordinal()-1 + directions.length) % directions.length;
        return directions[newDirectionPosition];
    }

    /** Returns the current direction rotated 90 degrees clockwise */
    public Direction rotateRight() {
        // Find the Direction value in the next slot of the directions array
        int newDirectionPosition = (this.ordinal()+1) % directions.length;
        return directions[newDirectionPosition];
    }

    /** Returns the current direction rotated 180 degrees backward */
    public Direction rotateReverse() {
        // Find the Direction value 2 slots forward in the directions array
        int newDirectionPosition = (this.ordinal()+2) % directions.length;
        return directions[newDirectionPosition];
    }
}