package dev.peytob.rpg.engine.spring.configuration;

import dev.peytob.rpg.engine.loader.archetype.componentFactory.ComponentAbstractFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ComponentFactoryConfiguration {

    @Bean
    Map<String, ComponentAbstractFactory<?, ?>> componentAbstractFactoryMap(List<ComponentAbstractFactory<?, ?>> componentAbstractFactories) {
        return componentAbstractFactories.stream()
                .collect(Collectors.toMap(ComponentAbstractFactory::getSerializedName, Function.identity()));
    }
}
