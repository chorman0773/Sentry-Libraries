package github.chorman0773.sentry.launcher;


import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Optional;

public class StartLauncher {
    private static final Options opts = new Options()
            .addOption(Option.builder().longOpt("start-path")
                    .numberOfArgs(1).argName("path").desc("Starts the game designated by path").build())
            .addOption(Option.builder().longOpt("no-new-window")
                .desc("Prevents the startup of a new window for the Launcher. Errors if no launcher is already running").build())
            .addOption(Option.builder().longOpt("new-window")
                .desc("Forces a new launcher to spawn. If an existing window is open, the window will still open, but the deamon will be supressed.")
            .build());
    public static void main(String[] args) throws ParseException {
        var v = new DefaultParser().parse(opts,args);
        var startPath = v.hasOption("start-path")? Optional.of(v.getOptionValue("start-path")):Optional.empty();

    }
}
