package dev.peytob.rpg.ecs.entity;

class GenericEntityTest extends EntityTest {

    @Override
    Entity createTestingImplementationEntity() {
        return new GenericEntity("generic");
    }
}