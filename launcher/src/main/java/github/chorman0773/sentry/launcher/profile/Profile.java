package github.chorman0773.sentry.launcher.profile;

import github.chorman0773.sentry.launcher.control.GameEntry;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Profile implements github.chorman0773.sentry.launcher.control.Profile {
    @Override
    public Path profileRoot() {
        return null;
    }

    @Override
    public String profileName() {
        return null;
    }

    @Override
    public GameEntry createEntry(URI descriptorURI, String name) {
        return null;
    }

    @Override
    public Stream<GameEntry> games() {
        return null;
    }
}
