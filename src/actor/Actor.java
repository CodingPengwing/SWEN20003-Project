package actor;

import maplogic.Location;
import bagel.Image;
import java.util.ArrayList;

/** This class is the underlying structure for all Actors in the game. It
 * contains the type, image and location of the Actor. All Actor related
 * classes inherit from this class either directly or through another child.
 */
public class Actor {
    // Location is made protected so that MovableActor can implement movement
    // logic inside the 'movable' package. It is declared final to restrict
    // mutability. Movement logic should NOT be implemented anywhere outside
    // the 'movable' package.
    protected final Location location;
    private Image image;
    private final ActorType type;

    // To store the current Actors in the game that don't move
    private final static ArrayList<Actor> stationaryActors = new ArrayList<>();

    /** Constructs an Actor when given a type and a location on the map.
     * Upon creation, the image of the Actor will be set according to the
     * default images for each type of Actor, the image is immutable.
     * The Actor will be added to the private static 'stationaryActors'
     * array if it is not a MovableActor type.
     * @param type This is set at creation and is immutable. Only accepts
     *             values of enum ActorType.
     * @param x This is the x position on the map.
     * @param y This is the y position on the map.
     * Using x and y, a Location instance is created.
     */
    public Actor(ActorType type, double x, double y) {
        this.location = new Location(x, y);
        this.type = type;
        setImage(type);
        switch (type) {
            // MovableActor types, don't add to stationaryActors array
            case GATHERER: case THIEF: break;
            // stationary Actor types
            default: stationaryActors.add(this);
        }
    }

    /** Returns the type of the Actor, this enum object is immutable
     * @return instance of ActorType enum
     */
    public final ActorType getType() {
        return type;
    }

    /** Determines whether 2 Actors are in the same location.
     * @param other The other Actor that is being compared to this one.
     * @return true if both Actors are in the same tile. false otherwise.
     */
    public final boolean locationEquals(Actor other) {
        if (location.equals(other.location)) { return true; }
        return false;
    }

    /** Returns an ArrayList of all stationary Actors that are at position (x,y).
     * @param x This is the x position.
     * @param y This is the y position.
     * @return ArrayList<Actor> containing all stationary Actors found at that tile.
     */
    public static ArrayList<Actor> getStationaryActorsAtLocation(double x, double y) {
        ArrayList<Actor> output = new ArrayList<>();
        for (Actor actor : stationaryActors) {
            if (actor.getX() == x && actor.getY() == y) output.add(actor);
        }
        return output;
    }

    /** Renders all stationary Actors onto the screen. */
    public static void renderStationaryActors() {
        for (Actor actor : stationaryActors) { actor.render(); }
    }

    // Renders the image of the Actor at its location. Can be overridden.
    protected void render() { image.drawFromTopLeft(getX(), getY()); }

    // Returns the x coordinate of the Actor
    protected final double getX() { return location.getX(); }
    // Returns the y coordinate of the Actor
    protected final double getY() { return location.getY(); }

    // Returns all the Actors of the specified types given in the arguments.
    // To maintain the immutability of the 'stationaryActors' array, a partial
    // copy is returned through a new ArrayList.
    protected static ArrayList<Actor> getStationaryActorsOfTypes(ActorType... requiredTypes) {
        ArrayList<Actor> output = new ArrayList<>();
        for (Actor actor : stationaryActors) {
            for (ActorType type : requiredTypes) {
                if (actor.type.equals(type)) { output.add(actor); break; }
            }
        }
        return output;
    }

    // Sets the image for the Actor based on its type.
    // The following are all the default images for each type.
    private void setImage(ActorType type) {
        switch (type) {
            case TREE: image = new Image("src/res/images/tree.png"); break;
            case GOLDENTREE: image = new Image("src/res/images/gold-tree.png"); break;
            case STOCKPILE: image = new Image("src/res/images/cherries.png"); break;
            case HOARD: image = new Image("src/res/images/hoard.png"); break;
            case PAD: image = new Image("src/res/images/pad.png"); break;
            case FENCE: image = new Image("src/res/images/fence.png"); break;
            case SIGNUP: image = new Image("src/res/images/up.png"); break;
            case SIGNDOWN: image = new Image("src/res/images/down.png"); break;
            case SIGNLEFT: image = new Image("src/res/images/left.png"); break;
            case SIGNRIGHT: image = new Image("src/res/images/right.png"); break;
            case POOL: image = new Image("src/res/images/pool.png"); break;
            case GATHERER: image = new Image("src/res/images/gatherer.png"); break;
            case THIEF: image = new Image("src/res/images/thief.png"); break;
            default: System.err.println("error: no preset image for this actor type: "+type.toString());
        }
    }
}