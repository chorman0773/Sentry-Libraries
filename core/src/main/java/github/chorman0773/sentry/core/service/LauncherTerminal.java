package github.chorman0773.sentry.core.service;

import github.chorman0773.sentry.launch.LauncherService;

import java.io.BufferedWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Launcher Service that wraps a terminal that can be written to
 */
public interface LauncherTerminal extends LauncherService<LauncherTerminal> {
    /**
     * A PrintWriter that writes to the terminal
     */
    public PrintWriter getTerminalWriter();
    /**
     * A PrintStream that writes to the terminal
     */
    public PrintStream getTerminalStream();
}
