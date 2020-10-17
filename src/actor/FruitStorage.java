package actor;

import bagel.Font;

public class FruitStorage extends Actor {
    private final static int FRUIT_FONT_SIZE = 25;
    private final static Font FRUIT_FONT = new Font("src/res/VeraMono.ttf", FRUIT_FONT_SIZE);
    private final static int TREE_DEFAULT_NUM_FRUIT = 3;
    private final static int DEFAULT_NUM_FRUIT = 0;
    private int numFruit;

    public FruitStorage(ActorType type, double x, double y) {
        super(type, x, y);
        switch (type) {
            case TREE:
                numFruit = TREE_DEFAULT_NUM_FRUIT;
                break;
            default:
                numFruit = DEFAULT_NUM_FRUIT;
        }
    }

    public int getNumFruit() {
        return numFruit;
    }

    // Increase numFruit by 1
    public void increaseNumFruit() {
        numFruit++;
    }

    // Decrease numFruit by 1
    public void decreaseNumFruit() {
        numFruit--;
    }

    // Overrides default render method to render the number of fruits as well as Actor image
    @Override
    protected void render() {
        super.render();
        FRUIT_FONT.drawString(Integer.toString(numFruit), getX(), getY());
    }

    public static void tallyHoardsAndStockpiles() {
        for (Actor actor : getStationaryActorsOfTypes(ActorType.HOARD, ActorType.STOCKPILE)) {
            FruitStorage fs = (FruitStorage) actor;
            System.out.println(fs.numFruit);
        }
    }
}