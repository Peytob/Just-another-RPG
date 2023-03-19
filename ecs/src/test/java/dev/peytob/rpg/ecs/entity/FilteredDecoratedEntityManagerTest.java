package dev.peytob.rpg.ecs.entity;

import dev.peytob.rpg.ecs.entity.filer.FilterManagers;

public class FilteredDecoratedEntityManagerTest extends EntityManagerTest {
    @Override
    EntityManager createInstance() {
        return FilterManagers.decorate(EntityManagers.mutable());
    }
}
