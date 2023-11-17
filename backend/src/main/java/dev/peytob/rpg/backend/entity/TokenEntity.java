package dev.peytob.rpg.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity(name = "Token")
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity extends AbstractEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", updatable = false)
    @Setter(AccessLevel.NONE)
    private UserEntity user;

    @Column(name = "ENCODED_TOKEN", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private String encodedToken;

    @Column(name = "EXPIRATION_AT", nullable = false)
    private Instant expirationAt;

    @Column(name = "TYPE", nullable = false)
    private TokenType type;

}
