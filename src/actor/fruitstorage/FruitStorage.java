package actor.fruitstorage;

import actor.Actor;
import bagel.Font;

public abstract class FruitStorage extends Actor {
    private final static int FRUIT_FONT_SIZE = 25;
    private int numFruit;

    public FruitStorage(double x, double y, String imagePath, int numFruit) {
        super(x, y, imagePath);
        this.numFruit = numFruit;
    }

    final public int getNumFruit() {
        return numFruit;
    }

    // Increase numFruit by 1
    final public void increaseNumFruit() {
        numFruit++;
    }

    // Decrease numFruit by 1
    final public void decreaseNumFruit() {
        numFruit--;
    }

    // Overrides default render method to render the number of fruits as well as Actor image
    @Override
    final protected void render() {
        super.render();
        Font font = new Font("src/res/VeraMono.ttf", FRUIT_FONT_SIZE);
        font.drawString(Integer.toString(numFruit), location.getX(), location.getY());
    }

    public static void tallyHoardsAndStockpiles() {
        for (Actor actor : stationaryActors) {
            if (actor instanceof Hoard || actor instanceof Stockpile) {
                FruitStorage fs = (FruitStorage) actor;
                System.out.println(fs.numFruit);
            }
        }
    }
}