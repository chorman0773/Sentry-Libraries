package github.chorman0773.sentry.launcher.agent;


import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;


public class AgentMain {

    public static void premain(String[] args, Instrumentation instrument) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> cl = ClassLoader.getSystemClassLoader().loadClass("github.chorman0773.sentry.launcher.bootstrap.InitPathInstrument");
        VarHandle handle = MethodHandles.privateLookupIn(cl,MethodHandles.lookup()).findStaticVarHandle(cl,"instrument",Instrumentation.class);
        handle.set(instrument);
    }
}
