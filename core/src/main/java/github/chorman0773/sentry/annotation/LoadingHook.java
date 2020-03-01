package github.chorman0773.sentry.annotation;

import github.chorman0773.sentry.launch.LauncherInterface;
import github.chorman0773.sentry.launch.LoadingPhase;

import java.util.function.Consumer;

/**
 * Defines a handle to execute during a particular phase
 *
 * The hook is run during the specified phase,
 *  and is indeterminately sequenced will all other actions that occur during that phase.
 */
public @interface LoadingHook {
    /**
     * The phase the hook is executed during
     */
    LoadingPhase phase();

    /**
     * The class specifying the hook to execute.
     *  The class is instantiated as though by its Default Constructor,
     *  then its accept method is called with an interface to the Launcher.
     */
    Class<? extends Consumer<LauncherInterface>> hook();
}
