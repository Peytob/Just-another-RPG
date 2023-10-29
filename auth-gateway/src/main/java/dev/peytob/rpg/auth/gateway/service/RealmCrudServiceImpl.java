package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.realm.RealmCreateDto;
import dev.peytob.rpg.auth.gateway.dto.realm.RealmGetDto;
import dev.peytob.rpg.auth.gateway.dto.realm.RealmUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.exception.EntityAlreadyExistsException;
import dev.peytob.rpg.auth.gateway.exception.NotFoundException;
import dev.peytob.rpg.auth.gateway.repository.RealmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    @Transactional(readOnly = true)
    public Page<Realm> getRealmsPage(Pageable pageable) {
        return realmRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Realm createRealm(RealmCreateDto realmCreateDto) {
        if (realmRepository.existsByName(realmCreateDto.name())) {
            throw buildRealmAlreadyExistsException(realmCreateDto.name());
        }

        Realm realm = Realm.builder()
            .name(realmCreateDto.name())
            .build();

        return realmRepository.save(realm);
    }

    @Override
    @Transactional
    public Realm updateRealm(Realm realm, RealmUpdateDto realmUpdateDto) {
        if (realmRepository.existsByName(realmUpdateDto.name())) {
            throw buildRealmAlreadyExistsException(realmUpdateDto.name());
        }

        realm.setName(realmUpdateDto.name());

        return realmRepository.save(realm);
    }

    @Override
    @Transactional
    public void deleteRealm(Realm realm) {
        realmRepository.delete(realm);
    }

    private RuntimeException buildRealmAlreadyExistsException(String name) {
        return new EntityAlreadyExistsException("Realm with name '" + name + "' already exists!");
    }
}
