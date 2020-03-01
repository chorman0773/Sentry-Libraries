package github.chorman0773.sentry.launch;

import github.lightningcreations.lcjei.IEngineInterface;

/**
 * The root type of "LaunchArtifacts" for Sentry Games of all types.
 * The defined methods are called during the appropriate phase of execution,
 *  and as a result of the various methods defined in IEngineInterface
 */
public interface LaunchArtifact {
    public void initialise(LauncherInterface lint,String[] args);

    /**
     * Called immediate after {@link #init()}, and any time the game is unpaused by {@link IEngineInterface#resume()}.
     */
    public void start();
    public void init();
    /**
     * Called immediate after {@link #destroy()}, and any time the game is paused by {@link IEngineInterface#suspend()}.
     */
    public void stop();
    public void destroy();
}
