package maplogic;

/** This class defines the location (or position) logic for the game.
 * A location consists of an x and y coordinate.
 */
public class Location {
    // This is the preset tile size for the game, the movement mechanism depends on this.
    private final static int TILE_SIZE = 64;

    // x and y coordinates on the map
    private double x;
    private double y;

    /** Constructs a Location instance containing an x and y coordinate.
     * Only accepts multiples of 64 for both x and y.
     * @param x This is the x coordinate on the map.
     * @param y This is the y coordinate on the map.
     */
    public Location(double x, double y) {
        if (isDefinedTile(x, y)) {
            this.x = x;
            this.y = y;
        }
        else System.err.println("error: (" + x + ", " + y + ") is not a defined tile.");
    }

    /** Returns the x coordinate.
     * @return x coordinate.
     */
    public double getX() {
        return x;
    }

    /** Returns the y coordinate.
     * @return y coordinate.
     */
    public double getY() {
        return y;
    }

    /** Moves the Location one tile up */
    public void moveUp() {
        y = y - TILE_SIZE;
    }

    /** Moves the Location one tile down */
    public void moveDown() {
        y = y + TILE_SIZE;
    }

    /** Moves the Location one tile left */
    public void moveLeft() {
        x = x - TILE_SIZE;
    }

    /** Moves the Location one tile right */
    public void moveRight() {
        x = x + TILE_SIZE;
    }

    /** Checks whether the given location is a well-defined tile */
    public static boolean isDefinedTile(double x, double y) {
        if ((x % TILE_SIZE == 0) && (y % TILE_SIZE == 0)) {
            return true;
        }
        return false;
    }

    /** Checks whether 2 Location instances are equal to each other
     * @param o The other Location that is being compared to this one.
     * @return true if the Locations are equal. false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.x, x) == 0 &&
                Double.compare(location.y, y) == 0;
    }
}