package dev.peytob.rpg.client.event.handler.window.key;

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
    public void handle(KeyboardKey key, KeyAction action, KeyModifiers modifiers) {
        logger.info("Key {} {} with modifiers {}", key, action, modifiers);
    }
}
