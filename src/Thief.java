import java.util.ArrayList;
public class Thief extends MobileActor {
    private boolean consuming;

    // Constructor with preset gatherer image
    public Thief(double x, double y, int direction)
    {
        super(x, y, "res/images/thief.png", direction);
        consuming = false;
        mobileActors.add(this);
    }

    public void tick() {};
}

