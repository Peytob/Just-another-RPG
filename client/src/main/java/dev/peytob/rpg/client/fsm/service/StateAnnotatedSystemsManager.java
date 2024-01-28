package dev.peytob.rpg.client.fsm.service;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class StateAnnotatedSystemsManager implements StateSystemsManager {

    private final Multimap<Class<? extends EngineState>, OrderedSystem> systems;

    public StateAnnotatedSystemsManager(Multimap<Class<? extends EngineState>, OrderedSystem> systems) {
        this.systems = Multimaps.unmodifiableMultimap(systems);
    }

    @Override
    public Collection<OrderedSystem> getSystemsForState(EngineState engineState) {
        return getSystemsForState(engineState.getClass());
    }

    @Override
    public Collection<OrderedSystem> getSystemsForState(Class<? extends EngineState> engineStateClass) {
        // TODO If state extends other state, then parent systems should be returned too
        return systems.get(engineStateClass);
    }

    @PostConstruct
    private void logOrderMap() {
        if (!log.isDebugEnabled()) {
            return;
        }

        log.debug("Begin of logging engine states systems");

        systems.asMap().forEach((engineState, systemList) -> {
            String systemListString = systemList.stream()
                .map(orderedSystem -> orderedSystem.getSystem().getClass().getSimpleName() + "[" + orderedSystem.getOrder() + "]")
                .collect(Collectors.joining(", "));

            log.debug("Systems list for {} state: {}", engineState.getSimpleName(), systemListString);
        });

        log.debug("End of logging engine states systems");
    }
}