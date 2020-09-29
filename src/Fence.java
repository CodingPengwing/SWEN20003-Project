public class Fence extends Actor {
    // Constructor with preset tree image
    public Fence(double x, double y) {
        super(x, y, "res/images/fence.png");
        stationaryActors.add(this);
    }
}
