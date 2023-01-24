package dev.peytob.rpg.engine.resource;

import dev.peytob.rpg.engine.loader.archetype.componentFactory.ComponentFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils.resolveTypeArgument;

public record Archetype(
        Integer id,
        String textId,
        Collection<ComponentFactory<?>> componentFactories
) implements Resource {

    public Archetype {
        componentFactories = List.copyOf(componentFactories);
    }

    public static final class ArchetypeBuilder {

        private final Collection<ComponentFactory<?>> componentFactories;

        public ArchetypeBuilder() {
            this.componentFactories = new HashSet<>();
        }

        public boolean addComponentFactory(ComponentFactory<?> componentFactory) {
            if (containsSameFactory(componentFactory)) {
                return false;
            }

            return componentFactories.add(componentFactory);
        }

        public Collection<ComponentFactory<?>> getComponentFactories() {
            return Collections.unmodifiableCollection(componentFactories);
        }

        public Archetype build(Integer id, String textId) {
            return new Archetype(id, textId, componentFactories);
        }

        private boolean containsSameFactory(ComponentFactory<?> componentFactory) {
            if (componentFactories.contains(componentFactory)) {
                return true;
            }

            Class<?> componentClass = resolveTypeArgument(componentFactory.getClass(), ComponentFactory.class);

            return componentFactories.stream()
                    .anyMatch(existsFactory -> {
                        Class<?> factoryType = resolveTypeArgument(existsFactory.getClass(),ComponentFactory.class);
                        return factoryType.equals(componentClass);
                    });
        }
    }
}
