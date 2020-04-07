module github.chorman0773.sentry.launcher {
    requires github.chorman0773.sentry.core;
    requires github.chorman0773.sentry.launch;
    requires github.chorman0773.sentry.launcher.control;
    requires github.chorman0773.sentry.foreign;
    requires github.chorman0773.sentry.test;
    requires github.lightningcreations.lcjei;
    requires org.objectweb.asm;
    requires org.objectweb.asm.commons;
    requires org.objectweb.asm.tree;
    requires org.objectweb.asm.util;
    requires java.rmi;
    requires java.instrument;
    requires commons.cli;
    opens github.chorman0773.sentry.launcher.bootstrap to github.chorman0773.sentry.launcher.agent;
}