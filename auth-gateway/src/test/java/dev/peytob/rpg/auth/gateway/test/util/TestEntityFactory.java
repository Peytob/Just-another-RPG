package dev.peytob.rpg.auth.gateway.test.util;

import dev.peytob.rpg.auth.gateway.dto.realm.RealmCreateDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;
import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import dev.peytob.rpg.auth.gateway.service.RealmUserCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TestEntityFactory {

    private final RealmCrudService realmCrudService;

    private final RealmUserCrudService realmUserCrudService;

    public Realm createRealm(String name) {
        RealmCreateDto realmCreateDto = new RealmCreateDto(name);
        return realmCrudService.createRealm(realmCreateDto);
    }

    public User createUser(String username, Realm realm) {
        return createUser(username, realm, Instant.now());
    }

    public User createUser(String username, Realm realm, Instant lastLoginAt) {
        User user = User.builder()
            .username(username)
            .lastLoginAt(lastLoginAt)
            .email(username + "@email.dev")
            .passwordHash(username + "hash")
            .groups(Collections.emptyList())
            .realm(realm)
            .build();

        return realmUserCrudService.saveUser(user, realm);
    }
}
