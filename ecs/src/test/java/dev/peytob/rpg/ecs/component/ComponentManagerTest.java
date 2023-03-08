package dev.peytob.rpg.ecs.component;

import dev.peytob.rpg.ecs.EcsTests;
import dev.peytob.rpg.ecs.exception.ComponentAlreadyRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ComponentManagerTest extends EcsTests {

    ComponentManager componentManager;

    abstract ComponentManager createInstance();

    @BeforeEach
    void setUpComponentManagerInstance() {
        this.componentManager = createInstance();
    }

    @Test
    void componentsManagerIsClearAfterCreating() {
        assertElementsEquals(
                Collections.emptyList(),
                componentManager.getTypes()
        );

        assertEquals(0, componentManager.getSize());
    }

    @Test
    void returnsEmptyCollectionAtNotExistsType() {
        assertIterableEquals(
                Collections.emptyList(),
                componentManager.getAllByType(FirstTestComponent.class)
        );
    }

    @Test
    void oneComponentSuccessfullyRegistered() {
        FirstTestComponent component = new FirstTestComponent();
        Class<FirstTestComponent> componentClass = FirstTestComponent.class;

        componentManager.register(component);

        assertElementsEquals(
                componentManager.getTypes(),
                Collections.singleton(componentClass)
        );

        assertElementsEquals(
                componentManager.getAllByType(componentClass),
                Collections.singleton(component)
        );
    }

    @Test
    void manyComponentsWithSingleTypeSuccessfullyRegistered() {
        FirstTestComponent component1 = new FirstTestComponent();
        FirstTestComponent component2 = new FirstTestComponent();
        FirstTestComponent component3 = new FirstTestComponent();
        Class<FirstTestComponent> componentClass = FirstTestComponent.class;

        componentManager.register(component1);
        componentManager.register(component2);
        componentManager.register(component3);

        assertEquals(3, componentManager.getSize());

        assertElementsEquals(
                Collections.singleton(componentClass),
                componentManager.getTypes()
        );

        assertElementsEquals(
                List.of(component1, component2, component3),
                componentManager.getAllByType(componentClass)
        );
    }

    @Test
    void manyComponentsWithDifferentTypesSuccessfullyRegistered() {
        FirstTestComponent firstComponent = new FirstTestComponent();
        SecondTestComponent secondComponent = new SecondTestComponent();

        componentManager.register(firstComponent);
        componentManager.register(secondComponent);

        assertEquals(2, componentManager.getSize());

        assertElementsEquals(
                List.of(firstComponent.getClass(), secondComponent.getClass()),
                componentManager.getTypes()
        );

        assertElementsEquals(
                Collections.singleton(firstComponent),
                componentManager.getAllByType(FirstTestComponent.class)
        );

        assertElementsEquals(
                Collections.singleton(secondComponent),
                componentManager.getAllByType(SecondTestComponent.class)
        );
    }

    @Test
    void typeDisappearsAfterRemovingLastTypeComponent() {
        Component firstComponent = new FirstTestComponent();
        Component secondComponent = new SecondTestComponent();

        componentManager.register(firstComponent);
        componentManager.register(secondComponent);

        componentManager.remove(firstComponent);

        assertElementsEquals(
                Collections.singleton(secondComponent.getClass()),
                componentManager.getTypes()
        );

        assertIterableEquals(
                Collections.emptyList(),
                componentManager.getAllByType(firstComponent.getClass())
        );

        assertEquals(1, componentManager.getSize());
    }

    @Test
    void componentManagerDeletesAllElementsAfterClearing() {
        Component firstComponent = new FirstTestComponent();
        Component secondComponent = new SecondTestComponent();

        componentManager.register(firstComponent);
        componentManager.register(secondComponent);

        componentManager.clear();

        assertElementsEquals(
                Collections.emptyList(),
                componentManager.getTypes()
        );

        assertEquals(0, componentManager.getSize());
    }

    @Test
    void componentManagerThrowsExceptionIfComponentAlreadyExists() {
        Component component = new FirstTestComponent();

        componentManager.register(component);
        assertThrows(ComponentAlreadyRegisteredException.class, () -> componentManager.register(component));
    }

    @Test
    void typesCollectionShouldBeImmutable() {
        Component component = new FirstTestComponent();
        componentManager.register(component);

        Collection<Class<? extends Component>> types = componentManager.getTypes();

        assertThrows(UnsupportedOperationException.class, () -> types.add(Component.class));
        assertThrows(UnsupportedOperationException.class, types::clear);
        assertThrows(UnsupportedOperationException.class, () -> types.remove(component.getClass()));
    }

    @Test
    void componentsCollectionShouldBeImmutable() {
        FirstTestComponent component = new FirstTestComponent();
        componentManager.register(component);

        Collection<FirstTestComponent> components = componentManager.getAllByType(FirstTestComponent.class);

        assertThrows(UnsupportedOperationException.class, () -> components.add(new FirstTestComponent()));
        assertThrows(UnsupportedOperationException.class, components::clear);
        assertThrows(UnsupportedOperationException.class, () -> components.remove(component));
    }
}