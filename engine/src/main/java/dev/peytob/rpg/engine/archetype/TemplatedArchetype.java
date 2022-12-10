package dev.peytob.rpg.engine.archetype;

import dev.peytob.rpg.ecs.component.Component;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.engine.exception.ComponentsInjectionException;

import java.util.Collection;
import java.util.List;

final class TemplatedArchetype implements Archetype {

    private final Collection<ComponentFactory<?>> componentFactories;

    public TemplatedArchetype(Collection<ComponentFactory<?>> componentFactories) {
        // List::copyOf returns unmodifiable collection
        this.componentFactories = List.copyOf(componentFactories);
    }

    @Override
    public Entity injectComponents(Entity entity) {
        if (!entity.isEmpty()) {
            throw new ComponentsInjectionException("Entity should be empty for components injection!");
        }

        componentFactories.forEach(componentFactory -> {
            Component component = componentFactory.create();
            entity.bindComponent(component);
        });

        return entity;
    }

    @Override
    public Collection<ComponentFactory<?>> getComponentFactories() {
        return componentFactories;
    }
}
