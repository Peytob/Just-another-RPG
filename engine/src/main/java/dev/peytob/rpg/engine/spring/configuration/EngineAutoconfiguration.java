package dev.peytob.rpg.engine.spring.configuration;

import dev.peytob.rpg.engine.Engine;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Engine.class)
public class EngineAutoconfiguration {
}
