package dev.peytob.rpg.engine.spring.configuration;

import dev.peytob.rpg.ecs.system.System;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class SystemFactoryConfiguration {

    @Bean
    Map<Class<? extends System>, System> systemFactoryMap(List<System> systems) {
        return systems.stream()
                .collect(Collectors.toUnmodifiableMap(System::getClass, Function.identity()));
    }
}
