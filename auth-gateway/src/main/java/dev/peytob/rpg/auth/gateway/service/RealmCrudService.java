package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.RealmDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RealmCrudService {

    Optional<Realm> findRealmByName(String realmName);

    Realm getRealmByName(String realmName);

    Optional<Realm> findRealmById(String realmId);

    Realm getRealmById(String realmId);

    Page<Realm> getRealmsPage(Pageable pageable);

    Realm createRealm(RealmDto realmDto);

    Realm updateRealm(Realm realm, RealmDto realmDto);

    void deleteRealm(Realm realm);
}
