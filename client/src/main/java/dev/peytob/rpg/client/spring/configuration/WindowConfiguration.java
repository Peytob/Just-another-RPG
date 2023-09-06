package dev.peytob.rpg.client.spring.configuration;

import dev.peytob.rpg.client.graphic.model.glfw.Window;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WindowConfiguration {

    @Bean
    Window window() {
        return Window.create("Just another RPG", 800, 600);
    }
}
