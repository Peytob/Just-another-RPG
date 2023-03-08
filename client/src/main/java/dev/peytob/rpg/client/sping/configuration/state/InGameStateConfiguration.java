package dev.peytob.rpg.client.sping.configuration.state;

import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.context.template.EcsContextTemplate;
import dev.peytob.rpg.engine.context.template.EcsContextTemplates;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.engine.state.EngineStates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
        return EcsContextTemplates.unmodifiable(systems());
    }

    private List<OrderedSystem> systems() {
        return List.of();
    }
}
