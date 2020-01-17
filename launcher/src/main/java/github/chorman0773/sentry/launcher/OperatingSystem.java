package github.chorman0773.sentry.launcher;

public enum OperatingSystem {
    WINDOWS,
    MACOS,
    LINUX,
    BSD,
    OTHER;
    public static OperatingSystem getOs(){
        var name = System.getProperty("os.name");
        if (name.startsWith("Windows")) // We are on Windows,
            return WINDOWS;
        else if(name.startsWith("Mac OS X")||name.startsWith("macOS")||name.contains("OS X")||name.contains("Darwin"))
            return MACOS;
        else if(name.startsWith("Linux"))
            return LINUX;
        else if(name.contains("BSD"))
            return BSD;
        else
            return OTHER;
    }
}
