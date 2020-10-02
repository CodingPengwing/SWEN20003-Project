package actor;

public class GoldenTree extends Actor {
    // Constructor with default Golden Tree image
    public GoldenTree(double x, double y) {
        super(x, y, "src/res/images/gold-tree.png");
        stationaryActors.add(this);
    }
}
