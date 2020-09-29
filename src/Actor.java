import bagel.Image;

import java.util.ArrayList;

public abstract class Actor {
    protected Image image;
    protected Location location;

    protected static ArrayList<Actor> stationaryActors = new ArrayList<>();

    // Constructor
    public Actor(double x, double y) {
        this.location = new Location(x, y);
    }

    public Actor(double x, double y, String imagePath) {
        this(x, y);
        image = new Image(imagePath);
    }

    // Draws the image of the Actor at their location
    public void render() {
        image.drawFromTopLeft(location.getX(), location.getY());
    }

}
