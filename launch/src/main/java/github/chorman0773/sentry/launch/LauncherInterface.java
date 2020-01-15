package github.chorman0773.sentry.launch;

import github.lightningcreations.lcjei.IEngineInterface;
import github.lightningcreations.lcjei.IGameInfo;
import github.lightningcreations.lcjei.resources.ResourceSet;

import java.net.URI;
import java.nio.file.Path;
import java.security.AccessControlContext;
import java.security.PermissionCollection;
import java.security.PrivilegedAction;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for interacting with the Sentry Launcher.
 * An instance of this class is passed to Sentry
 */
public interface LauncherInterface extends IEngineInterface<GameLaunchArtifact> {
    /**
     * Closes the launcher window, stopping the game execution.
     */
    public void close() throws IllegalStateException;
    public IGameInfo<GameLaunchArtifact> getGameInfo();
    public Path getGameRoot();
    public ResourceSet<Path> getResources();
    public SecurityManager getSandbox();
    public Stream<ModInterface> getLoadedMods();
    public ModInterface loadMod(URI uri) throws IllegalStateException;
    public void disableMods();
    public <T> T privilegedExec(PrivilegedAction<T> r, PermissionCollection requested) throws SecurityException;
    public void doPrivileged(Runnable r, AccessControlContext ctx);

    /**
     * Obtains a handle to some service of the launcher, returning Optional.empty() if the requested service is unavailable
     */
    public <S extends LauncherService<S>> Optional<S> getService(Class<S> cl);
}
