package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.component.FirstTestComponent;
import dev.peytob.rpg.ecs.component.SecondTestComponent;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.event.implementation.event.FirstEvent;
import dev.peytob.rpg.ecs.event.implementation.event.SecondEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

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
        assertTrue(ecsContext.getAllEntities().isEmpty());
        assertTrue(ecsContext.getComponentTypes().isEmpty());
    }

    @Test
    void newEntitySuccessfullyCreated() {
        Entity entity = ecsContext.createEntity("newEntitySuccessfullyCreated");
        assertIterableEquals(Collections.singleton(entity), ecsContext.getAllEntities());
    }

    @Test
    void contextEntitiesNotifiesContextAboutNewComponents() {
        Entity entity = ecsContext.createEntity("contextEntitiesNotifiesContextAboutNewComponents");
        SecondTestComponent component = new SecondTestComponent();
        entity.bindComponent(component);

        assertIterableEquals(
                Collections.singleton(component),
                ecsContext.getComponentsByType(component.getClass()));
    }

    @Test
    void contextEntitiesNotifiesContextAboutNewComponentRemoving() {
        Entity entity = ecsContext.createEntity("contextEntitiesNotifiesContextAboutNewComponentRemoving");
        FirstTestComponent first = new FirstTestComponent();
        entity.bindComponent(first);
        SecondTestComponent second = new SecondTestComponent();
        entity.bindComponent(second);

        assertElementsEquals(
                Collections.singleton(first),
                ecsContext.getComponentsByType(FirstTestComponent.class));

        assertElementsEquals(
                Collections.singleton(second),
                ecsContext.getComponentsByType(SecondTestComponent.class));

        entity.removeComponent(first.getClass());

        assertTrue(
                ecsContext.getComponentsByType(first.getClass()).isEmpty());

        assertElementsEquals(
                Collections.singleton(second),
                ecsContext.getComponentsByType(SecondTestComponent.class));
    }

    @Test
    void returnsEmptyOptionalIfEntityIdNotExists() {
        assertTrue(ecsContext.getEntityById("notExistsIds").isEmpty());
    }

    @Test
    void returnsCorrectEntityById() {
        ecsContext.createEntity("wrongEntityId");
        Entity expectedEntity = ecsContext.createEntity("expectedEntity");

        Optional<Entity> foundEntity = ecsContext.getEntityById(expectedEntity.getId());
        assertTrue(foundEntity.isPresent());
        assertSame(expectedEntity, foundEntity.get());
    }

    @Test
    void entityCanBeRemovedSuccessfully() {
        Entity entity = ecsContext.createEntity("entityCanBeRemovedSuccessfully");
        entity.bindComponent(new FirstTestComponent());
        entity.bindComponent(new SecondTestComponent());

        ecsContext.removeEntity(entity);

        assertTrue(ecsContext.getEntityById(entity.getId()).isEmpty());
        assertTrue(ecsContext.getComponentTypes().isEmpty());
    }

    @Test
    void eventCanBeSuccessfullyAdded() {
        FirstEvent event = new FirstEvent();

        ecsContext.addEvent(event);
        assertElementsEquals(
            Collections.singleton(FirstEvent.class),
            ecsContext.getEventTypes());

        assertElementsEquals(
            Collections.singleton(event),
            ecsContext.getEventsByType(FirstEvent.class));
    }

    @Test
    void eventCanBeSuccessfullyRemoved() {
        FirstEvent event = new FirstEvent();

        ecsContext.addEvent(event);
        ecsContext.removeEvent(event);

        assertTrue(ecsContext.getEventsByType(FirstEvent.class).isEmpty());
    }

    @Test
    void clearEventsRemovesAllEvent() {
        FirstEvent firstEvent = new FirstEvent();
        FirstEvent anotherFirstEvent = new FirstEvent();
        SecondEvent secondEvent = new SecondEvent();

        ecsContext.addEvent(firstEvent);
        ecsContext.addEvent(anotherFirstEvent);
        ecsContext.addEvent(secondEvent);

        ecsContext.clearEvents();

        assertTrue(ecsContext.getEventTypes().isEmpty());
        assertTrue(ecsContext.getEventsByType(FirstEvent.class).isEmpty());
        assertTrue(ecsContext.getEventsByType(SecondEvent.class).isEmpty());
    }
}
