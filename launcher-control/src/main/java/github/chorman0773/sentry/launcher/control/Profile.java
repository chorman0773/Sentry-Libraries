package github.chorman0773.sentry.launcher.control;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface Profile {
    public Path profileRoot();
    public GameEntry createEntry(URI descriptorURI,String name);
    public Stream<GameEntry> games();
}
