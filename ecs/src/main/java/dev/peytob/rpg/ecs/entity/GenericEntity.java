package dev.peytob.rpg.ecs.entity;

import dev.peytob.rpg.ecs.component.Component;
import dev.peytob.rpg.ecs.exception.ComponentAlreadyRegisteredException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This implementation just decorates Map collection.
 */
public final class GenericEntity implements Entity {

    private final String id;

    private final Map<Class<? extends Component>, Component> components;

    public GenericEntity(String id) {
        this.id = id;
        this.components = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Collection<Component> getComponents() {
        return components.values();
    }

    @Override
    public Collection<Class<? extends Component>> getComponentsTypes() {
        return components.keySet();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass) {
        Component component = components.get(componentClass);
        return component == null ? null : (T) component;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Component> T removeComponent(Class<T> componentClass) {
        Component component = components.remove(componentClass);
        return component == null ? null : (T) component;
    }

    @Override
    public void bindComponent(Component component) {
        Class<? extends Component> componentClass = component.getClass();

        if (components.containsKey(componentClass)) {
            throw new ComponentAlreadyRegisteredException("Entity already contains component with type " + componentClass.getSimpleName(), component);
        }

        components.put(componentClass, component);
    }
}
