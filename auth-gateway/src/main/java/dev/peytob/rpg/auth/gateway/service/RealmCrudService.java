package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;

import java.util.Optional;

public interface RealmCrudService {

    Optional<Realm> findRealmByName(String realmName);

    Realm getRealmByName(String realmName);

    Optional<Realm> findRealmById(String realmId);

    Realm getRealmById(String realmId);
}
