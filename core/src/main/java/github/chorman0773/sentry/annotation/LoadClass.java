package github.chorman0773.sentry.annotation;

import java.util.function.Consumer;

public @interface LoadClass {
    public String load();
    public Class<? extends Consumer<String>>[] onFail() default {};
    public Class<? extends Consumer<Class<?>>>[] onSuccess() default {};
}
