package actor;

public class MitosisPool extends Actor {
    // Constructor with default mitosis pool image
    public MitosisPool(double x, double y) {
        super(x, y, "src/res/images/pool.png");
        stationaryActors.add(this);
    }
}
