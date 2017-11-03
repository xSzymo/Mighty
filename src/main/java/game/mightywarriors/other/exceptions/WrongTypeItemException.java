package game.mightywarriors.other.exceptions;

public class WrongTypeItemException extends Exception {

    public WrongTypeItemException() {
        super();
    }

    public WrongTypeItemException(String message) {
        super(message);
    }

    public WrongTypeItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongTypeItemException(Throwable cause) {
        super(cause);
    }
}
