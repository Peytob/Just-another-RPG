package dev.peytob.rpg.server.gameplay.service.data;

import dev.peytob.rpg.server.gameplay.resource.Character;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class BackendCharacterService implements CharacterService {

    @Qualifier("backendRestTemplate")
    private final RestTemplate backendRestTemplate;

    @Override
    public Character getCharacterById(String characterId) {
        return backendRestTemplate.getForObject("/character/{characterId}", Character.class, characterId);
    }

    @Override
    public Character getUserCharacterById(String userId, String characterId) {
        return backendRestTemplate.getForObject("/user/{userId}/character/{characterId}", Character.class, userId, characterId);
    }
}
