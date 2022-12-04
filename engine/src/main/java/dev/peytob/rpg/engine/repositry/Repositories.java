package dev.peytob.rpg.engine.repositry;

import dev.peytob.rpg.engine.resource.Resource;

public enum Repositories {;

    public static <T extends Record & Resource> Repository<T> mutable() {
        return new BaseRepository<>();
    }
}
