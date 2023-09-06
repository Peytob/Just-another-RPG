package dev.peytob.rpg.client.spring.configuration;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.context.EcsContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EcsContextConfiguration {

    @Bean
    EcsContext ecsContext() {
        return EcsContexts.empty();
    }
}
