module github.chorman0773.sentry.core{
    requires transitive github.chorman0773.sentry.launch;
    requires transitive java.desktop;
    requires transitive org.slf4j;
    requires org.objectweb.asm;
    requires java.instrument;
    exports github.chorman0773.sentry;
    exports github.chorman0773.sentry.generic;
    exports github.chorman0773.sentry.annotation;
    exports github.chorman0773.sentry.core.service;

    provides github.chorman0773.sentry.launch.LaunchProviderService with github.chorman0773.sentry.core.launch.CoreLaunchProviderService;
}
