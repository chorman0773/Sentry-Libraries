package github.chorman0773.sentry.foreign.lcjei;

import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.lightningcreations.lcjei.IEngineInterface;
import github.lightningcreations.lcjei.IGameInfo;

import java.util.UUID;

class WrappedGameDescriptor<ForeignGame> implements IGameInfo<GameLaunchArtifact> {
    private IGameInfo<ForeignGame> wrapped;
    WrappedGameDescriptor(IGameInfo<ForeignGame> wrapped){
        this.wrapped = wrapped;
    }
    @Override
    public Class<? extends GameLaunchArtifact> getGameClass() {
        return WrappedGame.class;
    }

    @Override
    public Class<? extends IEngineInterface<GameLaunchArtifact>> getEngineInterfaceClass() {
        return LauncherInterface.class;
    }

    @Override
    public String getName() {
        return wrapped.getName();
    }

    @Override
    public String getVersion() {
        return wrapped.getVersion();
    }

    @Override
    public UUID getGameId() {
        return wrapped.getGameId();
    }
}
