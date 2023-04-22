package dev.peytob.rpg.engine.exception.archetype;

import dev.peytob.rpg.engine.resource.Archetype;

public final class ArchetypeRegisteringException extends RuntimeException {

    private final Archetype archetype;

    public ArchetypeRegisteringException(String message, Archetype archetype) {
        super(message);
        this.archetype = archetype;
    }

    public Archetype getArchetype() {
        return archetype;
    }
}
