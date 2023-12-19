package dev.peytob.rpg.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "Character")
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterEntity extends AbstractEntity {

    @ManyToOne(optional = false)
    private UserEntity user;

    @Column(name = "NAME", nullable = false, updatable = false, unique = true)
    private String name;
}
