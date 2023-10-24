package dev.peytob.rpg.auth.gateway.repository;

import dev.peytob.rpg.auth.gateway.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends AbstractEntity> extends JpaRepository<T, String> {
}
