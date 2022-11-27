package dev.peytob.rpg.ecs.system;

public enum SystemManagers {;

    public static SystemManager mutable() {
        return new SimpleSystemManager();
    }
}
