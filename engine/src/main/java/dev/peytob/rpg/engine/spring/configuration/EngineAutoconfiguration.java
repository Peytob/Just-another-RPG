package dev.peytob.rpg.engine.spring.configuration;

import dev.peytob.rpg.engine.RpgEngine;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = RpgEngine.class)
public class EngineAutoconfiguration {
}
