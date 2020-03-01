package github.chorman0773.sentry.launcher.control;

import github.chorman0773.sentry.launch.LauncherContext;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.chorman0773.sentry.launch.LoadingPhase;
import github.chorman0773.sentry.launch.marker.ChecksPermission;

import java.lang.instrument.Instrumentation;
import java.net.URI;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Handle to launch a game Entry.
 */
public interface GameHandle {
    /**
     * Obtains an interface to the LauncherContext which the game will run in.
     * Because the LauncherInterface is accessed from outside the context of the game,
     *  certain access guarantees, such as those provided by {@link LauncherInterface#getGameRoot()},
     *  may not be satisfied.
     *
     * However, for the most part, many permissions will be available to this LauncherInterface that are not
     *  otherwise automatically available.
     */
    public LauncherInterface boundLauncher();

    /**
     * Returns the class loader which finds classes loaded by the game
     */
    public ClassLoader getHandleClassLoader();

    /**
     * Creates a Hook to be called during the given LoadingPhase of the game.
     * The Loading Hook is granted all permissions specified in the current Security Context
     *  as obtained by a call to the {@link SecurityManager#getSecurityContext()} applied to the in-place security Manager.
     * <br/>
     * The hook is passed the LauncherInterface returned by boundLauncher().
     *
     * Additionally, the hook is evaluated in the context of the running game,
     *  as can be observed by a call to {@link LauncherContext#getLauncherContext()}.
     *
     * Note that it is important not to leak the passed LauncherInterface to any other part of the running game.
     * The results of doing so are highly implementation-specific, and may be unpredictable.
     */
    public void addLoadingHook(LoadingPhase phase, Consumer<LauncherInterface> hook);

    /**
     * Launches the Game specified by Handle.
     * THis method may be called at most once.
     *  Subsequent calls to this method will throw an IllegalStateException
     */
    public void launch() throws IllegalStateException;

    /**
     * Installs a game local agent which can instrument classes within the game.
     * @param agentMain The handle for which to pass the agent
     * @throws UnsupportedOperationException If Instrumentation is not supported by the game
     */
    @ChecksPermission("LauncherPermission(instrument)")
    public void addAgent(BiConsumer<String[], Instrumentation> agentMain);
}
