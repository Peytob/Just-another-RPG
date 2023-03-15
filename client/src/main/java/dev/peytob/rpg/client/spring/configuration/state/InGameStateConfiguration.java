package dev.peytob.rpg.client.spring.configuration.state;

import dev.peytob.rpg.client.context.system.window.WindowEventPoolingSystem;
import dev.peytob.rpg.client.context.system.window.WindowSwappingBuffersSystem;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import dev.peytob.rpg.engine.context.template.EcsContextTemplates;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.state.EngineStates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * TODO This is temporary fast solution
 */
@Configuration
public class InGameStateConfiguration {

    private final SystemFactory systemFactory;

    public InGameStateConfiguration(SystemFactory systemFactory) {
        this.systemFactory = systemFactory;
    }

    @Bean
    EngineState inGameState() {
        return EngineStates.create("inGameState", ecsTemplate());
    }

    private EcsContextTemplate ecsTemplate() {
        Collection<OrderedSystem> systems = asOrderedSystems(unorderedSystems());

        return EcsContextTemplates.unmodifiable(systems);
    }

    private List<System> unorderedSystems() {
        return List.of(
                systemFactory.getSystem(WindowEventPoolingSystem.class),
                systemFactory.getSystem(WindowSwappingBuffersSystem.class)
        );
    }

    private Collection<OrderedSystem> asOrderedSystems(List<System> systems) {
        List<OrderedSystem> orderedSystems = new ArrayList<>(systems.size());
        for (int index = 0; index < systems.size(); index++) {
            System system = systems.get(index);
            orderedSystems.add(OrderedSystem.wrap(system, index));
        }
        return Collections.unmodifiableCollection(orderedSystems);
    }
}
