package github.chorman0773.sentry;

/**
 * Represents a fatal error that occurs during some initialization stage of the game.
 *
 * If caught by a game, the game MUST rethrow the error. The Launcher is intended to handle GameCrash.
 *
 * Both the Game and Sentry itself may throw a GameCrash.
 */
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
