package github.chorman0773.sentry.launch;

import java.security.Permission;
import java.util.Arrays;

/**
 * Launcher Permissions represent permissions used to access LauncherServices and the provided interface.
 */
public final class LauncherPermission extends Permission {
    private final String[] privilege;
    private static final String REGEX = "^\\*|[a-zA-Z_]*(\\.[a-zA-Z_]*)*(\\.\\*)?$";

    /**
     * Constructs a new LauncherPermission with the given privilege.
     * privilege MUST match the regex "^\*|[a-zA-Z_]*(\.[a-zA-Z_]*)*(\.\*)?$"
     */
    public LauncherPermission(String privilege) {
        super("launch");
        if(!privilege.matches(REGEX))
            throw new IllegalArgumentException("Invalid LauncherPermisison. Privilege must match ^\\*|[a-zA-Z_]*(\\.[a-zA-Z_]*)*(\\.\\*)?$");
        this.privilege = privilege.split("\\.");
    }

    /**
     * Checks if this permission "implies" another.
     * A LauncherPermission implies another LauncherPermission if and only if
     *  they require the same privilage, or this permission is a wildcard or constrained wildcard,
     *   and the root privilage is the root of permission.
     *  A LauncherPermission never implies a permission of a different type.
     *
     *  {@inheritDoc}
     */
    @Override
    public boolean implies(Permission permission) {
        if(!(permission instanceof LauncherPermission))
            return false;
        else{
            for(int i = 0;i<privilege.length;i++){
                if(((LauncherPermission) permission).privilege.length<=i)
                    return false;
                else if(privilege[i].equals("*"))
                    return true;
                else if(!privilege[i].equals(((LauncherPermission) permission).privilege[i]))
                    return false;
            }
            return privilege.length==((LauncherPermission) permission).privilege.length;
        }
    }

    /**
     * Checks if this permission equals another object.
     * A LauncherPermission a is equal to another LauncherPermission b if and only if
     *  a.implies(b) and b.implies(a).
     */
    @Override
    public boolean equals(Object o) {
        if( o ==null) return false;
        else if(o==this) return true;
        else if(o.getClass()!=LauncherPermission.class) return false;
        else
            return Arrays.equals(privilege, ((LauncherPermission) o).privilege);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(privilege);
    }

    /**
     * Returns the privilege associated with this LauncherPermission.
     * No other actions are specified by a LauncherPermisison.
     */
    @Override
    public String getActions() {
        return String.join(".",privilege);
    }

}
