package game.mightywarriors.other.exceptions;

public class LockedAccountException extends Exception {

    public LockedAccountException() {
        super();
    }

    public LockedAccountException(String message) {
        super(message);
    }

    public LockedAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockedAccountException(Throwable cause) {
        super(cause);
    }
}
