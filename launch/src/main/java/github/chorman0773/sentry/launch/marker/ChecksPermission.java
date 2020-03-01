package github.chorman0773.sentry.launch.marker;

import java.lang.annotation.*;

/**
 * Indicates that a given method or constructor checks a permission
 *  with a given permission Specification.
 * ChecksPermission implies that the permission is checked with the currently installed Sandbox,
 *  and that a SecurityException is thrown if the permission is not available.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR})
@Repeatable(ChecksAll.class)
public @interface ChecksPermission {
    /**
     * The permission Specification.
     * Permission Specifications have the form:
     * <i>class</i>(<i>[name,](opt)</i><i>action</i>), or simply <i>action</i> (for defaulting to RuntimePermission)
     */
    public String value();
}
