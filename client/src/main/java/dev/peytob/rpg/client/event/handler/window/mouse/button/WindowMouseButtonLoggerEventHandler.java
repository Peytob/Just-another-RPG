package dev.peytob.rpg.client.event.handler.window.mouse.button;

import dev.peytob.rpg.client.model.control.KeyAction;
import dev.peytob.rpg.client.model.control.KeyModifiers;
import dev.peytob.rpg.client.model.control.MouseButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WindowMouseButtonLoggerEventHandler extends WindowMouseButtonEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(WindowMouseButtonLoggerEventHandler.class);

    @Override
    public void handle(MouseButton button, KeyAction action, KeyModifiers modifiers) {
        logger.info("Mouse button {} {} with modifiers {}", button, action, modifiers);
    }
}
