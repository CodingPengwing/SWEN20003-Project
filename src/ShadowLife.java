import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;

import javax.swing.plaf.TableHeaderUI;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.FileReader;
import java.util.Scanner;

public class ShadowLife extends AbstractGame {
    private static int TICK_LENGTH = 200;
    private static boolean firstTick = true;
    private static int tickCount = 0;
    private final Image background = new Image("res/images/background.png");
    // To keep track of the previous update time
    private long lastTick = System.currentTimeMillis();

    public ShadowLife() {
        // Default dimensions with name of game
        super(1024, 768, "Shadow Life");
    }

    // Start of the program
    public static void main(String[] args) {
        ShadowLife game = new ShadowLife();
        // Create the setting for current World and add to 'worlds' array
        createSetting("res/worlds/harvest.csv");
        game.run();
    }

    // Provides the game with updates to each Actor's location every tick (500ms)
    @Override
    public void update(Input input) {
        long currentTime = System.currentTimeMillis();

        if (firstTick) {
            drawCurrentState();
            firstTick = false;
        }
        else {
            // Only update the state of the world every tick (500ms)
            if (currentTime - lastTick > TICK_LENGTH) {
                MobileActor.updateActors();
                lastTick = currentTime;
            }
            drawCurrentState();
        }
    }

    // Draws all Actors and the background in the World's current state
    public void drawCurrentState() {
        background.drawFromTopLeft(0, 0);
        for (Actor actor : Actor.stationaryActors) { actor.render(); }
        for (Actor actor : MobileActor.mobileActors) {
            actor.render();
        }
    }



    private static void createSetting(String worldFile) {
        // Open world file and take input
        try (Scanner scanner = new Scanner(new FileReader(worldFile))) {
            while (scanner.hasNextLine()) {
                // This program assumes the input given is cleaned
                String[] input = scanner.nextLine().split(",");
                double x = Double.parseDouble(input[1]);
                double y = Double.parseDouble(input[2]);

                // Check whether the Actors are spawned in a valid tile, if yes,
                // create and store Trees/Gatherers in their respective arrays
                if (Location.isWellDefined(x, y)) {
                    switch (input[0]) {
                        case "Tree":
                            new Tree(x, y);
                            break;
                        case "GoldenTree":
                            new GoldenTree(x, y);
                            break;
                        case "Stockpile":
                            new Stockpile(x, y);
                            break;
                        case "Hoard":
                            new Hoard(x, y);
                            break;
                        case "Pad":
                            new Pad(x, y);
                            break;
                        case "Fence":
                            new Fence(x, y);
                            break;
                        case "SignUp":
                            new Sign(x, y, Direction.UP);
                            break;
                        case "SignDown":
                            new Sign(x, y, Direction.DOWN);
                            break;
                        case "SignLeft":
                            new Sign(x, y, Direction.LEFT);
                            break;
                        case "SignRight":
                            new Sign(x, y, Direction.RIGHT);
                            break;
                        case "MitosisPool":
                            new MitosisPool(x, y);
                            break;
                        case "Gatherer":
                            new Gatherer(x, y, Direction.LEFT);
                            break;
                        case "Thief":
                            new Thief(x, y, Direction.UP);
                            break;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}