package dev.peytob.rpg.engine.module.archetype.exception;

public class ArchetypeParsingException extends RuntimeException {

    public ArchetypeParsingException(String message) {
        super(message);
    }

    public ArchetypeParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
