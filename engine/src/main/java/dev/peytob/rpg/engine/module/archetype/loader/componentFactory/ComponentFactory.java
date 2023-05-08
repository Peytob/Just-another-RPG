package dev.peytob.rpg.engine.module.archetype.loader.componentFactory;

import dev.peytob.rpg.ecs.component.Component;

public interface ComponentFactory<T extends Component> {

    T create();
}
