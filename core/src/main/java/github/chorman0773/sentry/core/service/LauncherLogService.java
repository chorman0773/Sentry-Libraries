package github.chorman0773.sentry.core.service;

import github.chorman0773.sentry.launch.LauncherService;
import org.slf4j.Logger;

public interface LauncherLogService extends LauncherService<LauncherLogService> {
    public Logger getLogService();
}
