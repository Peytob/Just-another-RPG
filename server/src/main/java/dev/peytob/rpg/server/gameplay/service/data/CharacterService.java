package dev.peytob.rpg.server.gameplay.service.data;

import dev.peytob.rpg.server.gameplay.resource.Character;

public interface CharacterService {

    Character getCharacterById(String characterId);

    Character getUserCharacterById(String userId, String characterId);
}
