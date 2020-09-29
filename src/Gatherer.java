
import java.util.ArrayList;
public class Gatherer extends MobileActor {

    // Constructor with preset gatherer image
    public Gatherer(double x, double y, int direction)
    {
        super(x, y, "res/images/gatherer.png", direction);
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
                    Gatherer g1 = new Gatherer(location.getX(), location.getY(), direction.getDirection()-90);
                    Gatherer g2 = new Gatherer(location.getX(), location.getY(), direction.getDirection()+90);
                    g1.move();
                    g2.move();
                    mobileActors.remove(this);
                }
                if (actor instanceof Sign) {
                    direction.setDirection(((Sign) actor).getDirection());
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
