package github.chorman0773.sentry.launch;

public interface LaunchArtifact {
    public void initialise(LauncherInterface lint,String[] args);
    public void start();
    public void init();
    public void stop();
    public void destroy();
}
