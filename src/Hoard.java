public class Hoard extends FruitStorage {
    // Constructor with preset tree image
    public Hoard(double x, double y) {
        super(x, y, "res/images/hoard.png", 0);
        stationaryActors.add(this);
    }
}