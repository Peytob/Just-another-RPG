package dev.peytob.rpg.engine.archetype;

import dev.peytob.rpg.ecs.component.Component;
import dev.peytob.rpg.engine.NonContextRpgEngineTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class ComponentFactoryTest<T extends Component> extends NonContextRpgEngineTest {

    ComponentFactory<T> componentFactory;

    abstract ComponentFactory<T> createInstance();

    @BeforeEach
    void setUp() {
        componentFactory = createInstance();
    }

    @Test
    void isAllComponentsDifferentObjects() {
        T first = componentFactory.create();
        T second = componentFactory.create();

        assertNotSame(first, second);
    }
}