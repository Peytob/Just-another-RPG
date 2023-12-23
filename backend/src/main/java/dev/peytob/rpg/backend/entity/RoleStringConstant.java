package dev.peytob.rpg.backend.entity;

/**
 * Usable roles constants for annotations. Already converted to {@link org.springframework.security.core.GrantedAuthority}
 * as usable enum in {@link UserRole}.
 */
public enum RoleStringConstant {;
    public static final String ROLE_PLAYER = "ROLE_PLAYER";
    public static final String ROLE_ADMIN_PLAYER = "ROLE_ADMIN_PLAYER";
    public static final String ROLE_READER = "ROLE_READER";
    public static final String ROLE_ADMIN_READER = "ROLE_ADMIN_READER";
    public static final String ROLE_WRITER = "ROLE_WRITER";
    public static final String ROLE_ADMIN_WRITER = "ROLE_ADMIN_WRITER";
}
