package dev.peytob.rpg.ecs.entity;

public enum EntityManagers {;

    public static EntityManager mutable() {
        return new SimpleEntityManager();
    }

    public static EntityManager unmodifiable(EntityManager entityManager) {
        if (entityManager instanceof UnmodifiableEntityManager unmodifiableEntityManager) {
            return unmodifiableEntityManager;
        }

        return new UnmodifiableEntityManager(entityManager);
    }
}
