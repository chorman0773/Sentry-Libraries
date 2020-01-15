package github.chorman0773.sentry.launch;

import github.lightningcreations.lcjei.IGameInfo;

public interface GameLaunchArtifact extends LaunchArtifact {
    public IGameInfo<GameLaunchArtifact> getInfo();
}
