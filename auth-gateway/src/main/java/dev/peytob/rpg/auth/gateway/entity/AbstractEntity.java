package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue(generator = "base32id")
    private String id;

    @CreationTimestamp
    private Instant created;

    @UpdateTimestamp
    private Instant updated;
}
