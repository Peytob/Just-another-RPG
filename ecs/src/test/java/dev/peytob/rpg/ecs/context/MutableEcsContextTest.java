package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.component.FirstTestComponent;
import dev.peytob.rpg.ecs.component.SecondTestComponent;
import dev.peytob.rpg.ecs.entity.Entity;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MutableEcsContextTest extends EcsContextTest {

    @Override
    EcsContext createInstance() {
        return EcsContexts.mutable(Collections.emptyList());
    }

    @Test
    void contextEntityShouldSyncComponentsWithContextOnComponentBind() {
        Entity entity = ecsContext.createEntity("contextEntityShouldSyncComponentsWithContext");

        entity.bindComponent(new FirstTestComponent());
        entity.bindComponent(new SecondTestComponent());

        assertContextIsSyncedToSingletonEntity(ecsContext, entity);
    }

    @Test
    void contextEntityShouldSyncComponentsWithContextOnComponentRemoved() {
        Entity entity = ecsContext.createEntity("contextEntityShouldSyncComponentsWithContext");

        entity.bindComponent(new FirstTestComponent());
        entity.bindComponent(new SecondTestComponent());

        entity.removeComponent(SecondTestComponent.class);

        assertContextIsSyncedToSingletonEntity(ecsContext, entity);
    }

    @Test
    void contextEntityIsAliveWhileContextContainsIt() {
        Entity entity = ecsContext.createEntity("contextEntityShouldSyncComponentsWithContext");

        assertTrue(entity.isAlive());
        ecsContext.removeEntity(entity);
        assertFalse(entity.isAlive());
    }

    private void assertContextIsSyncedToSingletonEntity(EcsContext ecsContext, Entity entity) {
        assertElementsEquals(
            entity.getComponentsTypes(),
            ecsContext.getComponentTypes());

        entity.getComponentsTypes()
            .forEach(componentType -> {
                boolean isContextContainsComponent = ecsContext
                    .getComponentsByType(componentType)
                    .contains(entity.getComponent(componentType));

                assertTrue(isContextContainsComponent);
            });
    }
}
