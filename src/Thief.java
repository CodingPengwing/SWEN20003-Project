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

    public void tick() {
        if (active) move();
        for (Actor actor : stationaryActors) {
            if (actor.location.equals(location)) {
                if (actor instanceof Fence) {
                    active = false;
                    direction.setDirection(direction.getDirection()-180);
                    move();
                }
                if (actor instanceof MitosisPool) {
                    Thief t1 = new Thief(location.getX(), location.getY(), direction.getDirection()-90);
                    Thief t2 = new Thief(location.getX(), location.getY(), direction.getDirection()+90);
                    t1.move();
                    t2.move();
                    mobileActors.remove(this);
                }
                if (actor instanceof Sign) {
                    direction.setDirection(((Sign) actor).getDirection());
                }
                if (actor instanceof Pad) {
                    consuming = true;
                }

                if ((actor instanceof Tree || actor instanceof GoldenTree) && !carrying) {
                    if (((FruitStorage) actor).getNumFruit() > 0) {
                        carrying = true;
                        direction.setDirection(direction.getDirection()-180);
                    }
                    if (actor instanceof Tree) {
                        Tree tree = (Tree) actor;
                        if (tree.getNumFruit() > 0)
                            tree.setNumFruit(tree.getNumFruit()-1);
                    }
                }
                if (actor instanceof Hoard || actor instanceof Stockpile) {
                    if (carrying) {
                        carrying = false;
                        FruitStorage storage = (FruitStorage) actor;
                        storage.setNumFruit(storage.getNumFruit()+1);
                    }
                    direction.setDirection(direction.getDirection()-180);
                }
            }
        }
    }
}

