package github.chorman0773.sentry;

import github.chorman0773.sentry.annotation.Game;
import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.lightningcreations.lcjei.IEngineInterface;
import github.lightningcreations.lcjei.IGameInfo;

import java.util.UUID;

/**
 * Represents a Game Descriptor derived from a Game's annotation.
 */
public final class GameDescriptor implements IGameInfo<GameLaunchArtifact> {
    private final Class<? extends GameBasic> gameClass;
    private final Game gameAnnotation;

    GameDescriptor(Class<? extends GameBasic> gameClass){
        this.gameClass = gameClass;
        this.gameAnnotation = gameClass.getAnnotation(Game.class);
    }

    @Override
    public Class<? extends GameLaunchArtifact> getGameClass() {
        return gameClass;
    }

    @Override
    public Class<? extends IEngineInterface<GameLaunchArtifact>> getEngineInterfaceClass() {
        return LauncherInterface.class;
    }

    @Override
    public String getName() {
        return gameAnnotation.gameId();
    }

    @Override
    public String getVersion() {
        return gameAnnotation.version();
    }

    @Override
    public UUID getGameId() {
        return UUID.fromString(gameAnnotation.uuid());
    }
}
