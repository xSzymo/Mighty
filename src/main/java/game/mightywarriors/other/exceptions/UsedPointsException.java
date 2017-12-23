package game.mightywarriors.other.exceptions;

public class UsedPointsException extends Exception {

    public UsedPointsException() {
        super();
    }

    public UsedPointsException(String message) {
        super(message);
    }

    public UsedPointsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsedPointsException(Throwable cause) {
        super(cause);
    }
}
