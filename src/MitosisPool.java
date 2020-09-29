public class MitosisPool extends Actor {
    // Constructor with preset tree image
    public MitosisPool(double x, double y) {
        super(x, y, "res/images/pool.png");
        stationaryActors.add(this);
    }
}
