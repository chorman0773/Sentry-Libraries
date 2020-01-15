package github.chorman0773.sentry.core.service;

import github.chorman0773.sentry.launch.LauncherService;

import java.io.BufferedWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

public interface LauncherTerminal extends LauncherService<LauncherTerminal> {
    public PrintWriter getTerminalWriter();
    public PrintStream getTerminalStream();
}
