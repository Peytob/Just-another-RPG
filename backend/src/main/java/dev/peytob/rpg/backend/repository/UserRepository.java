package dev.peytob.rpg.backend.repository;

import dev.peytob.rpg.backend.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity> {
}
