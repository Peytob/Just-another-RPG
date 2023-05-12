package dev.peytob.rpg.client.module.graphic.archetype.template;

import dev.peytob.rpg.core.module.base.componentFactory.PositionComponentFactory;
import dev.peytob.rpg.client.module.graphic.archetype.componentFactory.CameraComponentFactory;
import dev.peytob.rpg.engine.module.archetype.loader.provider.classpath.ArchetypeTemplate;
import dev.peytob.rpg.engine.module.archetype.loader.provider.classpath.ClasspathArchetypeFactory;
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
