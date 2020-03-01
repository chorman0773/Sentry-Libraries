package github.chorman0773.sentry.launcher.control;

import github.chorman0773.sentry.launch.marker.ChecksPermission;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface Profile {
    /**
     * Returns the path containing the root folder of the profile.
     */
    public Path profileRoot();

    /**
     * Gets the name of this profile.
     * If this is the temporary profile, returns !.
     * If this is the root profile, throws a SecurityException unconditionally.
     *
     * @throws SecurityException if this is the root profile (the root profile is never namable)
     */
    public String profileName();

    /**
     * Attempts to create a new GameEntry within this profile,
     *  given the URI of the Game Descriptor, and the entry name.
     *<br/>
     * The descriptor is fetched via lcupm. The same limitations as {@link github.chorman0773.sentry.launch.LauncherInterface#loadMod(URI)}
     *  apply to this call.
     */
    @ChecksPermission("LauncherProfilePermission({this.profileName()},write)")
    public GameEntry createEntry(URI descriptorURI,String name);

    /**
     * Returns a stream over all games in the profile
     */
    public Stream<GameEntry> games();
}
