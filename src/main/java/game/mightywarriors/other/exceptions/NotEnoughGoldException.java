package game.mightywarriors.other.exceptions;

public class NotEnoughGoldException extends Exception {

    public NotEnoughGoldException() {
        super();
    }

    public NotEnoughGoldException(String message) {
        super(message);
    }

    public NotEnoughGoldException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughGoldException(Throwable cause) {
        super(cause);
    }
}
