/** This class contains most of the error handling that happens while the program
 * is taking input from the command line or world file. There are 3 categories of
 * errors that are catered for, divided into 3 different constructors.
 */
public class InvalidInputException extends Exception {
    /** This is the standard error message that is produced when the initial
     * command line input is not well defined.
     */
    public InvalidInputException() {
        super("usage: ShadowLife <tick rate> <max ticks> <world file>");
    }

    /** This is the error message that is produced when the worldFile given in
     * the command line arguments is not found.
     * @param worldFile This is the world file that caused the error.
     */
    public InvalidInputException(String worldFile) {
        super("error: file \"" + worldFile + "\" not found");
    }

    /** This is the error message that is produced when the a line inside a
     * world file is not well defined. The correct format should be:
     * [actor type],[x coordinate],[y coordinate]
     * @param worldFile This is the world file where the error was found.
     * @param lineNumber This is the line that caused the error.
     */
    public InvalidInputException(String worldFile, int lineNumber) {
        super("error: in file \"" + worldFile + "\" at line " + lineNumber);
    }
}
