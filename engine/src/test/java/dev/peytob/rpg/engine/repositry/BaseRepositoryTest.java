package dev.peytob.rpg.engine.repositry;

import dev.peytob.rpg.engine.resource.TestResource1;

public class BaseRepositoryTest extends RepositoryTest<TestResource1> {

    @Override
    Repository<TestResource1> createInstance() {
        return Repositories.mutable();
    }

    @Override
    TestResource1 createTestResource(Integer id, String textId) {
        return new TestResource1(id, textId);
    }
}
