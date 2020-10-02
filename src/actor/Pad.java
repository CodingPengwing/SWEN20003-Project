package actor;

public class Pad extends Actor {
    // Constructor with default Pad image
    public Pad(double x, double y) {
        super(x, y, "src/res/images/pad.png");
        stationaryActors.add(this);
    }
}