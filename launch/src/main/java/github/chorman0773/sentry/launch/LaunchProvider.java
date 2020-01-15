package github.chorman0773.sentry.launch;

import java.util.Optional;
import java.util.stream.Stream;

public interface LaunchProvider {
    public Stream<LaunchArtifact> artifacts();
    public Optional<GameLaunchArtifact> gameArtifact();
}
