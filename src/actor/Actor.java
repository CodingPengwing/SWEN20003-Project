package actor;

import maplogic.Location;
import bagel.Image;
import java.util.ArrayList;

public abstract class Actor {
    private Image image;
    protected Location location;

    // To store the current Actors in the game that don't move
    protected static ArrayList<Actor> stationaryActors = new ArrayList<>();

    public Actor(double x, double y) {
        this.location = new Location(x, y);
    }

    public Actor(double x, double y, String imagePath) {
        this(x, y);
        image = new Image(imagePath);
    }

    protected void setImage(String imagePath) {
        image = new Image(imagePath);
    }

    // Draws the image of the Actor at their location
    protected void render() {
        image.drawFromTopLeft(location.getX(), location.getY());
    }

    // Determines if 2 Actors are in the same location
    public boolean locationMatch(Actor actor) {
        if (location.equals(actor.location)) return true;
        return false;
    }

    // Renders all Actors in the stationaryActors array
    public static void renderStationaryActors() {
        for (Actor actor : stationaryActors) actor.render();
    }
}