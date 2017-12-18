package game.mightywarriors.other.exceptions;

public class BusyChampionException extends Exception {

    public BusyChampionException() {
        super();
    }

    public BusyChampionException(String message) {
        super(message);
    }

    public BusyChampionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusyChampionException(Throwable cause) {
        super(cause);
    }
}
