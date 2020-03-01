package github.chorman0773.sentry.launcher.control;

import github.chorman0773.sentry.launch.GameLaunchArtifact;
import github.chorman0773.sentry.launch.LauncherContext;
import github.chorman0773.sentry.launch.LauncherInterface;
import github.lightningcreations.lcjei.service.EngineLookup;

import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.Objects;


/**
 * Constitutes A particular type of access to a profile.
 * A Profile may have a standard name (given by the user),
 *  or a special name.
 *  The special name "!" is a temporary profile,
 *  The special name "?" is the current profile,
 *  the special name "*" is all profiles.
 *
 * Profiles have read, write, launchgame, and delete permissions. There is also an "all" granting permission "*"
 */
public final class LauncherProfilePermission extends Permission {
    private final String action;

    private static String getCurrentProfileName(){
        LauncherInterface lint = LauncherContext.getLauncherContext().getLauncherInterface();
        ProfileAccessService serv = lint.getService(ProfileAccessService.class).get();
        Profile prof = AccessController.doPrivileged((PrivilegedAction<Profile>) serv::currentProfile);
        return prof.profileName();
    }

    public LauncherProfilePermission(String name,String action) {
        super(name);
        this.action = action;
    }

    @Override
    public boolean implies(Permission permission) {
        if(permission instanceof LauncherProfilePermission){
            String thisName = this.getName();
            String otherName = permission.getName();
            if(thisName.equals("*")||thisName.equals(otherName)||
                    (thisName.equals("?")&&otherName.equals(getCurrentProfileName())))
                return this.action.equals("*")||this.action.equals(((LauncherProfilePermission) permission).action);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LauncherProfilePermission that = (LauncherProfilePermission) o;
        return this.implies(that)&&that.implies(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(),action);
    }

    @Override
    public String getActions() {
        return action;
    }
}
