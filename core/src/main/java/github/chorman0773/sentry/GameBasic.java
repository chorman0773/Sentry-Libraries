package github.chorman0773.sentry;

import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.lightningcreations.lcjei.IGameInfo;

import java.awt.*;
import java.security.AccessControlContext;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * GameBasic is the root type of the Sentry Game Heirarchy,
 *  all standard sentry games should inherit from GameBasic
 */
public abstract class GameBasic extends Container implements GameLaunchArtifact, Runnable {
    private LauncherInterface lint;
    private String[] args;
    private Thread th;
    private final AccessControlContext ctx;
    private final IGameInfo<GameLaunchArtifact> info;
    private final AtomicBoolean close = new AtomicBoolean(false);

    protected GameBasic() {
        this.info = new GameDescriptor(this.getClass());
        ctx = new AccessControlContext(new java.security.ProtectionDomain[]{GameBasic.class.getProtectionDomain()});
        this.setPreferredSize(new Dimension(700,550));
    }

    @Override
    public final IGameInfo<GameLaunchArtifact> getInfo() {
        return info;
    }

    /**
     * Obtains the current Game LauncherInterface
     */
    public final LauncherInterface getLauncherInterface(){
        if(lint==null)
            throw new IllegalStateException("Game has not been initialized with an interface yet");
        return lint;
    }

    /**
     * Returns a copy of the game arguments, if any.
     * It is recommended that the caller cache the result
     */
    public final String[] getArgs(){
        return args.clone();
    }

    @Override
    public final void initialise(LauncherInterface lint, String[] args) {
        this.setSize(this.getPreferredSize());
        this.lint = lint;
        this.args = args.clone();
        lint.getCurrentDrawContainer().add(this);
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

    /**
     * Called when the game initializes (override optional method).
     * The default implementation does nothing.
     * @throws Throwable THe implementation may throw any exception. If an exception is thrown, the game crashes.
     */
    protected void onInit() throws Throwable{}

    /**
     * Checks if the game should shutdown.
     * Games should take notice of this as soon as possible and, once noticed,
     *  should complete there run methods quickly.
     */
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
        try {
            th.join();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Called when the game shutdowns (override optional method).
     * The default implementation has no effect
     * @throws Throwable The implementation can throw any exception. However, if an exception is thrown,
     *  the game thread may complete abruptly (this is considered a game crash).
     */
    protected void onDestroy() throws Throwable{}
}
