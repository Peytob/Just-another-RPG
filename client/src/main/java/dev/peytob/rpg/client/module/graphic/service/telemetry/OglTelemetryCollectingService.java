package dev.peytob.rpg.client.module.graphic.service.telemetry;

import dev.peytob.rpg.client.module.graphic.model.telemetry.GraphicTelemetry;
import org.springframework.stereotype.Service;

import static org.lwjgl.opengl.GL30.*;

@Service
public class OglTelemetryCollectingService implements GraphicTelemetryCollectingService {

    @Override
    public GraphicTelemetry collectGraphicTelemetry() {
        return new GraphicTelemetry(
            "OpenGL",
            glGetString(GL_VERSION),
            glGetString(GL_VENDOR),
            glGetString(GL_RENDERER),
            glGetString(GL_SHADING_LANGUAGE_VERSION)
        );
    }
}
