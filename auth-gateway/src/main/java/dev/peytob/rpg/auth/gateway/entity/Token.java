package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", updatable = false)
    @Setter(AccessLevel.NONE)
    private User user;

    @Column(name = "HASH", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private String hash;

    @Column(name = "EXPIRATION_AT", nullable = false)
    private Instant expirationAt;

    @Column(name = "TYPE", nullable = false)
    private TokenType type;
}
