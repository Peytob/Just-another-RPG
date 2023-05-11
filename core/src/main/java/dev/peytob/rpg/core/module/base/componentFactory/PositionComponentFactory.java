package dev.peytob.rpg.core.module.base.componentFactory;

import dev.peytob.rpg.core.module.base.context.component.PositionComponent;
import dev.peytob.rpg.engine.module.archetype.loader.componentFactory.ComponentFactory;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.stereotype.Component;

@Component
public final class PositionComponentFactory implements ComponentFactory<PositionComponent> {

    @Override
    public PositionComponent create() {
        return new PositionComponent(Vectors.immutableVec2());
    }
}
