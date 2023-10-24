package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity
public class Token extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", updatable = false)
    private User user;

    @Column(name = "TOKEN", nullable = false, updatable = false)
    private String token;

    @Column(name = "EXPECTED_AT", nullable = false)
    private Instant expected_at;
}
