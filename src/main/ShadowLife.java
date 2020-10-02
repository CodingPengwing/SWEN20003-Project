package main;

import actor.*;
import actor.fruitstorage.*;
import actor.mobile.*;
import maplogic.*;
import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;

import java.io.FileReader;
import java.util.Scanner;

public class ShadowLife extends AbstractGame {
    private static final int GAME_HEIGHT = 768;
    private static final int GAME_WIDTH = 1024;
    private static final int TICK_TIME = 100;
    private static final int BACKGROUND_X = 0;
    private static final int BACKGROUND_Y = 0;
    private int tickCount;
    private int maxTicks;
    private long lastTick;
    private final Image background = new Image("src/res/images/background.png");

    public ShadowLife() {
        super(GAME_WIDTH, GAME_HEIGHT, "Shadow Life");
        tickCount = 0;
        lastTick = System.currentTimeMillis();
    }

    // Start of the program
    public static void main(String[] args) {
        ShadowLife game = new ShadowLife();
        createSetting("src/res/worlds/product.csv");
        game.run();
    }

    // Renders the game constantly, provides the game with updates every tick (500ms)
    @Override
    public void update(Input input) {
        long currentTime = System.currentTimeMillis();
        // Update the state of the world every tick (500ms)
        if (currentTime - lastTick > TICK_TIME) {
            updateState();
            lastTick = currentTime;
            tickCount++;
        }
        renderState();
        if (tickCount > maxTicks) {
            stopGame();
        }
    }

    // Draws all Actors and the background in the game's current state
    private void renderState() {
        background.drawFromTopLeft(BACKGROUND_X, BACKGROUND_Y);
        Actor.renderStationaryActors();
        MobileActor.renderMobileActors();
    }

    // Update the state of the world
    private void updateState() {
        MobileActor.tickMobileActors();
    }

    // Stops the game and provides output to stdout
    private void stopGame() {

    }

    // Gets input from stdin
    private static void getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(input);
    }

    // Opens the given worldFile and creates Actors for the game
    private static void createSetting(String worldFile) {
        try (Scanner scanner = new Scanner(new FileReader(worldFile))) {
            while (scanner.hasNextLine()) {
                String[] input = scanner.nextLine().split(",");
                // After splitting, the first value should be the type of Actor,
                // the second and third values should be the x and y coordinates.
                String type = input[0];
                double x = Double.parseDouble(input[1]);
                double y = Double.parseDouble(input[2]);
                // Check whether the Actors are spawned in a valid tile, if yes,
                // create an instance.
                if (Location.isWellDefined(x, y)) {
                    switch (type) {
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
                        case "Pool":
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