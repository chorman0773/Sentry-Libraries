package github.chorman0773.sentry.test.helper;

import github.chorman0773.sentry.core.service.LauncherTerminal;

import java.io.BufferedWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

public class TestLauncherTerminal implements LauncherTerminal {
    public static TestLauncherTerminal TERM = new TestLauncherTerminal();
    @Override
    public PrintWriter getTerminalWriter() {
        return new PrintWriter(System.out);
    }

    @Override
    public PrintStream getTerminalStream() {
        return System.out;
    }
}
