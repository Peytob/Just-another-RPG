package dev.peytob.rpg.auth.gateway.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
public class AbstractEntity {

    @Id
    @GeneratedValue(generator = "base32id")
    private String id;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private Instant created;

    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private Instant updated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
