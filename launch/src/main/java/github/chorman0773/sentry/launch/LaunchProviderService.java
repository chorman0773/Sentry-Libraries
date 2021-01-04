package github.chorman0773.sentry.launch;

import java.lang.instrument.ClassDefinition;
import java.util.stream.Stream;

/**
 * Defines a Service (loaded by ServiceLoader), which can instantiate LaunchProviders given
 *  a stream of ClassDefinitions provided by the Launcher
 */
public interface LaunchProviderService {
    /**
     * Instantiates a launch provider given a stream of class definitions
     * @param defn A stream of Class definitions. Note that the classes are not guaranteed to be initialized
     * @return A new Launch Provider instantiated by the service
     */
    public LaunchProvider newLaunchProvider(Stream<ClassDefinition> defn);
}
