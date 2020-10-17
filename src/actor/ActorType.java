package actor;

import java.util.ArrayList;
import java.util.Arrays;

public enum ActorType {
    TREE,
    GOLDENTREE,
    STOCKPILE,
    HOARD,
    PAD,
    FENCE,
    SIGNUP,
    SIGNDOWN,
    SIGNLEFT,
    SIGNRIGHT,
    POOL,
    GATHERER,
    THIEF;

//    private final String type;
//
//    private ActorType(final String type) {
//        this.type = type;
//    }

    public static boolean contains(String type) {
        for (ActorType value : ActorType.values()) {
            if (value.name().equals(type)) {
                return true;
            }
        }
        return false;
    }

//    public static ActorType getType(String type) {
//
//    }
}