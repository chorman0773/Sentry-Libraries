package github.chorman0773.sentry.launcher.control;

import java.awt.*;
import java.net.URI;
import java.nio.file.Path;

public interface GameEntry {
    public String getName();
    public Path root();
    public GameHandle prepareLaunch();
    public URI getGameDescriptor();
    public URI mods();
}
