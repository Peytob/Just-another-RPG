package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.component.FirstTestComponent;
import dev.peytob.rpg.ecs.component.SecondTestComponent;
import dev.peytob.rpg.ecs.entity.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

abstract class EcsContextTest extends EcsTests {

    EcsContext ecsContext;

    abstract EcsContext createInstance();

    @BeforeEach
    void setUpEcsContextMethodInstance() {
        this.ecsContext = createInstance();
    }

    @Test
    void newContextIsEmpty() {
        assertEquals(0, ecsContext.getUnmodifiableEntityManager().getSize());
        assertEquals(0, ecsContext.getUnmodifiableComponentManager().getSize());
        assertEquals(0, ecsContext.getSystemManager().getSize());
    }

    @Test
    void newEntitySuccessfullyCreated() {
        Entity entity = ecsContext.newEntity("1");
        assertIterableEquals(Collections.singleton(entity), ecsContext.getUnmodifiableEntityManager().getAll());
    }

    @Test
    void contextEntitiesNotifiesContextAboutNewComponents() {
        Entity entity = ecsContext.newEntity("1");
        SecondTestComponent component = new SecondTestComponent();
        entity.bindComponent(component);

        assertIterableEquals(
                Collections.singleton(component),
                ecsContext.getUnmodifiableComponentManager().getAllByType(component.getClass()));
    }

    @Test
    void contextEntitiesNotifiesContextAboutNewComponentRemoving() {
        Entity entity = ecsContext.newEntity("1");
        FirstTestComponent first = new FirstTestComponent();
        entity.bindComponent(first);
        SecondTestComponent second = new SecondTestComponent();
        entity.bindComponent(second);

        assertElementsEquals(
                Collections.singleton(first),
                ecsContext.getUnmodifiableComponentManager().getAllByType(FirstTestComponent.class));

        assertElementsEquals(
                Collections.singleton(second),
                ecsContext.getUnmodifiableComponentManager().getAllByType(SecondTestComponent.class));

        entity.removeComponent(first.getClass());

        assertTrue(
                ecsContext.getUnmodifiableComponentManager().getAllByType(first.getClass()).isEmpty());

        assertElementsEquals(
                Collections.singleton(second),
                ecsContext.getUnmodifiableComponentManager().getAllByType(SecondTestComponent.class));
    }
}