package github.chorman0773.sentry.core.launch;

import github.chorman0773.sentry.GameCrash;
import github.chorman0773.sentry.launch.LaunchArtifact;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.chorman0773.sentry.launch.LoadingPhase;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Consumer;

public class LoadingHookArtifact implements LaunchArtifact {
    private LoadingPhase phase;
    private String name;
    private Class<? extends Consumer<LauncherInterface>> cl;
    private Object captured;
    private LauncherInterface lint;

    public LoadingHookArtifact(LoadingPhase phase,String name){
        this.phase = phase;
        this.name = name;
    }

    @Override
    public void initialise(LauncherInterface lint, String[] args) {
        this.lint = lint;
        this.captured = lint.getSandbox().getSecurityContext();
        this.cl =  lint.doPrivileged(()-> {
            try {
                Class<?> cl = Class.forName(name);
                if(!cl.isAssignableFrom(Consumer.class))
                    throw new IncompatibleClassChangeError("Invalid LoadingHook class");
                //noinspection unchecked
                return (Class<? extends Consumer<LauncherInterface>>)cl;
            } catch (ClassNotFoundException e) {
                throw new GameCrash(e);
            }
        },captured);


    }

    @Override
    public void start() {

    }

    @Override
    public void init() {

    }

    private void callHook() {
        try {
            @SuppressWarnings("unchecked") Consumer<LauncherInterface> consumer = (Consumer<LauncherInterface>) MethodHandles.publicLookup().findConstructor(this.cl, MethodType.methodType(void.class))
                .invokeExact();
            consumer.accept(this.lint);
        }catch(RuntimeException | Error e){
            throw e;
        } catch (Throwable e) {
            throw new GameCrash(e);
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }
}
