package github.chorman0773.sentry.launcher.security;

import java.nio.file.Path;
import java.security.AccessController;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.util.Set;

public class GameSecurityContext {
    private final PermissionCollection grant;
    private final PermissionCollection denied;
    public GameSecurityContext(Path root){
        grant = GameDefaultPermissions.buildDefaultSet(root);
        denied = new Permissions();
    }

    public void checkGrant(Permission p){
        if(denied.implies(p))
            AccessController.checkPermission(p);
        else if(!grant.implies(p)){

        }
    }
}
