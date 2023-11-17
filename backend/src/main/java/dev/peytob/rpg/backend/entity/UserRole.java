package dev.peytob.rpg.backend.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {

    PLAYER("ROLE_PLAYER"),
    ADMIN_PLAYER("ROLE_ADMIN_PLAYER"),
    READER("ROLE_READER"),
    ADMIN_READER("ROLE_ADMIN_READER"),
    WRITER("ROLE_WRITER"),
    ADMIN_WRITER("ROLE_ADMIN_WRITER");

    private final GrantedAuthority grantedAuthority;

    UserRole(String securityRole) {
        this.grantedAuthority = new SimpleGrantedAuthority(securityRole);
    }

    public GrantedAuthority getGrantedAuthority() {
        return grantedAuthority;
    }
}
