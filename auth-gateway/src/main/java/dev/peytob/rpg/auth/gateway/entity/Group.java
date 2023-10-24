package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GROUPS")
@Getter
@Setter
public class Group extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "REALM", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Realm realm;

    @Column(name = "NAME", nullable = false)
    private String name;
}
