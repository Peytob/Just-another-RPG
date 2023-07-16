package dev.peytob.rpg.client.module.graphic.pipeline;

import dev.peytob.rpg.client.module.graphic.model.telemetry.GraphicTelemetry;
import dev.peytob.rpg.client.module.graphic.service.telemetry.GraphicTelemetryCollectingService;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ShowDebugGraphicTelemetryInitializingStep implements InitializingPipelineStep {

    private static final Logger logger = LoggerFactory.getLogger(ShowDebugGraphicTelemetryInitializingStep.class);

    private final GraphicTelemetryCollectingService graphicTelemetryCollectingService;

    public ShowDebugGraphicTelemetryInitializingStep(GraphicTelemetryCollectingService graphicTelemetryCollectingService) {
        this.graphicTelemetryCollectingService = graphicTelemetryCollectingService;
    }

    @Override
    public void execute() {
        if (!logger.isDebugEnabled()) {
            return;
        }

        GraphicTelemetry graphicTelemetry = graphicTelemetryCollectingService.collectGraphicTelemetry();

        logger.debug("Graphic API name: {}", graphicTelemetry.apiName());
        logger.debug("Graphic API version: {}", graphicTelemetry.apiVersion());
        logger.debug("Graphic renderer name: {}", graphicTelemetry.renderer());
        logger.debug("Graphic vendor name: {}", graphicTelemetry.vendor());
        logger.debug("Graphic shader language release number: {}", graphicTelemetry.shadingLanguageVersion());
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
