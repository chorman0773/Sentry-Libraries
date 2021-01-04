package github.chorman0773.sentry.core.launch;

import github.chorman0773.sentry.launch.LaunchProvider;
import github.chorman0773.sentry.launch.LaunchProviderService;

import java.lang.instrument.ClassDefinition;
import java.util.stream.Stream;

public class CoreLaunchProviderService implements LaunchProviderService {
    @Override
    public LaunchProvider newLaunchProvider(Stream<ClassDefinition> defn) {

        return null;
    }
}
