package github.chorman0773.sentry.launch;

import java.util.function.Consumer;

public interface ModInterface {
    public Object getModObject();
    public Class<?> getModClass();
    public void addLoadingHook(int phase, Consumer<ModInterface> mod);
    public void disable();
}
