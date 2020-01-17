module github.chorman0773.sentry.authentication {
    requires transitive github.chorman0773.sentry.launch;
    requires github.lightningcreations.lcjei;
    requires java.desktop;
    requires java.instrument;
    requires java.net.http;
    requires com.google.gson;

    exports github.chorman0773.sentry.authentication;
    exports github.chorman0773.sentry.authentication.fs;
}