package github.chorman0773.sentry.launcher.bootstrap;


import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.chorman0773.sentry.test.TestLauncher;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

public class BootstrapLauncher extends TestLauncher {
    private Bootstrap b;
    private BootstrapLauncher(Bootstrap b,Path p,String[] args){
        super(b,p,args);
    }
    public BootstrapLauncher(String[] args) {
        this(new Bootstrap(), Path.of(System.getProperty("sentry.launcher.bootpath")), args);
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
}
