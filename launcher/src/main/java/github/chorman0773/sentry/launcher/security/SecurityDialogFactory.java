package github.chorman0773.sentry.launcher.security;

import javax.swing.*;
import java.security.Permission;
import java.security.PermissionCollection;

public class SecurityDialogFactory {
    public void createSecurityDialog(Permission p, PermissionCollection grant, PermissionCollection deny){
        int v = JOptionPane.showConfirmDialog(null,String.format("%s(%s,%s)",p.getClass().getSimpleName(),p.getName(),p.getActions())
        ,"Grant Permission",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
        switch(v){
            case JOptionPane.YES_OPTION:
                grant.add(p);
                break;
            case JOptionPane.NO_OPTION:
                deny.add(p);
                // noinspection fallthrough
            case JOptionPane.CANCEL_OPTION:
            case JOptionPane.CLOSED_OPTION:
                throw new SecurityException();
        }
    }

}
