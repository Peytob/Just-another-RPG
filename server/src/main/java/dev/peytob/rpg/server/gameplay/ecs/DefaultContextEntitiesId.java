package dev.peytob.rpg.server.gameplay.ecs;

import dev.peytob.rpg.server.gameplay.resource.Character;

public enum DefaultContextEntitiesId {;

    public static String createCharacterEntityId(Character character) {
        return character.name() + ":" + character.id();
    }
}
