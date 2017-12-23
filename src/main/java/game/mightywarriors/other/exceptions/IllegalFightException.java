package game.mightywarriors.other.exceptions;

public class IllegalFightException extends Exception {

    public IllegalFightException() {
        super();
    }

    public IllegalFightException(String message) {
        super(message);
    }

    public IllegalFightException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalFightException(Throwable cause) {
        super(cause);
    }
}
