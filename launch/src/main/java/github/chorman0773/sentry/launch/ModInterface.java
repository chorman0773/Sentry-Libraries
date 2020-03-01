package github.chorman0773.sentry.launch;

import java.util.function.Consumer;

/**
 * Represents an interface to a Mod loaded and potentially running inside the game
 * ModInterfaces provide a limited interface
 */
public interface ModInterface {
    /**
     * Obtains the mod object, if it exists.
     */
    public Object getModObject();

    /**
     * Obtains the class that defines the mod
     */
    public Class<?> getModClass();

    /**
     * Registers a hook to be called during a loading phase.
     * If this call occurs after the specified Phase, it has no effect.
     */
    public void addLoadingHook(LoadingPhase phase, Consumer<ModInterface> mod);

    /**
     * Disables the mod,
     *  preventing any further startup code from being run.
     * Note that the mod isn't unloaded, and any code that has already run is not affected.
     * After this code, the reference to the mod object isn't kept.
     * This may cause {@link #getModObject()} to return null if no other references exist.
     */
    public void disable();
}
