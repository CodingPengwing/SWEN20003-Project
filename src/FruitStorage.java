import bagel.Font;

public abstract class FruitStorage extends Actor {
    protected int numFruit;
    // Constructor with preset tree image
    public FruitStorage(double x, double y, String imagePath, int numFruit) {
        super(x, y, imagePath);
        this.numFruit = numFruit;
    }

    public int getNumFruit() {
        return numFruit;
    }

    public void setNumFruit(int numFruit) {
        this.numFruit = numFruit;
    }

    @Override
    public void render() {
        image.drawFromTopLeft(location.getX(), location.getY());
        Font font = new Font("res/VeraMono.ttf",25);
        font.drawString(Integer.toString(numFruit), location.getX(), location.getY());
    }
}
