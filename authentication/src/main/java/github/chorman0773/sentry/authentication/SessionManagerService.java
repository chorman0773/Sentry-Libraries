package github.chorman0773.sentry.authentication;

import github.chorman0773.sentry.launch.LauncherService;

import java.util.Optional;

/**
 * Launcher Service for creating sessions,
 *  while handling authentication in the launcher.
 * Privileges usually are not required to utilise SessionManagerService,
 *  however the user is not required to allow a game to create a session.
 */
public interface SessionManagerService extends LauncherService<SessionManagerService> {
    /**
     * Attempts to create a session.
     * If a root session with a superset of these scopes already exists
     *  for the current game, derives a new session using "reduce" from that current session
     *  and returns it.
     * Otherwise, if a root launcher session exists,
     *  the user is prompted to issue a new root session with
     *  these scopes. If authorized, that session is returned.
     * Otherwise, the game is suspended and the current thread parked.
     *  The user is then prompted to authenticate for a root launcher session.
     *
     * If the user does not authenticate or authorize a new game session,
     *  an empty Optional is returned.
     *
     * If any scope requires elevation,
     *  the AuthorizationPermission("elevatedAuthenticate") is checked
     *   with the sandbox for the scopes. This may result in a SecurityException.
     *
     * This permission can only be granted to signed games.
     */
    public Optional<Session> createSession(String... scopes);
}
