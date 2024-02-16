package dev.peytob.rpg.client.graphic.configuration;

import dev.peytob.rpg.client.graphic.model.glfw.Window;
import dev.peytob.rpg.client.input.hid.service.GlfwHidEventQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WindowConfiguration {

    @Bean
    Window window(GlfwHidEventQueue hidEventQueue) {
        Window window = Window.create("Just another RPG", 800, 600);
        hidEventQueue.subscribe(window);
        return window;
    }
}
