package dev.peytob.rpg.client.spring.configuration.graphics;

import dev.peytob.rpg.client.model.graphics.Window;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WindowConfiguration {

    @Bean
    Window mainWindow() {
        return Window.create("Hello world", 800, 600);
    }
}
