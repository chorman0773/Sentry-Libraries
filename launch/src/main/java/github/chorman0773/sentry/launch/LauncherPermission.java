package github.chorman0773.sentry.launch;

import java.security.Permission;
import java.util.Arrays;

public final class LauncherPermission extends Permission {
    private final String[] privilege;
    public LauncherPermission(String privilege) {
        super("launch");
        this.privilege = privilege.split("\\.");
    }

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

    @Override
    public String getActions() {
        return String.join(".",privilege);
    }

}
