package github.chorman0773.sentry.launch;

import java.lang.instrument.ClassDefinition;
import java.util.stream.Stream;

public interface LaunchProviderService {
    public LaunchProvider newLaunchProvider(Stream<ClassDefinition> defn);
}
