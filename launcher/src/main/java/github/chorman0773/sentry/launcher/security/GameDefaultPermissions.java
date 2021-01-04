package github.chorman0773.sentry.launcher.security;

import github.chorman0773.sentry.launch.LauncherPermission;
import github.chorman0773.sentry.launcher.control.LauncherProfilePermission;

import java.io.File;
import java.io.FilePermission;
import java.net.MalformedURLException;
import java.net.URLPermission;
import java.nio.file.Path;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.util.Enumeration;
import java.util.PropertyPermission;

public class GameDefaultPermissions{

    public static PermissionCollection buildDefaultSet(Path root){
        Permissions permissions = new Permissions();
        permissions.add(new PropertyPermission("*","read"));
        var scheme = root.toAbsolutePath().toUri().getScheme();
        switch(scheme){
            case "file":
                var path = root.toAbsolutePath().toFile()+File.pathSeparator+"-";
                permissions.add(new FilePermission(path,"read"));
                permissions.add(new FilePermission(path,"write"));
                permissions.add(new FilePermission(path,"delete"));
                permissions.add(new FilePermission(path,"readlink"));
            break;
            case "http":
            case "https":
            case "ftp":
                permissions.add(new URLPermission(root.toAbsolutePath().toUri().toString()));
            break;
            case "sentry":
                // Nothing needs to be done.
            break;
            default:
                throw new IllegalArgumentException("Unknown Scheme: "+scheme);

        }
        permissions.add(new LauncherProfilePermission("!!","*"));
        permissions.add(new LauncherPermission("launcher.services.access"));
        return permissions;
    }
}
