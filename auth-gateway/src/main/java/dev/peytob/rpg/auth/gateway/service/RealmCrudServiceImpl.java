package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.exception.NotFoundException;
import dev.peytob.rpg.auth.gateway.repository.RealmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealmCrudServiceImpl implements RealmCrudService {

    private final RealmRepository realmRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Realm> findRealmByName(String realmName) {
        return realmRepository.findByName(realmName);
    }

    @Override
    @Transactional(readOnly = true)
    public Realm getRealmByName(String realmName) {
        return findRealmByName(realmName)
            .orElseThrow(() -> new NotFoundException("Realm with name '" + realmName + "' not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Realm> findRealmById(String realmId) {
        return realmRepository.findById(realmId);
    }

    @Override
    @Transactional(readOnly = true)
    public Realm getRealmById(String realmId) {
        return findRealmById(realmId).orElseThrow(() -> new NotFoundException("Realm with id '" + realmId + "' not found"));
    }
}
