package dev.peytob.rpg.ecs.component;

import java.util.Collection;

final class UnmodifiableComponentManager implements ComponentManager {

    private final ComponentManager componentManager;

    UnmodifiableComponentManager(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    @Override
    public void addComponent(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeComponent(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Class<? extends Component>> getComponentTypes() {
        return componentManager.getComponentTypes();
    }

    @Override
    public <T extends Component> Collection<T> getComponentsByType(Class<T> componentType) {
        return componentManager.getComponentsByType(componentType);
    }

    @Override
    public int getComponentsCount() {
        return componentManager.getComponentsCount();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
