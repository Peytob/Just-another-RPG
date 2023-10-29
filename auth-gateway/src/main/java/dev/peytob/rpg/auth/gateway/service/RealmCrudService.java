package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.realm.RealmCreateDto;
import dev.peytob.rpg.auth.gateway.dto.realm.RealmGetDto;
import dev.peytob.rpg.auth.gateway.dto.realm.RealmUpdateDto;
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

    Realm createRealm(RealmCreateDto realmCreateDto);

    Realm updateRealm(Realm realm, RealmUpdateDto realmUpdateDto);

    void deleteRealm(Realm realm);
}
