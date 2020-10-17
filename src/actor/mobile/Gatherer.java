package actor.mobile;

import actor.*;
import maplogic.Direction;

import java.util.ArrayList;

public class Gatherer extends MobileActor {
    // To store all the Gatherer's and Thief's in the game
    final protected static ArrayList<Gatherer> gatherers = new ArrayList<>();
    // To store new MobileActor's created during game execution
    final protected static ArrayList<Gatherer> newGatherers = new ArrayList<>();

    // Constructor with default Gatherer image
    public Gatherer(double x, double y, boolean initialActor) {
        super(ActorType.GATHERER, x, y);
        // If the Gatherer is initially defined at the start of the simulation
        if (initialActor) {
            getDirection().setDirection(Direction.LEFT);
            gatherers.add(this);
        }
        // If the Gatherer is created on the fly during the simulation
        else newGatherers.add(this);
    }

    @Override
    // Tick logic for Gatherer's
    protected void tick() {
        if (!isActive()) return;
        move();
        for (Actor actor : Actor.stationaryActors) {
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
                    case TREE:
                        interactTree(actor); break;
                    case GOLDENTREE:
                        interactGoldenTree(); break;
                    case HOARD:
                        interactHoard(actor); break;
                    case STOCKPILE:
                        interactStockpile(actor); break;
                }
            }
        }
    }

    protected static void tickGatherers() {
        for (Gatherer gatherer : gatherers) gatherer.tick();

        // If new Actors have been created during the tick,
        // add those Actors into their respective arrays
        // and clear out the newMobileActors array
        if (newGatherers.size() > 0) {
            for (Gatherer gatherer : newGatherers) {
                gatherers.add(gatherer);
            }
            newGatherers.clear();
        }
    }

    @Override
    // Interacting with Golden Tree
    protected boolean interactGoldenTree() {
        if (super.interactGoldenTree()) {
            getDirection().rotateReverse();
            return true;
        }
        return false;
    }

    @Override
    // Interacting with Tree
    protected boolean interactTree(Actor actor) {
        if (super.interactTree(actor)) {
            getDirection().rotateReverse();
            return true;
        }
        return false;
    }

    @Override
    protected void interactPad() {}

    @Override
    // Interacting with Hoard
    protected void interactHoard(Actor actor) {
        if (isCarrying()) {
            setCarrying(false);
            FruitStorage storage = (FruitStorage) actor;
            storage.increaseNumFruit();
        }
        getDirection().rotateReverse();
    }

    @Override
    // Interacting with Stockpile
    protected void interactStockpile(Actor actor) {
        interactHoard(actor);
    }
}
