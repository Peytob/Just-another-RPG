package dev.peytob.rpg.client.spring.configuration;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.peytob.rpg.client.fsm.EngineState;
import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.annotation.IncludeInStates;
import dev.peytob.rpg.client.fsm.service.StateAnnotatedSystemsManager;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;

@Configuration
public class StateAnnotatedSystemsManagerConfiguration {

    public static final int EXPECTED_SYSTEMS_COUNT_PER_STATE = 32;

    private static final IncludeInState[] EMPTY_INCLUDES = new IncludeInState[0];

    @Bean
    public StateAnnotatedSystemsManager stateAnnotatedSystemsManager(Collection<System> systems, Collection<EngineState> engineStates) {
        Multimap<Class<? extends EngineState>, OrderedSystem> systemsMap = buildSystemsMap(systems, engineStates);
        return new StateAnnotatedSystemsManager(systemsMap);
    }

    private Multimap<Class<? extends EngineState>, OrderedSystem> buildSystemsMap(Collection<System> systems, Collection<EngineState> engineStates) {
        Multimap<Class<? extends EngineState>, OrderedSystem> systemsMap = HashMultimap.create(engineStates.size(), EXPECTED_SYSTEMS_COUNT_PER_STATE);

        for (System system : systems) {
            IncludeInAllStates includeInAllStates = extractAnnotatedInAllStates(system);

            if (includeInAllStates != null) {
                Collection<Class<? extends EngineState>> excludedSystemStates = Arrays.asList(includeInAllStates.exclude());

                for (EngineState engineState : engineStates) {
                    Class<? extends EngineState> stateClass = engineState.getClass();
                    if (!excludedSystemStates.contains(stateClass)) {
                        OrderedSystem orderedSystem = OrderedSystem.wrap(system, includeInAllStates.order());
                        systemsMap.put(stateClass, orderedSystem);
                    }
                }
            } else {
                IncludeInState[] systemStates = extractAnnotatedEngineStates(system);

                for (IncludeInState stateSystem : systemStates) {
                    OrderedSystem orderedSystem = OrderedSystem.wrap(system, stateSystem.order());
                    systemsMap.put(stateSystem.engineState(), orderedSystem);
                }
            }
        }

        return systemsMap;
    }

    private IncludeInState[] extractAnnotatedEngineStates(System system) {
        IncludeInStates includeInStates = system.getClass().getAnnotation(IncludeInStates.class);

        if (includeInStates != null) {
            return includeInStates.states();
        }

        IncludeInState includeInState = system.getClass().getAnnotation(IncludeInState.class);

        if (includeInState != null) {
            return new IncludeInState[] { includeInState };
        }

        return EMPTY_INCLUDES;
    }

    private IncludeInAllStates extractAnnotatedInAllStates(System system) {
        return system.getClass().getAnnotation(IncludeInAllStates.class);
    }
}
