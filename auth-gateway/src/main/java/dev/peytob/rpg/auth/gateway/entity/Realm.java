package dev.peytob.rpg.auth.gateway.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Realm extends AbstractEntity {

    @Column(name = "NAME", nullable = false, length = 64)
    private String name;
}
