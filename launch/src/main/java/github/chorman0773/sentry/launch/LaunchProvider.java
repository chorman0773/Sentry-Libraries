package github.chorman0773.sentry.launch;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Interface exposed by Sentry Launch providers to the Launcher to allow generic
 *  instantiation of Sentry Games
 */
public interface LaunchProvider {
    /**
     * Returns a Stream of all LaunchArtifacts provided by this LaunchProvider,
     *  except for the gameArtifact, if it exists
     */
    public Stream<LaunchArtifact> artifacts();

    /**
     * If this Launch Provider defines a GameLaunchArtifact,
     *  returns that artifact, otherwise returns an Empty Optional.
     */
    public Optional<GameLaunchArtifact> gameArtifact();
}
