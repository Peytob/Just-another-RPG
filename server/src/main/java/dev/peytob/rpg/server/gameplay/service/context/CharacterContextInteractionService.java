package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.server.gameplay.resource.Character;

public interface CharacterContextInteractionService {

    void enterToContext(Character character, String worldContextRunnerName);
}
