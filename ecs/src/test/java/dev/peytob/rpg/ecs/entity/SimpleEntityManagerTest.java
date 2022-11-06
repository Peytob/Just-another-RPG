package dev.peytob.rpg.ecs.entity;

public class SimpleEntityManagerTest extends EntityManagerTest {

    @Override
    EntityManager createInstance() {
        return new SimpleEntityManager();
    }
}
