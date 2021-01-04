package github.chorman0773.sentry.launch;

import github.chorman0773.sentry.launch.marker.ChecksPermission;
import github.lightningcreations.lcjei.IEngineInterface;
import github.lightningcreations.lcjei.IGameInfo;
import github.lightningcreations.lcjei.resources.ResourceSet;

import javax.swing.*;
import java.net.URI;
import java.nio.file.Path;
import java.security.AccessControlContext;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.PrivilegedAction;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface for interacting with the Sentry Launcher.
 * An instance of this class is passed to Sentry.
 *
 * @apiNote This interface is intended to be implemented by the Sentry Launcher.
 *  Implementing this interface in user code is unsupported.
 *  As such, any increase in the requirements for implementation
 *  shall not be considered a breaking change for the purposes of semantic versioning.
 */
public interface LauncherInterface extends IEngineInterface<GameLaunchArtifact> {
    /**
     * Closes the launcher window, stopping the game execution.
     */
    @ChecksPermission("LauncherPermission(window.close)")
    public void close() throws IllegalStateException, SecurityException;

    /**
     * Gets the Game Descriptor
     */
    public IGameInfo<GameLaunchArtifact> getGameInfo();

    /**
     * Gets the root path of the game, for which the running game has the appropriate permission to
     *  read, write, and delete for that path and all paths enclosed within it.
     * The root path may be considered persitent by the game, and may be used to store save data
     */
    public Path getGameRoot();

    /**
     * Gets a ResourceSet where Paths are the key.
     * All Paths available to the ResourceSet are available to the game,
     *  and the game possesses read permissions to all such paths.
     * It is unspecified if any absolute paths can be used with this resource set,
     *   and if absolute paths can be read.
     */
    public ResourceSet<Path> getResources();

    /**
     * Obtains an instance of the SecurityManager that posseses the permissions active
     *  in the current execution environment.
     * Unless the in-place SecurityManager is replaced by a call to System.setSecurityManager,
     *  the return value is effectively the same (but not necessarily reference equal to)
     *  the current in-place SecurityManager obtained via a call to System.getSecurityManager.
     * As games normally cannot replace the in-place SecurityManager, this call is rarely required.
     */
    public SecurityManager getSandbox();

    /**
     * Gets a manipulable stream of all mods loaded within the running game
     */
    public Stream<ModInterface> getLoadedMods();

    /**
     * Loads the mod given by the provided URI.
     * URI shall be a valid LCUPM URI that accesses a sentry mod descriptor file.
     * The localcache, file, http, and https schemes are always supported (provided the appropriate permission is available).
     * It is implementation-defined if any other schemes are supported.
     * <br/>
     * The operation is not guaranteed to be supported during any given phase of execution.
     * If unsupported during the current phase, an UnsupportedOperationException is thrown.
     * <br/>
     * If the calling source has the LauncherPermission "lcupm.install.<i>scheme</i>" permission,
     *  no further permissions are checked (even if the scheme would access a resource otherwise protected
     *   by the Sandbox, such as a file on the filesystem).
     *  Otherwise, if the scheme accesses a permission protected resource, the scheme may check permissions on that resource.
     *  This may result in a SecurityException being thrown.
     *  The scheme localcache is never considered to access a permission protected resource, and as such will not throw a SecurityException.
     *
     * @apiNote The set of permissions required to access a resource with a non-standard scheme is unbounded.
     *  It is recommended that only the standard lcupm schemes are used (localcache, file, http, https, ftp, ftps, smb)
     *
     * @return An Interface to the mod being loaded.
     * @throws UnsupportedOperationException if the operation is not supported
     * @throws IllegalArgumentException if the uri scheme is not supported, the uri is malformed, cannot be accessed, or an LCUPM Error Occurs
     * @throws IllegalStateException if the current phase is {@link LoadingPhase#CLOSING} or later (including any Crash phase)
     * @throws SecurityException if the calling Code does not have LauncherPermission("lcupm.install.<i>scheme</i>"),
     *  the specified scheme accesses a permission protected resource (such as a file on the file system),
     *  and the System SecurityManager denies the caller access to that resource.
     */
    public ModInterface loadMod(URI uri) throws IllegalStateException, IllegalArgumentException, SecurityException;


    /**
     *
     * Loads an Instrumentation Agent from its lcupm descriptor pointed to by uri.
     * The limitations of accessing a resource via lcupm are the same as those specified by loadMod
     *
     * @throws UnsupportedOperationException If the operation is not supported
     * @throws IllegalArgumentException if the uri scheme is not supported, the uri is malformed, cannot be accessed, or an LCUPM Error Occurs
     * @throws SecurityException if the calling Code does not have LauncherPermission("lcupm.install.<i>scheme</i>"),
     *  the specified scheme accesses a permission protected resource (such as a file on the file system),
     *  and the System SecurityManager denies the caller access to that resource,
     *  or the System SecurityManager Denies the caller the LauncherPermission instrument
     */
    @ChecksPermission("LauncherPermission(instrument)")
    public default void loadAgent(URI uri) throws SecurityException, UnsupportedOperationException, IllegalArgumentException{
        throw new UnsupportedOperationException("loadAgent");
    }

    /**
     * Disables all loaded mods if possible.
     * This is effectively equivalent to getLoadedMods().forEach(ModInterface::disable),
     */
    public void disableMods();

    /**
     * Executes the given PrivilegedAction after granting (if permitted by the user),
     *  the permissions specified by "requested".
     * If the call does not throw an exception,
     *  the request is guaranteed to be repeatable (that is, subsequent requests for the same PermissionCollection,
     *   or any permissions implied by the PermissionCollection will not throw a SecurityException directly).
     */
    public <T> T privilegedExec(PrivilegedAction<T> r, PermissionCollection requested) throws SecurityException;
    /**
     * Executes the given PrivilegedAction after granting (if permitted by the user),
     *  the permissions specified by "requested".
     * If the call does not throw an exception,
     *  the request is guaranteed to be repeatable (that is, subsequent requests for the same Permission,
     *   or any permissions implied by the Permission will not throw a SecurityException directly).
     */
    public <T> T privilegedExec(PrivilegedAction<T> r, Permission requested) throws SecurityException;
    /**
     * Executes the given Runnable after granting (if permitted by the user),
     *  the permissions specified by "requested".
     * If the call does not throw an exception,
     *  the request is guaranteed to be repeatable (that is, subsequent requests for the same PermissionCollection,
     *   or any permissions implied by the PermissionCollection will not throw a SecurityException directly).
     */
    public void privilegedExec(Runnable r, PermissionCollection requested) throws SecurityException;
    /**
     * Executes the given Runnable after granting (if permitted by the user),
     *  the permissions specified by "requested".
     * If the call does not throw an exception,
     *  the request is guaranteed to be repeatable (that is, subsequent requests for the same Permission,
     *   or any permissions implied by the Permission will not throw a SecurityException directly).
     */
    public void privilegedExec(Runnable r, Permission requested) throws SecurityException;

    /**
     * Performs some action from the permissions implied by the Context.
     * @param ctx The Access Context, which is guaranteed to be valid if it is obtained from a getContext call from either the System Security Manager,
     *            or from the Sandbox
     * @throws SecurityException if the context is not a valid context
     */
    public <T> T doPrivileged(PrivilegedAction<T> r, Object ctx);

    /**
     * Performs some action from the permissions implied by the Context.
     * @param ctx The Access Context, which is guaranteed to be valid if it is obtained from a getContext call from either the System Security Manager,
     *            or from the Sandbox
     * @throws SecurityException if the context is not a valid context
     */
    public void doPrivileged(Runnable r, Object ctx);

    /**
     * Obtains a handle to some service of the launcher, returning Optional.empty()
     * if the requested service is unavailable
     */
    public <S extends LauncherService<S>> Optional<S> getService(Class<S> cl);
}
