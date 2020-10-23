package actor;

/** This enum class defines all the types of Actors that are currently defined
 * for the game. Any types that are not specified here will not be allowed in
 * the game as an Exception will be thrown somewhere in the program.
 *
 * Side note: Future implementations can make use of reflections if there is a
 * significant increase in Actor types.
 */
public enum ActorType {
    TREE, GOLDENTREE, STOCKPILE, HOARD, PAD, FENCE, SIGNUP,
    SIGNDOWN, SIGNLEFT, SIGNRIGHT, POOL, GATHERER, THIEF;
}