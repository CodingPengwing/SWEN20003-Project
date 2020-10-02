package actor;

public class Fence extends Actor {
    // Constructor with default Fence image
    public Fence(double x, double y) {
        super(x, y, "src/res/images/fence.png");
        stationaryActors.add(this);
    }
}
