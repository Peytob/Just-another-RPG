package dev.peytob.rpg.client.module.graphic.spring.configuration;

import dev.peytob.rpg.client.module.graphic.model.Window;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WindowConfiguration {

    @Bean
    Window mainWindow() {
        return Window.create("Hello world", 800, 600);
    }
}
