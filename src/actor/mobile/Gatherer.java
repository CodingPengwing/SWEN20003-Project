package actor.mobile;

import actor.*;
import actor.fruitstorage.*;

public class Gatherer extends MobileActor {
    // Constructor with default gatherer image
    public Gatherer(double x, double y, int direction)
    {
        super(x, y, "src/res/images/gatherer.png", direction);
        MobileActor.gatherers.add(this);
    }

    protected void tick() {
    }
}
