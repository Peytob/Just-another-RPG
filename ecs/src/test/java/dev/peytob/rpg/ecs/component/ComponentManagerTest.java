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
                componentManager.getComponentTypes()
        );

        assertEquals(0, componentManager.getComponentsCount());
    }

    @Test
    void returnsEmptyCollectionAtNotExistsType() {
        assertIterableEquals(
                Collections.emptyList(),
                componentManager.getComponentsByType(FirstTestComponent.class)
        );
    }

    @Test
    void oneComponentSuccessfullyRegistered() {
        FirstTestComponent component = new FirstTestComponent();
        Class<FirstTestComponent> componentClass = FirstTestComponent.class;

        componentManager.addComponent(component);

        assertElementsEquals(
                componentManager.getComponentTypes(),
                Collections.singleton(componentClass)
        );

        assertElementsEquals(
                componentManager.getComponentsByType(componentClass),
                Collections.singleton(component)
        );
    }

    @Test
    void manyComponentsWithSingleTypeSuccessfullyRegistered() {
        FirstTestComponent component1 = new FirstTestComponent();
        FirstTestComponent component2 = new FirstTestComponent();
        FirstTestComponent component3 = new FirstTestComponent();
        Class<FirstTestComponent> componentClass = FirstTestComponent.class;

        componentManager.addComponent(component1);
        componentManager.addComponent(component2);
        componentManager.addComponent(component3);

        assertEquals(3, componentManager.getComponentsCount());

        assertElementsEquals(
                Collections.singleton(componentClass),
                componentManager.getComponentTypes()
        );

        assertElementsEquals(
                List.of(component1, component2, component3),
                componentManager.getComponentsByType(componentClass)
        );
    }

    @Test
    void manyComponentsWithDifferentTypesSuccessfullyRegistered() {
        FirstTestComponent firstComponent = new FirstTestComponent();
        SecondTestComponent secondComponent = new SecondTestComponent();

        componentManager.addComponent(firstComponent);
        componentManager.addComponent(secondComponent);

        assertEquals(2, componentManager.getComponentsCount());

        assertElementsEquals(
                List.of(firstComponent.getClass(), secondComponent.getClass()),
                componentManager.getComponentTypes()
        );

        assertElementsEquals(
                Collections.singleton(firstComponent),
                componentManager.getComponentsByType(FirstTestComponent.class)
        );

        assertElementsEquals(
                Collections.singleton(secondComponent),
                componentManager.getComponentsByType(SecondTestComponent.class)
        );
    }

    @Test
    void typeDisappearsAfterRemovingLastTypeComponent() {
        Component firstComponent = new FirstTestComponent();
        Component secondComponent = new SecondTestComponent();

        componentManager.addComponent(firstComponent);
        componentManager.addComponent(secondComponent);

        componentManager.removeComponent(firstComponent);

        assertElementsEquals(
                Collections.singleton(secondComponent.getClass()),
                componentManager.getComponentTypes()
        );

        assertIterableEquals(
                Collections.emptyList(),
                componentManager.getComponentsByType(firstComponent.getClass())
        );

        assertEquals(1, componentManager.getComponentsCount());
    }

    @Test
    void componentManagerDeletesAllElementsAfterClearing() {
        Component firstComponent = new FirstTestComponent();
        Component secondComponent = new SecondTestComponent();

        componentManager.addComponent(firstComponent);
        componentManager.addComponent(secondComponent);

        componentManager.clear();

        assertElementsEquals(
                Collections.emptyList(),
                componentManager.getComponentTypes()
        );

        assertEquals(0, componentManager.getComponentsCount());
    }

    @Test
    void componentManagerThrowsExceptionIfComponentAlreadyExists() {
        Component component = new FirstTestComponent();

        componentManager.addComponent(component);
        assertThrows(ComponentAlreadyRegisteredException.class, () -> componentManager.addComponent(component));
    }

    @Test
    void typesCollectionShouldBeImmutable() {
        Component component = new FirstTestComponent();
        componentManager.addComponent(component);

        Collection<Class<? extends Component>> types = componentManager.getComponentTypes();

        assertThrows(UnsupportedOperationException.class, () -> types.add(Component.class));
        assertThrows(UnsupportedOperationException.class, types::clear);
        assertThrows(UnsupportedOperationException.class, () -> types.remove(component.getClass()));
    }

    @Test
    void componentsCollectionShouldBeImmutable() {
        FirstTestComponent component = new FirstTestComponent();
        componentManager.addComponent(component);

        Collection<FirstTestComponent> components = componentManager.getComponentsByType(FirstTestComponent.class);

        assertThrows(UnsupportedOperationException.class, () -> components.add(new FirstTestComponent()));
        assertThrows(UnsupportedOperationException.class, components::clear);
        assertThrows(UnsupportedOperationException.class, () -> components.remove(component));
    }

    @Test
    void singletonComponentCanBeRegistered() {
        SingletonComponent singletonComponent = new FirstSingletonComponent();

        assertDoesNotThrow(() -> componentManager.addComponent(singletonComponent));
    }

    @Test
    void otherSingletonComponentCanBeRegisteredAfterRemovingFirst() {
        SingletonComponent singletonComponent1 = new FirstSingletonComponent();
        SingletonComponent singletonComponent2 = new FirstSingletonComponent();

        assertDoesNotThrow(() -> componentManager.addComponent(singletonComponent1));
        assertThrows(ComponentAlreadyRegisteredException.class, () -> componentManager.addComponent(singletonComponent2));
    }

    @Test
    void twoSameSingletonComponentCantBeRegistered() {
        SingletonComponent singletonComponent1 = new FirstSingletonComponent();
        SingletonComponent singletonComponent2 = new FirstSingletonComponent();

        componentManager.addComponent(singletonComponent1);
        componentManager.removeComponent(singletonComponent1);
        assertDoesNotThrow(() -> componentManager.addComponent(singletonComponent2));
    }

    @Test
    void twoDifferentSingletonCanBeRegistered() {
        SingletonComponent singletonComponent1 = new FirstSingletonComponent();
        SingletonComponent singletonComponent2 = new SecondSingletonComponent();

        assertDoesNotThrow(() -> componentManager.addComponent(singletonComponent1));
        assertDoesNotThrow(() -> componentManager.addComponent(singletonComponent2));
    }
}