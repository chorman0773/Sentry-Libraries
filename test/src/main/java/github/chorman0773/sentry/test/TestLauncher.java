package github.chorman0773.sentry.test;

import github.chorman0773.sentry.core.service.LauncherTerminal;
import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.chorman0773.sentry.launch.LauncherService;
import github.chorman0773.sentry.launch.ModInterface;
import github.chorman0773.sentry.test.helper.PathResourceSet;
import github.chorman0773.sentry.test.helper.TestLauncherTerminal;
import github.lightningcreations.lcjei.IGameInfo;
import github.lightningcreations.lcjei.resources.ResourceSet;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.nio.file.Path;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PermissionCollection;
import java.security.PrivilegedAction;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class TestLauncher implements LauncherInterface {
    private final String[] args;
    private final AtomicBoolean initialized = new AtomicBoolean();
    private final AtomicBoolean running = new AtomicBoolean();
    private final Path root;
    private Container window;
    private Frame f;
    private final GameLaunchArtifact game;
    private final ResourceSet<Path> resources;

    public TestLauncher(GameLaunchArtifact launch,Path root,String[] args){
        this.game = launch;
        this.root = root;
        this.args = args;
        this.resources = new PathResourceSet(root);
    }

    @Override
    public synchronized void close() throws IllegalStateException {
        if(!initialized.get())
            throw new IllegalStateException("Launcher not initialized");
        if(!(window instanceof Window))
            throw new IllegalStateException("Launcher must be run in Windowed mode to use #close()");
        destroy();
        f.setVisible(false);
        f.dispose();
        f = null;
    }

    @Override
    public synchronized boolean initialize(Container container) throws IllegalStateException {
        if(initialized.get())
            throw new IllegalStateException("Launcher is already initialized");
        this.window = container;
        game.initialise(this,args);
        return true;
    }

    @Override
    public synchronized void initialize() throws IllegalStateException {
        if(initialized.get())
            throw new IllegalStateException("Launcher is already initialized");
        if(f==null)
            f = new Frame(game.getInfo().getName()+" "+game.getInfo().getVersion());
        initialize(f);
        f.pack();
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                synchronized(TestLauncher.this) {
                    if (initialized.get())
                        destroy();
                    f = null;
                }
            }
        });
        f.setVisible(true);
    }

    @Override
    public synchronized void destroy() throws IllegalStateException {
        if(!initialized.get())
            throw new IllegalStateException("Game is not initialized");
        if(this.running.get())
            game.stop();
        game.destroy();
    }

    @Override
    public synchronized void run() throws IllegalStateException {
        if(!initialized.get())
            throw new IllegalStateException("Game already running");
        game.init();
        game.start();
        this.running.set(true);
    }

    @Override
    public Container getCurrentDrawContainer() {
        return this.window;
    }

    @Override
    public GameLaunchArtifact getGameObject() {
        return game;
    }

    @Override
    public IGameInfo<GameLaunchArtifact> getGameInfo() {
        return game.getInfo();
    }

    @Override
    public synchronized void suspend() throws IllegalStateException {
        if(!this.running.get())
            throw new IllegalStateException("Game is already suspended");
        game.stop();
        this.running.set(true);
    }

    @Override
    public synchronized void resume() throws IllegalStateException {
        if(this.running.get())
            throw new IllegalStateException("Game is not suspended");
    }

    @Override
    public Path getGameRoot() {
        return this.root;
    }

    @Override
    public ResourceSet<Path> getResources() {
        return resources;
    }

    @Override
    public SecurityManager getSandbox() {
        return System.getSecurityManager();
    }

    @Override
    public Stream<ModInterface> getLoadedMods() {
        return Stream.empty();
    }

    @Override
    public ModInterface loadMod(URI uri) throws IllegalStateException {
        return null;
    }

    @Override
    public void disableMods() {

    }

    @Override
    public <T> T privilegedExec(PrivilegedAction<T> privilegedAction, PermissionCollection permissionCollection) throws SecurityException {
        return AccessController.doPrivileged(privilegedAction);
    }

    @Override
    public void doPrivileged(Runnable runnable, AccessControlContext ctx) {
        AccessController.doPrivileged((PrivilegedAction<Void>) ()->{runnable.run();return null;},ctx);
    }

    @Override
    public <S extends LauncherService<S>> Optional<S> getService(Class<S> aClass) {
        if(aClass== LauncherTerminal.class)
            //noinspection unchecked
            return Optional.of((S)TestLauncherTerminal.TERM);
        else
            return Optional.empty();
    }
}
