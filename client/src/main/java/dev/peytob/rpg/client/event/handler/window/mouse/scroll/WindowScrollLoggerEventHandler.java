package dev.peytob.rpg.client.event.handler.window.mouse.scroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WindowScrollLoggerEventHandler extends WindowScrollEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(WindowScrollLoggerEventHandler.class);

    @Override
    public void handle(double xOffset, double yOffset) {
        logger.info("Scroll event. Offsets: {}, {}", xOffset, yOffset);
    }
}
