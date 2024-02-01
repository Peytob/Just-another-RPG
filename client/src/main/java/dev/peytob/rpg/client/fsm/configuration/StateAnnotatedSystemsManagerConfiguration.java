package dev.peytob.rpg.client.fsm.configuration;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Streams;
import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.annotation.IncludeInStates;
import dev.peytob.rpg.client.fsm.annotation.SystemMixin;
import dev.peytob.rpg.client.fsm.service.StateAnnotatedSystemsManager;
import dev.peytob.rpg.client.fsm.state.EngineState;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class StateAnnotatedSystemsManagerConfiguration {

    public static final int EXPECTED_SYSTEMS_COUNT_PER_STATE = 32;

    private static final IncludeInState[] EMPTY_INCLUDES = new IncludeInState[0];

    private final Collection<System> systems;

    private final Collection<SystemMixin> systemMixins;

    private final Collection<EngineState> engineStates;

    @Bean
    public StateAnnotatedSystemsManager stateAnnotatedSystemsManager() {
        Multimap<Class<? extends System>, SystemMixin> systemMixinsIndex = Multimaps.index(systemMixins, SystemMixin::getTargetSystem);

        Multimap<Class<? extends EngineState>, OrderedSystem> nativeSystemsMap = buildNativeSystemsMap(systemMixinsIndex);
        // TODO Validate systemsMap

        return new StateAnnotatedSystemsManager(nativeSystemsMap);
    }

    private Multimap<Class<? extends EngineState>, OrderedSystem> buildNativeSystemsMap(Multimap<Class<? extends System>, SystemMixin> systemMixinsIndex) {
        Multimap<Class<? extends EngineState>, OrderedSystem> systemsMap = HashMultimap.create(engineStates.size(), EXPECTED_SYSTEMS_COUNT_PER_STATE);

        // Include in all states
        for (System system : systems) {
            Collection<SystemMixin> targetSystemMixins = systemMixinsIndex.get(system.getClass());

            Map<Class<? extends EngineState>, OrderedSystem> includeInAllStatesResult =
                processIncludeInAllStates(system, targetSystemMixins);

            systemsMap.putAll(Multimaps.forMap(includeInAllStatesResult));

            Map<Class<? extends EngineState>, OrderedSystem> includeInStateResult =
                processIncludeInStates(system, targetSystemMixins);

            systemsMap.putAll(Multimaps.forMap(includeInStateResult));
        }

        return systemsMap;
    }

    private Map<Class<? extends EngineState>, OrderedSystem> processIncludeInAllStates(System system, Collection<SystemMixin> systemMixinIndex) {
        if (systemMixinIndex.size() > 1) {
            log.error("For ecs system {} found {} mixins: {}. Should be 0 or 1.", system.getClass(), systemMixinIndex.size(), systemMixinIndex);
            throw new IllegalStateException("Too many mixins for ecs system in context. Should be 0 or 1.");
        }

        List<IncludeInAllStates> mixinIncludeInAllStates = systemMixinIndex.stream()
            .map(systemMixin -> systemMixin.getClass().getAnnotation(IncludeInAllStates.class))
            .toList();

        IncludeInAllStates systemIncludeInAllStates = system.getClass().getAnnotation(IncludeInAllStates.class);

        if (mixinIncludeInAllStates.isEmpty() && systemIncludeInAllStates == null) {
            return Collections.emptyMap();
        }

        boolean allMixinsOrdersIsSame = mixinIncludeInAllStates.stream()
            .map(IncludeInAllStates::order)
            .distinct()
            .count() <= 1;

        if (!allMixinsOrdersIsSame || !mixinIncludeInAllStates.isEmpty() && systemIncludeInAllStates != null && mixinIncludeInAllStates.get(0).order() == systemIncludeInAllStates.order()) {
            log.error("Orders from system and mixins 'Include in all states' should be equal");
            throw new IllegalStateException("Orders from system and mixins 'Include in all states' should be equal");
        }

        int systemOrder;
        if (systemIncludeInAllStates != null) {
            systemOrder = systemIncludeInAllStates.order();
        } else {
            systemOrder = mixinIncludeInAllStates.get(0).order();
        }

        Collection<Class<? extends EngineState>> excludeAccumulator = new HashSet<>(EXPECTED_SYSTEMS_COUNT_PER_STATE);

        if (systemIncludeInAllStates != null) {
            excludeAccumulator.addAll(Arrays.asList(systemIncludeInAllStates.exclude()));
        }

        mixinIncludeInAllStates.stream()
            .map(IncludeInAllStates::exclude)
            .filter(Objects::nonNull)
            .map(Arrays::asList)
            .forEach(excludeAccumulator::addAll);

        return engineStates.stream()
            .map(EngineState::getClass)
            .filter(engineStateClass -> !excludeAccumulator.contains(engineStateClass))
            .collect(Collectors.toMap(identity(), engineStateClass -> OrderedSystem.wrap(system, systemOrder)));
    }

    private Map<Class<? extends EngineState>, OrderedSystem> processIncludeInStates(System system, Collection<SystemMixin> systemMixinIndex) {
        IncludeInState[] systemStates = extractAnnotatedEngineStates(system);

        Collection<IncludeInState> includeInStatesMixin = systemMixinIndex.stream()
            .map(this::extractAnnotatedEngineStates)
            .flatMap(Stream::of)
            .toList();

        return Streams.concat(Arrays.stream(systemStates), includeInStatesMixin.stream())
            .collect(Collectors.toMap(IncludeInState::value, includeInState -> OrderedSystem.wrap(system, includeInState.order())));
    }

    private IncludeInState[] extractAnnotatedEngineStates(Object object) {
        IncludeInStates includeInStates = object.getClass().getAnnotation(IncludeInStates.class);

        if (includeInStates != null) {
            return includeInStates.states();
        }

        IncludeInState includeInState = object.getClass().getAnnotation(IncludeInState.class);

        if (includeInState != null) {
            return new IncludeInState[] { includeInState };
        }

        return EMPTY_INCLUDES;
    }
}

