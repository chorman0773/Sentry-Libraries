package github.chorman0773.sentry.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Game {
    public String gameId();
    public String uuid();
    public String version();
    public String publicKey() default "";

    public LoadClass[] loadClasses() default {};

    public LoadingHook[] loadingHooks() default {};
}
