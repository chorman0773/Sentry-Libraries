package github.chorman0773.sentry.launcher.control;

import github.chorman0773.sentry.launch.LauncherInterface;
import github.chorman0773.sentry.launch.LauncherService;
import github.chorman0773.sentry.launch.LoadingPhase;

import java.util.function.Consumer;

public interface LaunchHooksService extends LauncherService<LaunchHooksService> {
    public void registerLaunchHook(Consumer<LauncherInterface> hook, LoadingPhase phase);
}
