package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.component.FirstTestComponent;
import dev.peytob.rpg.ecs.component.SecondTestComponent;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.event.EventHandler;
import dev.peytob.rpg.ecs.event.implementation.event.FirstEvent;
import dev.peytob.rpg.ecs.event.implementation.handler.FirstEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
                ecsContext.getUnmodifiableComponentManager().getAllByType(first.getClass()));

        assertElementsEquals(
                Collections.singleton(second),
                ecsContext.getUnmodifiableComponentManager().getAllByType(second.getClass()));

        entity.removeComponent(first.getClass());

        assertTrue(
                ecsContext.getUnmodifiableComponentManager().getAllByType(first.getClass()).isEmpty());

        assertElementsEquals(
                Collections.singleton(second),
                ecsContext.getUnmodifiableComponentManager().getAllByType(second.getClass()));
    }

    @Test
    void contextCanCatchSimpleEvent() {
        FirstEventHandler firstEventHandler = Mockito.mock(FirstEventHandler.class);

        FirstEvent event = new FirstEvent();
        FirstEventHandlerWrapper wrapper = new FirstEventHandlerWrapper(firstEventHandler);

        ecsContext.getEventManager().register(wrapper);

        assertDoesNotThrow(() -> ecsContext.catchEvent(event));

        Mockito.verify(firstEventHandler).handle(ecsContext, event);
    }

    /**
     * TODO Remove wrapper after fix EventReflectionUtilsTest::resolveHandlerEventType method fix!
     * Wrapper, that allows to passing event handlers mocks to current realisation of resolveHandlerEventType method.
     */
    private static class FirstEventHandlerWrapper implements EventHandler<FirstEvent> {

        private final FirstEventHandler eventHandler;

        public FirstEventHandlerWrapper(FirstEventHandler eventHandler) {
            this.eventHandler = eventHandler;
        }

        @Override
        public void handle(EcsContext context, FirstEvent event) {
            eventHandler.handle(context, event);
        }
    }
}