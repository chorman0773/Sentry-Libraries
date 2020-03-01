package github.chorman0773.sentry.launch.marker;

import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR})
@Documented
public @interface ChecksAll {
    ChecksPermission[] value();
}
