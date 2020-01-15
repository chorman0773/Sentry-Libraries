package github.chorman0773.sentry.core.service;

import github.chorman0773.sentry.launch.LauncherService;

import java.io.BufferedWriter;

public interface LauncherTerminal extends LauncherService<LauncherTerminal> {
    public BufferedWriter getTerminalWriter();
}
