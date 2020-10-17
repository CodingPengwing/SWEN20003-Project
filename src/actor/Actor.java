package actor;

import maplogic.Location;
import bagel.Image;
import java.util.ArrayList;

public class Actor {
    private Image image;
    final private ActorType type;
    final private Location location;

    // To store the current Actors in the game that don't move
    final private static ArrayList<Actor> stationaryActors = new ArrayList<>();

    public Actor(ActorType type, double x, double y) {
        this.location = new Location(x, y);
        this.type = type;
        setImage(type);
        switch (type) {
            case GATHERER:
            case THIEF:
                break;
            default:
                stationaryActors.add(this);
        }
    }

    public ActorType getType() {
        return type;
    }

    // Determines if 2 Actors are in the same location
    final public boolean locationEquals(Actor actor) {
        if (location.equals(actor.location)) return true;
        return false;
    }

    public static ArrayList<Actor> getStationaryActorsAtLocation(double x, double y) {
        ArrayList<Actor> output = new ArrayList<>();
        for (Actor actor : stationaryActors) {
            if (actor.getX() == x && actor.getY() == y) {
                output.add(actor);
            }
        }
        return output;
    }

    // Renders all Actors in the stationaryActors array
    public static void renderStationaryActors() {
        for (Actor actor : stationaryActors) actor.render();
    }

    // Draws the image of the Actor at their location
    protected void render() {
        image.drawFromTopLeft(location.getX(), location.getY());
    }

    final protected double getX() { return location.getX(); }
    final protected double getY() { return location.getY(); }
    final protected void moveUp() { location.moveUp(); }
    final protected void moveDown() { location.moveDown(); }
    final protected void moveLeft() { location.moveLeft(); }
    final protected void moveRight() { location.moveRight(); }

    protected static ArrayList<Actor> getStationaryActorsOfTypes(ActorType... types) {
        ArrayList<Actor> output = new ArrayList<>();
        for (Actor actor : stationaryActors) {
            for (ActorType type : types) {
                if (actor.type.equals(type)) output.add(actor);
            }
        }
        return output;
    }

    // Sets the image for the Actor based on its type
    private void setImage(ActorType type) {
        switch (type) {
            case TREE:
                image = new Image("src/res/images/tree.png");
                break;
            case GOLDENTREE:
                image = new Image("src/res/images/gold-tree.png");
                break;
            case STOCKPILE:
                image = new Image("src/res/images/cherries.png");
                break;
            case HOARD:
                image = new Image("src/res/images/hoard.png");
                break;
            case PAD:
                image = new Image("src/res/images/pad.png");
                break;
            case FENCE:
                image = new Image("src/res/images/fence.png");
                break;
            case SIGNUP:
                image = new Image("src/res/images/up.png");
                break;
            case SIGNDOWN:
                image = new Image("src/res/images/down.png");
                break;
            case SIGNLEFT:
                image = new Image("src/res/images/left.png");
                break;
            case SIGNRIGHT:
                image = new Image("src/res/images/right.png");
                break;
            case POOL:
                image = new Image("src/res/images/pool.png");
                break;
            case GATHERER:
                image = new Image("src/res/images/gatherer.png");
                break;
            case THIEF:
                image = new Image("src/res/images/thief.png");
                break;
        }
    }
}