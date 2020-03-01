package github.chorman0773.sentry.launch;

/**
 * Represents a Logical Phase in the loading of a game.
 */
public enum LoadingPhase {
    /**
     * Represents the earliest logical phase of a game.
     * Note that it is impossible for games themselves (or mods) to handle this phase,
     *  as LaunchProviders are not yet instantiated.
     * However, external observers (such as those using the launcher-control library),
     *  may interact with games during this phase.
     */
    LOADING,
    /**
     * Represents the earliest phase which can be handled by games.
     * This phase is the active phase when the {@link LaunchArtifact#initialise(LauncherInterface, String[])}
     *  method is called
     */
    INITIALISATION,
    /**
     * Represents the phase when the game is starting up.
     * This phase is the active phase when the {@link LaunchArtifact#init()} method is called,
     *  and when the {@link LaunchArtifact#start()} method is first called.
     */
    STARTUP,
    /**
     * Represents the phase when the game is starting up.
     * This phase is the active phase when the {@link LaunchArtifact#destroy()} method is called,
     *  and when the {@link LaunchArtifact#stop()} method is last called, immediately before the call to destroy.
     */
    SHUTDOWN,
    /**
     * Represents the final phase of execution before the game closes.
     *  Under normal circumstances a game cannot handle this phase (or AT_CRASH).
     * However, the game is still loaded during this phase.
     */
    CLOSING,
    /**
     * Represents the phase just before the game closes due to an uncaught exception.
     */
    BEFORE_CRASH,
    /**
     * Represents the phase as the game is {@link #CLOSING} due to an uncaught exception
     */
    AT_CRASH,
    /**
     * Represents the phase of the game that occurs after the game completely shutsdown due to a crash.
     * Games cannot handle this phase, and it is not safe to access game state from any observers
     */
    AFTER_CRASH
}
