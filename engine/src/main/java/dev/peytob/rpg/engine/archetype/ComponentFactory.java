package dev.peytob.rpg.engine.archetype;

import dev.peytob.rpg.ecs.component.Component;

public interface ComponentFactory<T extends Component> {

    T create();
}
