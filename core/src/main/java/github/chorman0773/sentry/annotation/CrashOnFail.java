package github.chorman0773.sentry.annotation;

import github.chorman0773.sentry.GameCrash;

import java.util.function.Consumer;

/**
 * A class for use in LoadClass, that unconditionally crashes on failure.
 */
public class CrashOnFail implements Consumer<String> {

    @Override
    public void accept(String s) {
        throw new GameCrash(String.format("Failed to load class: %s",s));
    }
}
