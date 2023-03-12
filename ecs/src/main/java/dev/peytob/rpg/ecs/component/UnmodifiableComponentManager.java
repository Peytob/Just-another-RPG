package dev.peytob.rpg.ecs.component;

import java.util.Collection;

final class UnmodifiableComponentManager implements ComponentManager {

    private final ComponentManager componentManager;

    UnmodifiableComponentManager(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    @Override
    public void register(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Class<? extends Component>> getTypes() {
        return componentManager.getTypes();
    }

    @Override
    public <T extends Component> Collection<T> getAllByType(Class<T> componentClass) {
        return componentManager.getAllByType(componentClass);
    }

    @Override
    public int getSize() {
        return componentManager.getSize();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
