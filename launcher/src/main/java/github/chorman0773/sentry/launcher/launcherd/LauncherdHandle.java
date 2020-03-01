package github.chorman0773.sentry.launcher.launcherd;

import java.net.URI;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LauncherdHandle extends Remote {
    public String currentProfileId() throws RemoteException;
    public void focus() throws RemoteException;
    public void launch(URI[] roots) throws RemoteException;
}
