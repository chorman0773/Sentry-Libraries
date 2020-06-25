package github.chorman0773.sentry.generic;

import github.chorman0773.sentry.GameBasic;

import java.lang.invoke.VarHandle;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * Specialized version of GameBasic that performs some action
 *  at a regular rate.
 * This class manages calling the tick method at a specified rate.
 */
public abstract class GenericGame extends GameBasic {
    private Thread tickThread;
    private int updateRate;
    private Duration lastTickTime;
    private Duration totalTickTime;
    private long tickCount;
    private final AtomicBoolean running = new AtomicBoolean();
    private final AtomicBoolean pauseWhileDisabled = new AtomicBoolean(true);

    /**
     * @param rate the rate at which to call the {@link #tick()} method, in Hz. Rate shall not be 0 or negative, or exceed 1000
     */
    protected GenericGame(int rate) throws IllegalArgumentException {
        if(rate<=0||rate>1000)
            throw new IllegalArgumentException("rate shall not be 0 or negative, or exceed 1000");
        this.updateRate = rate;
        tickThread = new Thread(this::updateRunner);
    }

    /**
     * Used to disable or reenable pausing when the game is out of focus.
     *
     * Calls to this method from any thread (including the thread which calls {@link #tick()},
     *  do not introduce a data race.
     *
     * By default, the game pauses when it is out of focus.
     *
     * @implNote If the game is paused, then this call *synchronizes-with* attempts to resume the tick thread
     * @see #start()
     * @see #stop()
     */
    protected final void setPauseWhileDisabled(boolean pause){
        pauseWhileDisabled.setRelease(pause);
    }

    /**
     * If pauseWhileDisabled is set causes the game to resume ticking.
     *
     * Calls to this method do not introduce a data race.
     *
     * @implNote This call *synchronizes-with* attempts to resume the tick thread.
     * @apiNote This method is called automatically by the Sentry launcher whenever the window regains focus.
     * @see #setPauseWhileDisabled(boolean)
     */
    @Override
    public void start() {
        running.setRelease(true);
    }

    /**
     * If pauseWhileDisable is set, causes the game to stop ticking until {@link #start()} is called.
     *
     * Calls to this method do not introduce a data race.
     *
     * @implNote This call *synchronizes-with* attempts to resume the tick thread.
     * @apiNote This method is called automatically by the Sentry launcher whenever the window loses focus
     * @see #setPauseWhileDisabled(boolean)
     */
    @Override
    public void stop() {
        running.setRelease(false);
    }

    /**
     * This method is called by an unspecified thread up to rate times per second,
     *  while the game is not paused
     */
    protected abstract void tick();

    private void updateRunner(){
        while(this.shouldClose()){
            Instant tickBegin = Instant.now();
            tick();
            LockSupport.park();
            this.lastTickTime = Duration.between(tickBegin,Instant.now());
            this.totalTickTime = this.totalTickTime.plus(lastTickTime);
        }
    }

    @Override
    public void run() {
        tickThread.start();
        while(!this.shouldClose()){
            try {
                Thread.sleep(1000/updateRate); // noinspection
                if(this.running.getAcquire()&&this.pauseWhileDisabled.getAcquire())
                    LockSupport.unpark(tickThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LockSupport.unpark(tickThread);
    }
}
