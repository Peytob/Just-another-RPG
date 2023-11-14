package dev.peytob.rpg.backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Collection;

@Entity(name = "User")
@Table(name = "USERS")
@Builder
@Getter
@Setter
public class UserEntity extends AbstractEntity {

    @ElementCollection
    private Collection<UserRole> roles;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IS_BLOCKED", nullable = false)
    @Builder.Default
    private boolean isBlocked = false;

    @Column(name = "LAST_LOGIN_TIME", nullable = false)
    private Instant lastLoginAt;
}
