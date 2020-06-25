package github.chorman0773.sentry.launcher.security;

import java.security.*;

public class LauncherPolicy extends Policy {
    @Override
    public PermissionCollection getPermissions(CodeSource codesource) {

        return super.getPermissions(codesource);
    }

    @Override
    public PermissionCollection getPermissions(ProtectionDomain domain) {

        return super.getPermissions(domain);
    }

    @Override
    public boolean implies(ProtectionDomain domain, Permission permission) {
        return super.implies(domain, permission);
    }
}
