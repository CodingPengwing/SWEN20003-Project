package actor;

import bagel.Font;
import java.util.ArrayList;

/** This class is the parent class for all Actor types that have a fruit count
 * of some sort (currently Tree, Hoard and Stockpile). It overrides the render()
 * method defined in the Actor parent class to also render the number of fruits
 * (numFruit) contained by the FruitStorage Actor alongside the its own image.
 * It offers strict methods to mutate the numFruit
 */
public class FruitStorage extends Actor {
    // Default font and font size for rendering the fruit number
    private final static int FRUIT_FONT_SIZE = 24;
    private final static Font fruitFont = new Font("src/res/VeraMono.ttf", FRUIT_FONT_SIZE);

    // Default fruit count for Actors
    private final static int TREE_DEFAULT_NUM_FRUIT = 3;
    private final static int DEFAULT_NUM_FRUIT = 0;

    private int numFruit = DEFAULT_NUM_FRUIT;

    /** Contructs a FruitStorage type of Actor. Upon creation, the number of fruits
     * is set depending on the specific type of Actor that was created (ie. Tree,
     * Hoard or Stockpile).
     * @param type This is the ActorType instance, it is passed on to the Actor
     *            constructor to store.
     * @param x This is the x position on the map, it is passed on to the Actor
     *          constructor to initialise Location.
     * @param y This is the y position on the map, it is passed on to the Actor
     *          constructor to initialise Location.
     */
    public FruitStorage(ActorType type, double x, double y) {
        super(type, x, y);
        if (type == ActorType.TREE) numFruit = TREE_DEFAULT_NUM_FRUIT;
    }

    /** This method returns the number of fruits that the Actor is currently holding.
     * @return numFruit
     */
    public int getNumFruit() { return numFruit; }

    /** Increments the number of fruits that the Actor holds (numFruit) by 1
     */
    public void increaseNumFruit() { numFruit++; }

    /** Decrements the number of fruits that the Actor holds (numFruit) by 1
     */
    public void decreaseNumFruit() {
        if (numFruit > 0) numFruit--;
        else System.err.println("Cannot decrease numFruit to negative number.");
    }

    /** Prints the number of fruits at each Hoard and Stockpile to stdout
     * in the order these Actors were created. The method calls
     */
    public static void tallyHoardsAndStockpiles() {
        ArrayList<Actor> hoardsAndStockpiles = getStationaryActorsOfTypes(ActorType.HOARD, ActorType.STOCKPILE);
        for (Actor actor : hoardsAndStockpiles) {
            FruitStorage fs = (FruitStorage) actor;
            System.out.println(fs.numFruit);
        }
    }

    // Overrides default render method to render the number of fruits as well as Actor image
    @Override
    protected void render() {
        super.render();
        fruitFont.drawString(Integer.toString(numFruit), getX(), getY());
    }
}