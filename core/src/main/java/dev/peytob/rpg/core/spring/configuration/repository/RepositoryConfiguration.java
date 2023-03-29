package dev.peytob.rpg.core.spring.configuration.repository;

import dev.peytob.rpg.core.resource.LocationTemplate;
import dev.peytob.rpg.core.resource.Tile;
import dev.peytob.rpg.engine.repositry.Repositories;
import dev.peytob.rpg.engine.repositry.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    Repository<Tile> tileRepository() {
        return Repositories.mutable();
    }

    @Bean
    Repository<LocationTemplate> locationTemplateRepository() {
        return Repositories.mutable();
    }
}
