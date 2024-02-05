package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.server.gameplay.resource.Character;
import dev.peytob.rpg.server.gameplay.resource.CharacterSession;

public interface CharacterContextInteractionService {

    CharacterSession enterToContext(Character character);

    CharacterSession exitFromContext(Character character);

    WorldState getAwailableWorldState(Character character);
}
