public class InvalidInputException extends Exception {
    private static int FAILURE = -1;
    public InvalidInputException() {
        super("usage: ShadowLife <tick rate> <max ticks> <world file>");
    }

    public InvalidInputException(String worldFile) {
        super("error: file \"" + worldFile + "\" not found");
    }

    public InvalidInputException(String worldFile, int lineNumber) {
        super("error: in file \"" + worldFile + "\" at line " + lineNumber);
    }

    public void handler() {
        System.out.println(getMessage());
        System.exit(FAILURE);
    }
}
