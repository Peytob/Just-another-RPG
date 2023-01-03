package dev.peytob.rpg.engine.archetype;

public class ArchetypeParsingException extends RuntimeException {

    public ArchetypeParsingException(String message) {
        super(message);
    }

    public ArchetypeParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
