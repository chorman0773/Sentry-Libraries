package github.chorman0773.sentry.launcher.security;

import github.chorman0773.sentry.launch.LauncherInterface;

import java.nio.file.Path;

public class GameThreadGroup extends ThreadGroup {
    private GameSecurityContext ctx;
    private LauncherInterface lint;
    public GameThreadGroup(String name, Path root) {
        super(name);
        ctx = new GameSecurityContext(root);
    }

    public GameThreadGroup(ThreadGroup parent, String name, Path root) {
        super(parent, name);
        ctx = new GameSecurityContext(root);
    }
}
