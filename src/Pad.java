public class Pad extends Actor {
    // Constructor with preset tree image
    public Pad(double x, double y) {
        super(x, y, "res/images/pad.png");
        stationaryActors.add(this);
    }
}