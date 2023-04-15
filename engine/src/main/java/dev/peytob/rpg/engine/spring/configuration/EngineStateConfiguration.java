package dev.peytob.rpg.engine.spring.configuration;

import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.state.EngineStateProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class EngineStateConfiguration {

    @Bean
    Collection<EngineState> engineStates(Collection<EngineStateProvider> stateProviders) {
        return stateProviders.stream()
            .map(this::engineState)
            .toList();
    }

    @Bean
    EngineState engineState(EngineStateProvider stateProvider) {
        return stateProvider.createEngineState();
    }
}
