package github.chorman0773.sentry.annotation;

import github.chorman0773.sentry.launch.LauncherInterface;
import github.chorman0773.sentry.launch.LoadingPhase;

import java.util.function.Consumer;

public @interface LoadingHook {
    LoadingPhase phase();
    Class<? extends Consumer<LauncherInterface>> hook();
}
