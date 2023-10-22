package dev.peytob.rpg.auth.gateway.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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
