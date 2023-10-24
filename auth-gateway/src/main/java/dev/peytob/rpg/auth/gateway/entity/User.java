package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "USERS")
public class User extends AbstractEntity {

    @ManyToMany
    @JoinTable(
        name="USERS_GROUPS",
        joinColumns = @JoinColumn(name="USER_ID", referencedColumnName="ID"),
        inverseJoinColumns = @JoinColumn(name="GROUP_ID", referencedColumnName="ID"))
    private Collection<Group> groups;

    @Column(name = "NAME", nullable = false)
    private String name;
}
