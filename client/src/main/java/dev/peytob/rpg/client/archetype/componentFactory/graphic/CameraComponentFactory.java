package dev.peytob.rpg.client.archetype.componentFactory.graphic;

import dev.peytob.rpg.client.context.component.graphic.CameraComponent;
import dev.peytob.rpg.client.model.graphic.Camera;
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
