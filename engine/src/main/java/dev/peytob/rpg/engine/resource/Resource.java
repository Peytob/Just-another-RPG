package dev.peytob.rpg.engine.resource;

/**
 * Base interface for all repository elements.
 * Methods is optimized for using with records.
 */
public interface Resource {
    Integer id();

    String textId();
}
