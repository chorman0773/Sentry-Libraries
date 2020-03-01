package github.chorman0773.sentry.launch;

import github.lightningcreations.lcjei.IGameInfo;

/**
 * Represents the main launch artifact available to the game.
 */
public interface GameLaunchArtifact extends LaunchArtifact {
    public IGameInfo<GameLaunchArtifact> getInfo();
}
