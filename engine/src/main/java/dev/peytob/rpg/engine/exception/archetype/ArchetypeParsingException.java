package dev.peytob.rpg.engine.exception.archetype;

public class ArchetypeParsingException extends RuntimeException {

    public ArchetypeParsingException(String message) {
        super(message);
    }

    public ArchetypeParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
