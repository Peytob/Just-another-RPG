package dev.peytob.rpg.engine.spring.configuration;

import dev.peytob.rpg.engine.repositry.Repositories;
import dev.peytob.rpg.engine.repositry.Repository;
import dev.peytob.rpg.engine.resource.Archetype;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseRepositoriesConfiguration {

    @Bean
    Repository<Archetype> archetypeRepository() {
        return Repositories.mutable();
    }
}
