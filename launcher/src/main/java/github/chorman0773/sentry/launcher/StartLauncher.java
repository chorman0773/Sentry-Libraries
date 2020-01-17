package github.chorman0773.sentry.launcher;

import java.nio.file.Path;

public class StartLauncher {
    public static void main(String[] args){
        var HOME = Path.of(System.getProperty("user.home"));
        var os = OperatingSystem.getOs();

    }
}
