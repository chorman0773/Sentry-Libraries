package github.chorman0773.sentry.core.service;

import github.chorman0773.sentry.launch.LauncherService;
import org.slf4j.Logger;

/**
 * Represents a Service to obtain an Slf4j logger from the launcher.
 * If present, the game may log actions here. 
 */
public interface LauncherLogService extends LauncherService<LauncherLogService> {
    /**
     * Obtains an instance of the SLF4J Logger obtained from the launcher
     */
    public Logger getLogService();
}
