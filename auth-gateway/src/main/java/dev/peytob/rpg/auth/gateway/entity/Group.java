package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "GROUPS")
public class Group extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "GROUPS", nullable = false, updatable = false)
    private Realm realm;

    @Column(name = "NAME", nullable = false)
    private String name;
}
