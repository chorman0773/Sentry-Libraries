package github.chorman0773.sentry.launcher.control;

import github.chorman0773.sentry.launch.LauncherInterface;

import java.net.URI;
import java.util.function.Consumer;

public interface GameHandle {
    public LauncherInterface boundLauncher();
    public void addLoadingHook(int phase, Consumer<LauncherInterface> hook);
    public void launch();
    public void installMod(URI uri);
}
