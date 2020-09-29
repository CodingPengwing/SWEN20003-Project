
public class GoldenTree extends FruitStorage {
    public GoldenTree(double x, double y) {
        super(x, y, "res/images/gold-tree.png", 1);
        stationaryActors.add(this);
    }

    @Override
    public void render() {
        image.drawFromTopLeft(location.getX(), location.getY());
    }
}
