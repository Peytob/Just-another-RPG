package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "USERS")
@Getter
@Setter
public class User extends AbstractEntity {

    @ManyToMany
    @JoinTable(
        name="USERS_GROUPS",
        joinColumns = @JoinColumn(name="USER_ID", referencedColumnName="ID"),
        inverseJoinColumns = @JoinColumn(name="GROUP_ID", referencedColumnName="ID"))
    @Setter(AccessLevel.NONE)
    private Collection<Group> groups;

    @ManyToOne(optional = false)
    @JoinColumn(name = "REALM", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Realm realm;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IS_ACTIVATED", nullable = false)
    private boolean isActivated;
}
