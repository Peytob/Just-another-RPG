package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Realm extends AbstractEntity {

    @Column(name = "NAME", nullable = false, unique = true, length = 64)
    private String name;
}
