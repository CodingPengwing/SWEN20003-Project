package actor.mobile;

import actor.*;

import java.util.ArrayList;

public class Thief extends MobileActor {
    private boolean consuming;
    final protected static ArrayList<Thief> thieves = new ArrayList<>();

    // To store new MobileActor's created during game execution
    final protected static ArrayList<Thief> newThieves = new ArrayList<>();

    // Constructor with default Thief image
    public Thief(double x, double y, boolean initialActor) {
        super(ActorType.THIEF, x, y);
        consuming = false;
        // If the Thief is initially defined at the start of the simulation
        if (initialActor) thieves.add(this);
        // If the Thief is created on the fly during the simulation
        else newThieves.add(this);
    }

    @Override
    // Tick logic for Thief's
    protected void tick() {
        if (!isActive()) return;
        move();
        for (Actor actor : stationaryActors) {
            if (actor.locationEquals(this)) {
                switch (actor.getType()) {
                    case FENCE:
                        interactFence(); break;
                    case POOL:
                        interactPool(); break;
                    case SIGNUP:
                        interactSignUp(); break;
                    case SIGNDOWN:
                        interactSignDown(); break;
                    case SIGNLEFT:
                        interactSignLeft(); break;
                    case SIGNRIGHT:
                        interactSignRight(); break;
                    case PAD:
                        interactPad(); break;
                }
            }
        }
        for (Gatherer gatherer : Gatherer.gatherers) {
            if (gatherer.locationEquals(this)) {
                interactGatherer(); break;
            }
        }
        for (Actor actor : stationaryActors) {
            if (actor.locationEquals(this)) {
                switch (actor.getType()) {
                    case TREE:
                        interactTree(actor);
                        break;
                    case GOLDENTREE:
                        interactGoldenTree();
                        break;
                    case HOARD:
                        interactHoard(actor);
                        break;
                    case STOCKPILE:
                        interactStockpile(actor);
                        break;
                }
            }
        }
    }

    protected static void tickThieves() {
        for (Thief thief : thieves) thief.tick();

        // If new Actors have been created during the tick,
        // add those Actors into their respective arrays
        // and clear out the newMobileActors array
        if (newThieves.size() > 0) {
            for (Thief thief : newThieves) {
                thieves.add(thief);
            }
            newThieves.clear();
        }
    }

    @Override
    // Interacting with Mitosis Pool
    protected void interactPool() {
        super.interactPool();
        consuming = false;
    }

    @Override
    // Interacting with Pad
    protected void interactPad() {
        consuming = true;
    }

    @Override
    // Interacting with Gatherer
    protected void interactGatherer() {
        getDirection().rotateLeft();
    }

    @Override
    // Interacting with Hoard
    protected void interactHoard(Actor actor) {
        FruitStorage hoard = (FruitStorage) actor;
        if (consuming) {
            consuming = false;
            if (!isCarrying()) {
                if (hoard.getNumFruit() > 0) {
                    setCarrying(true);
                    hoard.decreaseNumFruit();
                }
                else {
                    getDirection().rotateRight();
                }
            }
        }
        else if (isCarrying()) {
            setCarrying(false);
            hoard.increaseNumFruit();
            getDirection().rotateRight();
        }
    }

    @Override
    // Interacting with Stockpile
    protected void interactStockpile(Actor actor) {
        FruitStorage stockpile = (FruitStorage) actor;
        if (!isCarrying()) {
            if (stockpile.getNumFruit() > 0) {
                setCarrying(true);
                consuming = false;
                stockpile.decreaseNumFruit();
                getDirection().rotateRight();
            }
        }
        else getDirection().rotateRight();
    }
}

