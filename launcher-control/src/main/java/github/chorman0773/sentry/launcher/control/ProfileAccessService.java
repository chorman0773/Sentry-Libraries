package github.chorman0773.sentry.launcher.control;

import github.chorman0773.sentry.launch.LauncherService;
import github.chorman0773.sentry.launch.marker.ChecksPermission;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Launcher Service that provides access to Launcher Profiles
 */
public interface ProfileAccessService extends LauncherService<ProfileAccessService> {
    /**
     * Returns a Stream that reads all Profiles in the launcher, lazily.
     */
    @ChecksPermission("LauncherProfilePermission(*,read)")
    public Stream<Profile> readProfiles();

    /**
     * Obtains a handle with read access to the current profile.
     */
    @ChecksPermission("LauncherProfilePermission(?,read)")
    public Profile currentProfile();

    /**
     * Creates a new Profile with a given name, with write access
     * @param root the Path the profile is created in.
     * @return a handle to that profile
     */
    @ChecksPermission("LauncherPermission(profiles.create)")
    @ChecksPermission("LauncherProfilePermission({name},write)")
    public Profile createProfile(Path root, String name);

    /**
     * Obtains the root profile with read access.
     * The root profile is not accessible via a name, and other accesses cannot be obtained to it.
     * The Launcher itself is run in the root profile, as are Launcher Plugins.
     *
     * Because it is not possible to name this profile, it is not possible to launch any games within the profile.
     */
    @ChecksPermission("LauncherPermission(profiles.accessroot)")
    public Profile rootProfile();

    /**
     * Creates a profile that will be deleted when the calling game exits.
     * The Profile has write access by default.
     * Permission to create this profile is available by default to games
     */
    @ChecksPermission("LauncherProfilePermission(!,write)")
    public Profile temporaryProfile();
}
