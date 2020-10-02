package actor.fruitstorage;

public class Stockpile extends FruitStorage {
    private final static int STOCKPILE_DEFAULT_NUM_FRUIT = 0;
    // Constructor with default stockpile image
    public Stockpile(double x, double y) {
        super(x, y, "src/res/images/cherries.png", STOCKPILE_DEFAULT_NUM_FRUIT);
        stationaryActors.add(this);
    }
}