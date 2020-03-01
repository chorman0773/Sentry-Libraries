package github.chorman0773.sentry.annotation;

import java.lang.annotation.*;

/**
 * The annotation specifying the main entry point to a Sentry Game.
 * The annotated type must extend GameBasic
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Game {
    /**
     * Specifies the string id of the game. Must be a valid java identifier
     */
    public String gameId();

    /**
     * Specifies the uuid of the game. Must be in the a valid UUID, that is not enclosed in braces
     */
    public String uuid();

    /**
     * Specifies the version of the game, in an unspecified format and versioning scheme
     */
    public String version();

    /**
     * Specifies the public key used to sign the game.
     */
    public String publicKey() default "";

    /**
     * Specifies classes to load during launch
     */
    public LoadClass[] loadClasses() default {};

    /**
     * Specifies hooks to run during particular phases of execution
     */
    public LoadingHook[] loadingHooks() default {};
}
