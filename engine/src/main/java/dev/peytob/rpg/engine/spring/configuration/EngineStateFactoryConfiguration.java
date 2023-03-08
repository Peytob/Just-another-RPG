package dev.peytob.rpg.engine.spring.configuration;

import dev.peytob.rpg.engine.state.EngineState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class EngineStateFactoryConfiguration {

    @Bean
    Map<String, EngineState> engineStateFactoryMap(List<EngineState> engineStates) {
        return engineStates.stream()
                .collect(Collectors.toUnmodifiableMap(EngineState::getName, Function.identity()));
    }
}
