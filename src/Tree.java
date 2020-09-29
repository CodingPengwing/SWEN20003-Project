public class Tree extends FruitStorage {
    // Constructor with preset tree image
    public Tree(double x, double y) {
        super(x, y, "res/images/tree.png", 3);
        stationaryActors.add(this);
    }
}

