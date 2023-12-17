package dev.peytob.rpg.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ConcurrentConfiguration {

    /**
     * This configuration will create new thread for every execute call. But I need to find a better solution.
     */
    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(Integer.MAX_VALUE);
    }
}
