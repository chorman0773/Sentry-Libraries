package github.chorman0773.sentry.launcher.bootstrap;


import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.chorman0773.sentry.launcherd.LauncherD;
import github.chorman0773.sentry.test.TestLauncher;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.rmi.RemoteException;

public class BootstrapLauncher extends TestLauncher implements LauncherD {
    private Bootstrap b;
    private BootstrapLauncher(Bootstrap b,Path p,String[] args){
        super(b,p,args);
    }
    public BootstrapLauncher(String[] args) {
        this(new Bootstrap(), Path.of(System.getProperty("sentry.launcher.bootpath")), args);
    }

    public Bootstrap getBootstrap(){
        return b;
    }

    @Override
    public synchronized void initialize() throws IllegalStateException {
        try {
            EventQueue.invokeAndWait(()->{
                JFrame j = new JFrame("Sentry Launcher");

            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            if(e.getCause() instanceof IllegalStateException)
                throw (IllegalStateException)e.getCause();
            else
                throw new RuntimeException(e.getCause());
        }
    }

    @Override
    public String currentProfileId() throws RemoteException {
        return b.currentProfileId();
    }

    @Override
    public void focus() throws RemoteException {
        if(this.window==null)
            this.initialize();
        EventQueue.invokeLater(()->{
            this.window.setVisible(true);
            if(this.window instanceof Window)
                ((Window) this.window).toFront();
            this.window.repaint();
        });
        b.focus();
    }

    @Override
    public void launch(Path[] roots) throws RemoteException {
        focus();
        b.launch(roots);
    }

    @Override
    public void launchDebug(Path[] roots, Path[] agents) throws RemoteException {
        focus();
        b.launchDebug(roots,agents);
    }
}
