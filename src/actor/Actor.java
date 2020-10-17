package actor;

import maplogic.Location;
import bagel.Image;
import java.util.ArrayList;
import java.util.Arrays;

public class Actor {
//    final public static String TREE = "Tree";
//    final public static String GOLDEN_TREE = "GoldenTree";
//    final public static String STOCKPILE = "Stockpile";
//    final public static String HOARD = "Hoard";
//    final public static String PAD = "Pad";
//    final public static String FENCE = "Fence";
//    final public static String SIGN_UP = "SignUp";
//    final public static String SIGN_DOWN = "SignDown";
//    final public static String SIGN_LEFT = "SignLeft";
//    final public static String SIGN_RIGHT = "SignRight";
//    final public static String POOL = "Pool";
//    final public static String GATHERER = "Gatherer";
//    final public static String THIEF = "Thief";
//    // All the types that will be accepted
//    final private static ArrayList<String> definedTypes = new ArrayList<>(Arrays.asList(TREE, GOLDEN_TREE, STOCKPILE,
//            HOARD, PAD, FENCE, SIGN_UP, SIGN_DOWN, SIGN_LEFT, SIGN_RIGHT, POOL, GATHERER, THIEF));


    private Image image;
    private ActorType type;
    final protected Location location;
    // To store the current Actors in the game that don't move
    final protected static ArrayList<Actor> stationaryActors = new ArrayList<>();


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

    // Draws the image of the Actor at their location
    protected void render() {
        image.drawFromTopLeft(location.getX(), location.getY());
    }

    // Determines if 2 Actors are in the same location
    final public boolean locationEquals(Actor actor) {
        if (location.equals(actor.location)) return true;
        return false;
    }

    // Renders all Actors in the stationaryActors array
    public static void renderStationaryActors() {
        for (Actor actor : stationaryActors) actor.render();
    }

//    public static boolean isDefinedType(String type) {
//        if (definedTypes.contains(type)) return true;
//        return false;
//    }

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