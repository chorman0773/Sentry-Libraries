package github.chorman0773.sentry.annotation;

import github.chorman0773.sentry.launch.LauncherInterface;

import java.util.function.Consumer;

public @interface Game {
    public String gameId();
    public String uuid();
    public String version();
    public String publicKey() default "";

    public LoadClass[] loadClasses() default {};

    public Class<? extends Consumer<LauncherInterface>>[] loadingHooks() default {};
}
