package actor.mobile;

import actor.*;
import actor.fruitstorage.*;

public class Thief extends MobileActor {
    private boolean consuming;

    // Constructor with default gatherer image
    public Thief(double x, double y, int direction)
    {
        super(x, y, "src/res/images/thief.png", direction);
        consuming = false;
        thieves.add(this);
    }

    protected void tick() {
    }
}

