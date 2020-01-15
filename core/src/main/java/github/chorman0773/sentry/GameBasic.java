package github.chorman0773.sentry;

import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.lightningcreations.lcjei.IGameInfo;

import java.awt.*;
import java.security.AccessControlContext;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class GameBasic extends Container implements GameLaunchArtifact, Runnable {
    private LauncherInterface lint;
    private String[] args;
    private Thread th;
    private final AccessControlContext ctx;
    private final IGameInfo<GameLaunchArtifact> info;
    private final AtomicBoolean close = new AtomicBoolean(false);

    protected GameBasic(IGameInfo<GameLaunchArtifact> info) {
        this.info = info;
        ctx = new AccessControlContext(new java.security.ProtectionDomain[]{GameBasic.class.getProtectionDomain()});
    }

    @Override
    public final IGameInfo<GameLaunchArtifact> getInfo() {
        return info;
    }

    public final LauncherInterface getLauncherInterface(){
        if(lint==null)
            throw new IllegalStateException("Game has not been initialized with an interface yet");
        return lint;
    }

    @Override
    public final void initialise(LauncherInterface lint, String[] args) {
        this.lint = lint;
        this.args = args.clone();
    }

    @Override
    public final void init() {
        try{
            onInit();
        }catch(Throwable t){
            throw new GameCrash("Fatal Error during startup",t);
        }
        th = new Thread(this);
        th.start();
    }

    public final void close(){
        lint.doPrivileged(()->lint.close(),ctx);
    }

    protected void onInit() throws Throwable{}

    protected final boolean shouldClose(){
        return this.close.getAcquire();
    }

    @Override
    public final void destroy() {
        try{
            onDestroy();
        }catch(Throwable t){
            throw new GameCrash("Fatal Error during shutdown",t);
        }finally {
            this.close.setRelease(true);
            th.interrupt();
        }
    }
    protected void onDestroy() throws Throwable{}
}
