package dev.peytob.rpg.client;

import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.engine.EngineRunningChecker;
import org.springframework.stereotype.Component;

@Component
public class ClientEngineRunningChecker implements EngineRunningChecker {

    private final Window window;

    public ClientEngineRunningChecker(Window window) {
        this.window = window;
    }

    @Override
    public boolean isEngineRunning() {
        return !window.isShouldClose();
    }
}
