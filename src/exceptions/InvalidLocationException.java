package exceptions;
import java.lang.Exception;
public class InvalidLocationException extends Exception {
    public InvalidLocationException(double x, double y) {
        super("Location (" + x + "), (" + y + ") is not a valid tile");
    }
}
