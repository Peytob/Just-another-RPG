package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GROUPS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "REALM", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Realm realm;

    @Column(name = "NAME", nullable = false)
    private String name;
}
