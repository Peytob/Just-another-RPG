package dev.peytob.rpg.client.service.handler.event.window;

import dev.peytob.rpg.client.model.control.KeyAction;
import dev.peytob.rpg.client.model.control.KeyModifiers;
import dev.peytob.rpg.client.model.control.KeyboardKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public final class WindowKeyLoggerEventHandler extends WindowKeyEventHandler {

    private final Logger logger = LoggerFactory.getLogger(WindowKeyLoggerEventHandler.class);

    @Override
    void handle(KeyboardKey key, KeyAction action, KeyModifiers modifiers) {
        logger.info("Key {} {} with modifiers {}", key, action, modifiers);
    }
}
