package github.chorman0773.sentry.launch.marker;

import java.lang.annotation.Documented;

/**
 * Indicates that the given method is sensitive to the Game it is running in,
 *  beyond basic security checks.
 */
@Documented
public @interface GameContextSensitive {
}
