public class Location {
    final static private int TILE_SIZE = 64;
    private double x;
    private double y;

    // Constructor
    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Returns x coordinate
    public double getX() {
        return x;
    }

    // Returns y coordinate
    public double getY() {
        return y;
    }

    // Moves the Location one tile up
    public void moveUp() {
        y = y - TILE_SIZE;
    }

    // Moves the Location one tile down
    public void moveDown() {
        y = y + TILE_SIZE;
    }

    // Moves the Location one tile to the left
    public void moveLeft() {
        x = x - TILE_SIZE;
    }

    // Moves the Location one tile to the right
    public void moveRight() {
        x = x + TILE_SIZE;
    }

    // Checks whether the given location is well-defined inside a tile
    public static boolean isWellDefined(double x, double y) {
        if (x % TILE_SIZE == 0 && y % TILE_SIZE == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.x, x) == 0 &&
                Double.compare(location.y, y) == 0;
    }
}
