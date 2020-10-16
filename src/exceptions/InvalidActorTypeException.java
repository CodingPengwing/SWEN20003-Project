package exceptions;
import java.lang.Exception;
public class InvalidActorTypeException extends Exception {
    public InvalidActorTypeException(String type) {
        super("Actor type " + type + " is not valid");
    }
}
