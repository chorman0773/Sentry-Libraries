package github.chorman0773.sentry.foreign.wrapper;

import java.awt.*;
import java.util.Optional;

/**
 * Base class of custom "wrapper" containers which can be unwrapped into some foreign graphics context.
 */
public abstract class ForeignContainer<Context> extends Container {
    private Graphics handle;
    private Context ctx;
    protected ForeignContainer(Graphics handle,Context ctx){
        this.handle = handle;
        this.ctx = ctx;
    }

    public <OtherContext> Optional<ForeignContainer<OtherContext>> checkContextType(Class<OtherContext> type){
        if(type.isInstance(ctx))
            //noinspection unchecked
            return Optional.of((ForeignContainer < OtherContext >)this);
        else
            return Optional.empty();
    }

    public Graphics getGraphics() {
        return handle;
    }

    public Context getWrappedContext(){
        return ctx;
    }
}
