package dev.peytob.rpg.auth.gateway.repository;

import dev.peytob.rpg.auth.gateway.dto.realm.RealmMetricDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface RealmRepository extends BaseRepository<Realm> {

    Optional<Realm> findByName(String realmName);

    boolean existsByName(String realmName);

    @Query("""
        SELECT
            r.name AS realmName,
            (SELECT COUNT(*) FROM User u where u.realm = r) AS totalUsersCount,
            (SELECT COUNT(*) FROM User u where u.realm = r AND u.lastLoginAt > :userActiveBorder) AS activeUsersCount
        FROM Realm r
        GROUP BY r.name
        """)
    Collection<RealmMetricDto> getAllRealmsMetrics(Instant userActiveBorder);
}
