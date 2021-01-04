package github.chorman0773.sentry.launcher;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum OperatingSystem {
    WINDOWS,
    MACOS,
    LINUX,
    BSD,
    PHANTOM,
    OTHER;

    private Path __cached_base_dir;

    private static OperatingSystem getOs0(){
        var name = System.getProperty("os.name");
        if (name.startsWith("Windows")) // We are on Windows,
            return WINDOWS;
        else if(name.startsWith("Mac OS X")||name.startsWith("macOS")||name.contains("OS X")||name.contains("Darwin"))
            return MACOS;
        else if(name.startsWith("Linux"))
            return LINUX;
        else if(name.contains("BSD"))
            return BSD;
        else if(name.contains("Phantom"))
            return PHANTOM;
        else
            return OTHER;
    }
    private static final OperatingSystem cached = getOs0();
    public static OperatingSystem getOs(){
        return cached;
    }

    public Path getBaseDir(){
        if(__cached_base_dir==null){
            Path home = Paths.get(System.getProperty("user.home"));
            switch(this){
                case WINDOWS:
                    home = home.resolve(Paths.get("AppData","roaming",".sentry"));
                break;
                case MACOS:
                    home = home.resolve(Paths.get("Library","Application Support","Sentry"));
                break;
                case LINUX:
                case BSD:
                case PHANTOM:
                    home = home.resolve(Paths.get(".local","share","sentry"));
                break;
                case OTHER:
                    home = home.resolve(Paths.get(".sentry"));
                break;
                default:
                    assert false;
                break;
            }
            this.__cached_base_dir = home;
        }
        return __cached_base_dir;
    }


}
