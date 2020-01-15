package github.chorman0773.sentry.generic;

import github.chorman0773.sentry.GameBasic;
import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.lightningcreations.lcjei.IGameInfo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public abstract class GenericGame extends GameBasic {
    private Thread tickThread;
    private int updateRate;
    private final AtomicBoolean running = new AtomicBoolean();
    private final AtomicBoolean pauseWhileDisabled = new AtomicBoolean(true);

    protected GenericGame(IGameInfo<GameLaunchArtifact> info) {
        super(info);
    }

    protected final void setPauseWhileDisabled(boolean pause){
        pauseWhileDisabled.setRelease(pause);
    }

    @Override
    public void start() {
        running.setRelease(true);
    }

    @Override
    public void stop() {
        running.setRelease(false);
    }

    protected abstract void tick();

    private void updateRunner(){
        while(this.shouldClose()){
            tick();
            LockSupport.park();
        }
    }

    @Override
    public void run() {
        tickThread = new Thread(this::updateRunner);
        tickThread.start();
        while(!this.shouldClose()){
            try {
                Thread.sleep(1000/updateRate);
                if(this.running.getAcquire()&&this.pauseWhileDisabled.getAcquire())
                    LockSupport.unpark(tickThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LockSupport.unpark(tickThread);
    }
}
