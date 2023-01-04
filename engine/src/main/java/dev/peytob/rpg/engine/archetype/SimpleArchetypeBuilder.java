package dev.peytob.rpg.engine.archetype;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static dev.peytob.rpg.engine.utils.reflection.GenericReflectionUtils.resolveTypeArgument;

final class SimpleArchetypeBuilder implements ArchetypeBuilder {

    private final Collection<ComponentFactory<?>> componentFactories;

    public SimpleArchetypeBuilder() {
        this.componentFactories = new HashSet<>();
    }

    @Override
    public boolean addComponentFactory(ComponentFactory<?> componentFactory) {
        if (containsSameFactory(componentFactory)) {
            return false;
        }

        return componentFactories.add(componentFactory);
    }

    @Override
    public Collection<ComponentFactory<?>> getComponentFactories() {
        return Collections.unmodifiableCollection(componentFactories);
    }

    @Override
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
                    Class<?> existsFactoryComponentType =
                            resolveTypeArgument(existsFactory.getClass(),ComponentFactory.class);
                    return existsFactoryComponentType.equals(componentClass);
                });
    }
}
