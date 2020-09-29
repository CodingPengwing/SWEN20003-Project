import java.util.ArrayList;
public class Thief extends MobileActor {
    private boolean consuming;

    public static ArrayList<Thief> thieves = new ArrayList<>();

    // Constructor with preset gatherer image
    public Thief(double x, double y, int direction)
    {
        super(x, y, "res/images/thief.png", direction);
        consuming = false;
        thieves.add(this);
    }

    public void tick() {
        if (!active) return;
        move();
        for (Actor actor : stationaryActors) {
            if (actor.location.equals(location)) {
                if (actor instanceof Fence) {
                    active = false;
                    direction.setDirection(direction.getDirection() - 180);
                    move();
                }
                if (actor instanceof MitosisPool) {
                    Thief t1 = new Thief(location.getX(), location.getY(), direction.getDirection() - 90);
                    Thief t2 = new Thief(location.getX(), location.getY(), direction.getDirection() + 90);
                    t1.move();
                    t2.move();
                    thieves.remove(this);
                }
                if (actor instanceof Sign) {
                    direction.setDirection(((Sign) actor).getDirection());
                }
                if (actor instanceof Pad) {
                    consuming = true;
                }
            }
        }
        for (Gatherer gatherer : Gatherer.gatherers) {
            if (gatherer.location.equals(location)) {
                direction.setDirection(direction.getDirection()+270);
                break;
            }
        }
        for (Actor actor : stationaryActors) {
            if (actor.location.equals(location)) {
                if ((actor instanceof Tree || actor instanceof GoldenTree) && !carrying) {
                    if (((FruitStorage) actor).getNumFruit() > 0) {
                        carrying = true;
                        //direction.setDirection(direction.getDirection()-180);
                    }
                    if (actor instanceof Tree) {
                        Tree tree = (Tree) actor;
                        if (tree.getNumFruit() > 0)
                            tree.setNumFruit(tree.getNumFruit()-1);
                    }
                }
                if (actor instanceof Hoard) {
                    Hoard hoard = (Hoard) actor;
                    if (consuming) {
                        consuming = false;
                        if (!carrying) {
                            if (hoard.getNumFruit() > 0) {
                                carrying = true;
                                hoard.setNumFruit(hoard.getNumFruit()-1);
                            }
                            else {
                                direction.setDirection(direction.getDirection()+90);
                            }
                        }
                    }
                    else if (carrying) {
                        carrying = false;
                        hoard.setNumFruit(hoard.getNumFruit()+1);
                        direction.setDirection(direction.getDirection()+90);
                    }
                }
                if (actor instanceof Stockpile) {
                    Stockpile stockpile = (Stockpile) actor;
                    if (!carrying) {
                        if (stockpile.getNumFruit() > 0) {
                            carrying = true;
                            consuming = false;
                            stockpile.setNumFruit(stockpile.getNumFruit()-1);
                            direction.setDirection(direction.getDirection()+90);
                        }
                    }
                    else direction.setDirection(direction.getDirection()+90);
                }
            }
        }
    }
}

