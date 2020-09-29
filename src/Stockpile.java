public class Stockpile extends FruitStorage {
    // Constructor with preset tree image
    public Stockpile(double x, double y) {
        super(x, y, "res/images/cherries.png", 0);
        stationaryActors.add(this);
    }
}