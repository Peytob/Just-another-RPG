package dev.peytob.rpg.client.configuration.library;

public interface StaticLibraryHandler {

    boolean initialize();

    void destroy();

    String getName();
}
