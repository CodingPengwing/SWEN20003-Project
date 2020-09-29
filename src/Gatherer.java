
import java.util.ArrayList;
public class Gatherer extends MobileActor {

    // Constructor with preset gatherer image
    public Gatherer(double x, double y, int direction)
    {
        super(x, y, "res/images/gatherer.png", direction);
        mobileActors.add(this);
    }

    public void tick() {

    }
}
