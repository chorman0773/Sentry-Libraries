package github.chorman0773.sentry.launcher;

import github.chorman0773.sentry.GameCrash;

import java.util.function.Predicate;

public interface GameCrashHelper {
    public static <T> void crashIfFalse(Predicate<? super T> pred,T t,String s){
        if(!pred.test(t))
            throw new GameCrash(String.format("Predicate Test Failed: %s",s));
    }
}
