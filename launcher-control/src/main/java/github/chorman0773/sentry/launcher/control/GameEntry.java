package github.chorman0773.sentry.launcher.control;

import github.chorman0773.sentry.launch.marker.ChecksPermission;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface GameEntry {
    /**
     * Gets the name of the game entry
     */
    public String getName();

    /**
     * Gets the root path to the Game Entry
     */
    public Path root();

    /**
     * Prepares a new handle for launching the game.
     */
    @ChecksPermission("LauncherProfilePermission({this.owningProfile().getName()),launchgame)")
    @ChecksPermission("LauncherPermission(launchgame)")
    public GameHandle prepareLaunch();

    /**
     * Gets the URI where the game descriptor is located.
     */
    public URI getGameDescriptor();

    /**
     * Obtains a stream over the URI where the mod descriptor for each mod attached to the entry is located.
     */
    public Stream<URI> mods();

    /**
     * Obtains the profile where the GameEntry is created.
     */
    public Profile owningProfile();
}
