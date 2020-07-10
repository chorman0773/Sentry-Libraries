package github.chorman0773.sentry.launcher.bootstrap;

import github.chorman0773.sentry.GameBasic;
import github.chorman0773.sentry.annotation.Game;
import github.chorman0773.sentry.launcherd.LauncherD;

import java.nio.file.Path;
import java.rmi.RemoteException;
import java.security.Policy;

@Game(gameId = "Launcher", uuid = "00000000-0000-0000-0000-000000000000", version = "0.1")
public class Bootstrap extends GameBasic implements LauncherD {
    @Override
    protected void onInit() throws Throwable {


    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {

    }

    @Override
    public String currentProfileId() throws RemoteException {
        return null;
    }

    @Override
    public void focus() throws RemoteException {

    }

    @Override
    public void launch(Path[] roots) throws RemoteException {

    }

    @Override
    public void launchDebug(Path[] roots, Path[] agents) throws RemoteException {

    }
}
