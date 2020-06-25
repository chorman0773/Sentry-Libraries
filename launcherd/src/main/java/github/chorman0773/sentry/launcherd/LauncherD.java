package github.chorman0773.sentry.launcherd;

import java.nio.file.Path;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface LauncherD extends Remote {
    public String currentProfileId() throws RemoteException;
    public void focus() throws RemoteException;
    public void launch(Path[] roots) throws RemoteException;
    public void launchDebug(Path[] roots,Path[] agents) throws RemoteException;
}
