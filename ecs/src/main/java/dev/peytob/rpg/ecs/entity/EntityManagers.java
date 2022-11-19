package dev.peytob.rpg.ecs.entity;

public enum EntityManagers {;

    public static EntityManager mutable() {
        return new SimpleEntityManager();
    }
}
