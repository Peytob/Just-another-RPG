package dev.peytob.rpg.ecs.component;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.peytob.rpg.ecs.exception.ComponentAlreadyRegisteredException;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * This implementation just wraps guava multimap collection of components.
 */
final class SimpleComponentManager implements ComponentManager {

    private final Multimap<Class<? extends Component>, Component> components;

    SimpleComponentManager() {
        this.components = HashMultimap.create();
    }

    @Override
    public void addComponent(Component component) {
        if (components.containsEntry(component.getClass(), component)) {
            throw new ComponentAlreadyRegisteredException("Component already registered", component);
        }

        components.put(component.getClass(), component);
    }

    @Override
    public boolean removeComponent(Component component) {
        return components.remove(component.getClass(), component);
    }

    @Override
    public Collection<Class<? extends Component>> getComponentTypes() {
        return Collections.unmodifiableSet(components.keySet());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Component> Collection<T> getComponentsByType(Class<T> componentType) {
        Collection<Component> typedComponents = components.get(componentType);

        return Collections.unmodifiableCollection((Collection<T>) typedComponents);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Component> Optional<T> getSingletonComponentByType(Class<T> componentType) {
        return (Optional<T>) components
            .get(componentType)
            .stream()
            .findFirst();
    }

    @Override
    public int getComponentsCount() {
        return components.size();
    }

    @Override
    public void clear() {
        components.clear();
    }
}
