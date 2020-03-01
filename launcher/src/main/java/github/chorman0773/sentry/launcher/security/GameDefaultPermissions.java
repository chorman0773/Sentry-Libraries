package github.chorman0773.sentry.launcher.security;

import github.chorman0773.sentry.launch.LauncherPermission;
import github.chorman0773.sentry.launcher.control.LauncherProfilePermission;

import java.io.File;
import java.io.FilePermission;
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
        File f = root.toAbsolutePath().toFile();
        String path = f.toString()+"/-";
        permissions.add(new FilePermission(path,"read"));
        permissions.add(new FilePermission(path,"write"));
        permissions.add(new FilePermission(path,"readlink"));
        permissions.add(new FilePermission(path,"delete"));
        permissions.add(new LauncherProfilePermission("!!","*"));
        permissions.add(new LauncherPermission("launcher.services.access"));
        return permissions;
    }
}
