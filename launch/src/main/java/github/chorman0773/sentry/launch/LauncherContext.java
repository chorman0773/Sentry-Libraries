package github.chorman0773.sentry.launch;

import github.chorman0773.sentry.launch.helper.__HiddenType;
import github.chorman0773.sentry.launch.helper.LauncherContextHelper;
import github.chorman0773.sentry.launch.marker.GameContextSensitive;

import java.util.Objects;

/**
 * Class available to obtain an instance of the Current Launcher Interface,
 *  where one may not be available.
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

}
