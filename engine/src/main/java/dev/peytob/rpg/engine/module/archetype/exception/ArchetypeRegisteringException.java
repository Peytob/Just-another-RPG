package dev.peytob.rpg.engine.module.archetype.exception;

import dev.peytob.rpg.engine.module.archetype.resource.Archetype;

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
