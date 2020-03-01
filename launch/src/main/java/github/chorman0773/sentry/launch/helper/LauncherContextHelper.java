package github.chorman0773.sentry.launch.helper;

import github.chorman0773.sentry.launch.LauncherContext;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class LauncherContextHelper {
    public static final AtomicReference<Supplier<LauncherContext>> ctxHelper = new AtomicReference<>();
    public static void setLauncherContextAccesser(Supplier<LauncherContext> ctx){
        ctxHelper.setRelease(ctx);
    }
}
