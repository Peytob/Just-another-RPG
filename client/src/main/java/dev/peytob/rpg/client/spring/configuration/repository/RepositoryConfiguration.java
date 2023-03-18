package dev.peytob.rpg.client.spring.configuration.repository;

import dev.peytob.rpg.client.resource.*;
import dev.peytob.rpg.engine.repositry.Repositories;
import dev.peytob.rpg.engine.repositry.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    Repository<Shader> shaderRepository() {
        return Repositories.mutable();
    }

    @Bean
    Repository<ShaderProgram> shaderProgramRepository() {
        return Repositories.mutable();
    }

    @Bean
    Repository<VertexArray> vertexArrayRepository() {
        return Repositories.mutable();
    }

    @Bean
    Repository<Buffer> bufferRepository() {
        return Repositories.mutable();
    }

    @Bean
    Repository<Mesh> meshRepository() {
        return Repositories.mutable();
    }
}
