module github.chorman0773.sentry.core{
    requires transitive github.chorman0773.sentry.launch;
    requires transitive java.desktop;
    requires java.instrument; //provide for launch
    requires github.lightningcreations.lcjei; //provide for launch
    requires transitive org.slf4j;
}