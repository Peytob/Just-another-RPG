package dev.peytob.rpg.client.archetype.componentFactory.common;

import dev.peytob.rpg.client.context.component.basic.PositionComponent;
import dev.peytob.rpg.engine.loader.archetype.componentFactory.ComponentFactory;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.stereotype.Component;

@Component
public final class PositionComponentFactory implements ComponentFactory<PositionComponent> {

    @Override
    public PositionComponent create() {
        return new PositionComponent(Vectors.immutableVec2());
    }
}
