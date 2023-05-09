package dev.peytob.rpg.client.module.graphic.archetype.componentFactory;

import dev.peytob.rpg.client.module.graphic.context.component.CameraComponent;
import dev.peytob.rpg.client.module.graphic.model.Camera;
import dev.peytob.rpg.engine.module.archetype.loader.componentFactory.ComponentFactory;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.stereotype.Component;

@Component
public final class CameraComponentFactory implements ComponentFactory<CameraComponent> {

    @Override
    public CameraComponent create() {
        return new CameraComponent(new Camera(Vectors.immutableVec2(), Vectors.immutableVec2i()));
    }
}
