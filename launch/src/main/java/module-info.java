/**
 * Root Module for Sentry Games
 */
module github.chorman0773.sentry.launch{
    exports github.chorman0773.sentry.launch;
    exports github.chorman0773.sentry.launch.marker;
    requires github.lightningcreations.lcjei;
    requires java.desktop;
    requires java.instrument;
    opens github.chorman0773.sentry.launch.helper to github.chorman0773.sentry.launcher;
    exports github.chorman0773.sentry.launch.helper to github.chorman0773.sentry.launcher.control, github.chorman0773.sentry.launcher;
}