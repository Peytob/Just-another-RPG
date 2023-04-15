package dev.peytob.rpg.client.archetype.template.graphic;

import dev.peytob.rpg.client.archetype.componentFactory.common.PositionComponentFactory;
import dev.peytob.rpg.client.archetype.componentFactory.graphic.CameraComponentFactory;
import dev.peytob.rpg.client.context.component.basic.PositionComponent;
import dev.peytob.rpg.client.context.component.graphic.CameraComponent;
import dev.peytob.rpg.client.model.graphic.Camera;
import dev.peytob.rpg.engine.loader.archetype.provider.classpath.ArchetypeTemplate;
import dev.peytob.rpg.engine.loader.archetype.provider.classpath.ClasspathArchetypeFactory;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class CameraArchetypeTemplateFactory implements ClasspathArchetypeFactory {

    private final PositionComponentFactory positionComponentFactory;

    private final CameraComponentFactory cameraComponentFactory;

    public CameraArchetypeTemplateFactory(PositionComponentFactory positionComponentFactory,
                                          CameraComponentFactory cameraComponentFactory) {
        this.positionComponentFactory = positionComponentFactory;
        this.cameraComponentFactory = cameraComponentFactory;
    }

    @Override
    public ArchetypeTemplate create() {
        return new ArchetypeTemplate(
            "camera",
            List.of(positionComponentFactory, cameraComponentFactory)
        );
    }
}
