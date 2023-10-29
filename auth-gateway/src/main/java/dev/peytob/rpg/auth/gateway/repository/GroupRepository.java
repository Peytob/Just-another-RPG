package dev.peytob.rpg.auth.gateway.repository;

import dev.peytob.rpg.auth.gateway.entity.Group;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends BaseRepository<Group> {
    Optional<Group> findByIdAndRealm(String id, Realm realm);

    Page<Group> findAllByRealm(Realm realm, Pageable pageable);
}
