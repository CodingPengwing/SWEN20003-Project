/** This class contains most of the error handling that happens while the program
 * is taking input from the command line or world file. There are 3 categories of
 * errors that are catered for, divided into 3 different constructors. The standard
 * procedure to handle these exceptions is to print the error message and exit with
 * status -1.
 */
public class InvalidInputException extends Exception {
    private static int FAILURE = -1;

    /** This is the standard error message that is produced when the initial
     * command line input is not well defined.
     */
    public InvalidInputException() {
        super("usage: ShadowLife <tick rate> <max ticks> <world file>");
    }

    /** This is the error message that is produced when the worldFile given in
     * the command line arguments is not found.
     * @param worldFile
     */
    public InvalidInputException(String worldFile) {
        super("error: file \"" + worldFile + "\" not found");
    }

    /** This is the error message that is produced when the a line inside a
     * world file is not well defined. The correct format should be:
     * [actor type],[x coordinate],[y coordinate]
     * @param worldFile This is the world file given through cmd args.
     * @param lineNumber This is the line number where the error occurred.
     */
    public InvalidInputException(String worldFile, int lineNumber) {
        super("error: in file \"" + worldFile + "\" at line " + lineNumber);
    }

    /** This is the standard way to handle exceptions in this program.
     * Print the message, then exit with status -1.
     */
    public void handler() {
        System.out.println(getMessage());
        System.exit(FAILURE);
    }
}
