package github.chorman0773.sentry.launch.marker;

import java.lang.annotation.*;

/**
 * Indicates that a package is a Sentry Core API.
 * The package is dynamically locked by the Sentry Launcher,
 *  checkPackageAccess will throw a SecurityException without the proper permission.
 * It is also illegal to define a package with this annotation without the proper permission.
 */
@Documented
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CoreApiPackage {
}
