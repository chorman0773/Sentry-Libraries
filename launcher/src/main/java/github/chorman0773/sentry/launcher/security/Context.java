package github.chorman0773.sentry.launcher.security;

import github.chorman0773.sentry.launch.LauncherContext;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.chorman0773.sentry.launch.helper.__HiddenType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.security.PrivilegedAction;

class Context extends LauncherContext {

    private static final __HiddenType construct;
    private final GameThreadGroup group;
    private final LauncherInterface lint;
    static{
        try {
            Constructor<__HiddenType> hidden = __HiddenType.class.getDeclaredConstructor();
            hidden.setAccessible(true);
            construct = hidden.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new NoSuchMethodError("waitwhat");
        }
    }

    public static LauncherContext getGameContext(){
        ThreadGroup g = Thread.currentThread().getThreadGroup();
        if(g instanceof GameThreadGroup)
            return ((GameThreadGroup) g).getContext();
        else
            return null;
    }


    Context(GameThreadGroup g,LauncherInterface lint) {
        //noinspection deprecation
        super(construct);
        this.group = g;
        this.lint = lint;
    }

    @Override
    protected __HiddenType __hide() {
        return construct;
    }

    @Override
    public LauncherInterface getLauncherInterface() {
        return lint;
    }

    @Override
    public ThreadGroup getGameThreadGroup() {
        return group;
    }

    @Override
    public void doPrivilaged(Runnable r) {
        lint.doPrivileged(r,group.getSecurityContext());
    }

    @Override
    public <T> T doPrivilaged(PrivilegedAction<T> r) {
        return lint.doPrivileged(r,group.getSecurityContext());
    }
}
