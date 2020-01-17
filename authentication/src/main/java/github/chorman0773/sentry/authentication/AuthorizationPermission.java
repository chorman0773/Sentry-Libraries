package github.chorman0773.sentry.authentication;

import java.security.Permission;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public final class AuthorizationPermission extends Permission {
    private final Set<String> scopes;
    public AuthorizationPermission(String name, Set<String> scopes) {
        super(name);
        this.scopes = scopes;
    }

    @Override
    public boolean implies(Permission permission) {
        if(permission instanceof AuthorizationPermission){
            if(getName().equals("*"))
                return scopes.containsAll(((AuthorizationPermission) permission).scopes);
            return getName().equals(permission.getName())&&scopes.containsAll(((AuthorizationPermission) permission).scopes);
        }else
            return false;
    }

    public Set<String> getScopes(){
        return Collections.unmodifiableSet(scopes);
    }

    @Override
    public boolean equals(Object o) {
        if(o==null) return false;
        else if(o==this) return true;
        else if(o.getClass()!=AuthorizationPermission.class) return false;
        else{
            return this.getName().equals(((AuthorizationPermission) o).getName())&&this.scopes.equals(((AuthorizationPermission) o).scopes);
        }
    }

    @Override
    public int hashCode() {
        return getName().hashCode()*31+scopes.hashCode();
    }

    @Override
    public String getActions() {
        return this.getName()+":["+scopes.stream().collect(Collectors.joining(","))+']';
    }
}
