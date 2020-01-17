package github.chorman0773.sentry.foreign.lcjei;

import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.lightningcreations.lcjei.IEngineInterface;
import github.lightningcreations.lcjei.IGameInfo;

public class WrappedGame<ForeignGame> implements GameLaunchArtifact {
    private final IEngineInterface<ForeignGame> engine;
    private ForeignGame game;
    private WrappedGameDescriptor<ForeignGame> descriptor;
    private LauncherInterface lint;
    private String[] args;
    public WrappedGame(ForeignGame game, IEngineInterface<ForeignGame> engine,IGameInfo<ForeignGame> descriptor){
        this.game = game;
        this.engine = engine;
        this.descriptor = new WrappedGameDescriptor<>(descriptor);
    }
    @Override
    public IGameInfo<GameLaunchArtifact> getInfo() {
        return descriptor;
    }

    public ForeignGame getGame() {
        return game;
    }

    @Override
    public void initialise(LauncherInterface lint, String[] args) {
        this.lint = lint;
        this.args = args;
        engine.initialize(lint.getCurrentDrawContainer());
    }

    public String[] getArgs(){
        return args.clone();
    }

    public LauncherInterface getLauncherInterface(){
        return lint;
    }

    @Override
    public void start() {
        engine.resume();
    }

    @Override
    public void init() {
        engine.run();
        engine.suspend();
    }

    @Override
    public void stop() {
        engine.suspend();
    }

    @Override
    public void destroy() {
        engine.destroy();
    }
}
