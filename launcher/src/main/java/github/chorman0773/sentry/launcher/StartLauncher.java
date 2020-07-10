package github.chorman0773.sentry.launcher;


import github.chorman0773.sentry.launcher.bootstrap.BootstrapLauncher;
import github.chorman0773.sentry.launcherd.LauncherD;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class StartLauncher {
    private static final Options opts = new Options()
            .addOption(Option.builder().longOpt("start-path")
                    .numberOfArgs(1).argName("path").desc("Starts the game designated by path").build())
            .addOption(Option.builder().longOpt("no-new-window")
                .desc("Prevents the startup of a new window for the Launcher. Errors if no launcher is already running").build())
            .addOption(Option.builder().longOpt("new-window")
                .desc("Forces a new launcher to spawn. If an existing window is open, the window will still open, but the deamon will be supressed.")
            .build());
    public static void main(String[] args) throws ParseException, RemoteException {
        var v = new DefaultParser().parse(opts,args);
        var startPath = (v.hasOption("start-path")? Optional.of(Stream.of(v.getOptionValue("start-path"))
                .map(s->s.split(":"))
                .flatMap(Arrays::stream)
                .map(Paths::get)
                .toArray(Path[]::new)): Optional.empty())
                ;

        var reg = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
        LauncherD daemon;
        try {
            daemon = (LauncherD) reg.lookup("github.chorman0773.sentry.launcherd.LauncherD");
            if(opts.hasOption("new-window")){
                daemon = new BootstrapLauncher(args);
            }
        } catch (RemoteException | NotBoundException e) {
            if(opts.hasOption("no-new-window")){
                System.err.println("Non-existent launcher window requested with --no-new-window");
                System.exit(1);
            }
            reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

        }

    }
}
