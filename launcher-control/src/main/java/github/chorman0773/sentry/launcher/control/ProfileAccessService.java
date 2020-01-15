package github.chorman0773.sentry.launcher.control;

import github.chorman0773.sentry.launch.LauncherService;

import java.net.URI;
import java.util.stream.Stream;

public interface ProfileAccessService extends LauncherService<ProfileAccessService> {
    public Stream<Profile> readProfiles();
    public Profile currentProfile();
    public Profile createProfile(URI root);
    public Profile rootProfile();
    public Profile temporaryProfile();
}
