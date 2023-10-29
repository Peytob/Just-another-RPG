package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Realm extends AbstractEntity {

    @Column(name = "NAME", nullable = false, unique = true, length = 64)
    private String name;
}
