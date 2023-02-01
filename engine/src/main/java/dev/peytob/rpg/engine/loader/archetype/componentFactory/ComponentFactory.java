package dev.peytob.rpg.engine.loader.archetype.componentFactory;

import dev.peytob.rpg.ecs.component.Component;

public interface ComponentFactory<T extends Component> {

    T create();
}
