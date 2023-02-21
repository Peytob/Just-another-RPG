package dev.peytob.rpg.client.system.library;

public interface StaticLibraryHandler {

    boolean initialize();

    void destroy();

    String getName();
}
