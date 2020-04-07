package github.chorman0773.sentry.launch;

import github.chorman0773.sentry.launch.helper.__HiddenType;
import github.chorman0773.sentry.launch.helper.LauncherContextHelper;
import github.chorman0773.sentry.launch.marker.GameContextSensitive;

import java.security.PrivilegedAction;
import java.util.Objects;

/**
 * Class available to obtain an instance of the Current Launcher Interface,
 *  where one may not be available.
 *
 * A LauncherContext is a snapshot of the current context.
 * If shared with a different context,
 *  the
 *
 */
public abstract class LauncherContext {
    @GameContextSensitive
    public static LauncherContext getLauncherContext(){
        return LauncherContextHelper.ctxHelper.get().get();
    }
    // Cannot be overriden outside of the launcher

    /**
     * @hidden
     * @deprecated Not intended to be exported to consumers
     */
    protected LauncherContext(__HiddenType type){
        Objects.requireNonNull(type);
    }

    /**
     * @hidden
     * @deprecated
     */
    protected abstract __HiddenType __hide();

    public abstract LauncherInterface getLauncherInterface();

    public abstract ThreadGroup getGameThreadGroup();

    public abstract void doPrivilaged(Runnable r);
    public abstract <T> T doPrivilaged(PrivilegedAction<T> r);

}
