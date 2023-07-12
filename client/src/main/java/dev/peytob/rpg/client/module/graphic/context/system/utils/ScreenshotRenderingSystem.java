package dev.peytob.rpg.client.module.graphic.context.system.utils;

import dev.peytob.rpg.client.fsm.annotation.IncludeInAllStates;
import dev.peytob.rpg.client.module.control.context.event.ScreenshotEvent;
import dev.peytob.rpg.client.module.graphic.model.Image;
import dev.peytob.rpg.client.module.graphic.model.Window;
import dev.peytob.rpg.client.module.graphic.service.utils.ImageIOService;
import dev.peytob.rpg.client.module.graphic.service.vendor.ScreenshotService;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.system.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static dev.peytob.rpg.client.module.graphic.model.RenderSystemDefaultOrder.AFTER_MAIN_RENDERING;
import static dev.peytob.rpg.math.geometry.Rectangles.rectI;

@Component
@IncludeInAllStates(order = AFTER_MAIN_RENDERING - 1)
public class ScreenshotRenderingSystem implements System {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotRenderingSystem.class);

    private final ScreenshotService screenshotService;

    private final ImageIOService imageIOService;

    private final Window window;

    public ScreenshotRenderingSystem(ScreenshotService screenshotService, ImageIOService imageIOService, Window window) {
        this.screenshotService = screenshotService;
        this.imageIOService = imageIOService;
        this.window = window;
    }

    @Override
    public void execute(EcsContext context) {
        boolean isScreenshotEventPresented = !context.getEventsByType(ScreenshotEvent.class).isEmpty();

        if (isScreenshotEventPresented) {
            String filename = generateScreenshotFilename();
            logger.info("Making screenshot and saving to file {}", filename);
            Image image = screenshotService.makeScreenshot(rectI(0, 0, window.getWidth(), window.getHeight()));
            imageIOService.saveImageFile(filename, image);
        }
    }

    private String generateScreenshotFilename() {
        return LocalDateTime.now().toString();
    }
}
