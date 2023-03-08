package dev.peytob.rpg.client.event.handler.window.mouse.position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WindowCursorPositionLoggerEventHandler extends WindowCursorPositionEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(WindowCursorPositionLoggerEventHandler.class);

    @Override
    public void handle(double newX, double newY) {
        logger.info("Cursor position event: {}, {}", newX, newY);
    }
}
