package actor.fruitstorage;

public class Tree extends FruitStorage {
    private final static int TREE_DEFAULT_NUM_FRUIT = 3;
    // Constructor with default Tree image
    public Tree(double x, double y) {
        super(x, y, "src/res/images/tree.png", TREE_DEFAULT_NUM_FRUIT);
        stationaryActors.add(this);
    }
}

