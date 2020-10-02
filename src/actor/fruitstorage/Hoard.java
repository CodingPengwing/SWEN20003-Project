package actor.fruitstorage;

public class Hoard extends FruitStorage {
    private final static int HOARD_DEFAULT_NUM_FRUIT = 0;
    // Constructor with default hoard image
    public Hoard(double x, double y) {
        super(x, y, "src/res/images/hoard.png", HOARD_DEFAULT_NUM_FRUIT);
        stationaryActors.add(this);
    }
}