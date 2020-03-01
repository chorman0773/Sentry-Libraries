package github.chorman0773.sentry.annotation;

import github.chorman0773.sentry.launch.LaunchArtifact;

import java.util.function.Consumer;

/**
 * Causes the class with the given name to loaded by the Sentry Launcher.
 *
 * Classes specified by LoadClass are loaded during INSTANTIATION phase of the game.
 * All classes loaded this way are indeterminately sequenced with any LoadingHook this phase,
 *  and the calls to {@link LaunchArtifact#initialise}.
 */
public @interface LoadClass {
    /**
     * Specifies the class to be loaded
     */
    public String load();

    /**
     * Species the handlers to run when the loading fails.
     * If the given class cannot be found (or initializing it results in an Error),
     *  each class specified here is instantiated as though by calling its default constructor,
     *   then the accept method is called with the name of the class (specified by load).
     *  Any exception thrown by either causes the game to crash.
     */
    public Class<? extends Consumer<String>>[] onFail() default {};
    /**
     * Species the handlers to run when the loading succeeds.
     * If loading the given class succeeds,
     *  each class specified here is instantiated as though by calling its default constructor,
     *   then the accept method is called with the loaded class.
     *  Any exception thrown by either causes the game to crash.
     */
    public Class<? extends Consumer<Class<?>>>[] onSuccess() default {};
}
