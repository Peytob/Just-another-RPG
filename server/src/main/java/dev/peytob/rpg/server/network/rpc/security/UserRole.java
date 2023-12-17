package dev.peytob.rpg.server.network.rpc.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public enum UserRole {

    PLAYER("ROLE_PLAYER"),
    ADMIN_PLAYER("ROLE_ADMIN_PLAYER"),
    READER("ROLE_READER"),
    ADMIN_READER("ROLE_ADMIN_READER"),
    WRITER("ROLE_WRITER"),
    ADMIN_WRITER("ROLE_ADMIN_WRITER");

    public static Collection<? extends GrantedAuthority> toSecurityRoles(Collection<UserRole> roles) {
        return roles.stream().map(UserRole::getGrantedAuthority).toList();
    }

    private final GrantedAuthority grantedAuthority;

    UserRole(String securityRole) {
        this.grantedAuthority = new SimpleGrantedAuthority(securityRole);
    }

    public GrantedAuthority getGrantedAuthority() {
        return grantedAuthority;
    }
}
