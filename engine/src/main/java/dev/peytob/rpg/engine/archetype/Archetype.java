package dev.peytob.rpg.engine.archetype;

import dev.peytob.rpg.ecs.entity.Entity;

import java.util.Collection;

public interface Archetype {

    Entity injectComponents(Entity entity);

    Collection<ComponentFactory<?>> getComponentFactories();
}
