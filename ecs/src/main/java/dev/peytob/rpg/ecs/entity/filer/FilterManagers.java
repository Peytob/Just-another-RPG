package dev.peytob.rpg.ecs.entity.filer;

import dev.peytob.rpg.ecs.entity.EntityManager;

public enum FilterManagers {;

    public static EntityManagerFilteredDecorator decorate(EntityManager entityManager) {
        return new EntityManagerFilteredDecorator(entityManager);
    }
}
