package dev.peytob.rpg.ecs.component;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.peytob.rpg.ecs.exception.ComponentAlreadyRegisteredException;

import java.util.Collection;
import java.util.Collections;

/**
 * This implementation just wraps guava multimap collection of components.
 */
final class SimpleComponentManager implements ComponentManager {

    private final Multimap<Class<? extends Component>, Component> components;

    SimpleComponentManager() {
        this.components = HashMultimap.create();
    }

    @Override
    public void register(Component component) {
        if (components.containsEntry(component.getClass(), component)) {
            throw new ComponentAlreadyRegisteredException("Component already registered", component);
        }

        components.put(component.getClass(), component);
    }

    @Override
    public boolean remove(Component component) {
        return components.remove(component.getClass(), component);
    }

    @Override
    public Collection<Class<? extends Component>> getTypes() {
        return Collections.unmodifiableSet(components.keySet());
    }

    @Override
    public Collection<Component> getAllByType(Class<? extends Component> componentClass) {
        return Collections.unmodifiableCollection(components.get(componentClass));
    }

    @Override
    public int getSize() {
        return components.size();
    }

    @Override
    public void clear() {
        components.clear();
    }
}
