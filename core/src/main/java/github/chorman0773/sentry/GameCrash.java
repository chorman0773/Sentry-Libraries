package github.chorman0773.sentry;

public class GameCrash extends Error {
    public GameCrash() {
    }

    public GameCrash(String message) {
        super(message);
    }

    public GameCrash(String message, Throwable cause) {
        super(message, cause);
    }

    public GameCrash(Throwable cause) {
        super(cause);
    }
}
