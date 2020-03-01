package github.chorman0773.sentry.launch;

import java.lang.instrument.ClassDefinition;
import java.util.stream.Stream;

/**
 * Defines a Service (loaded by ServiceLoader), which can instantiate LaunchProviders given
 *  a stream of ClassDefinitions provided by the Launcher
 */
public interface LaunchProviderService {
    public LaunchProvider newLaunchProvider(Stream<ClassDefinition> defn);
}
