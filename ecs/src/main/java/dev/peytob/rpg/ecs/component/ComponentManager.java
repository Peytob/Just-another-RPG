package dev.peytob.rpg.ecs.component;

import java.util.Collection;

public interface ComponentManager {

    void addComponent(Component component);

    boolean removeComponent(Component component);

    Collection<Class<? extends Component>> getComponentTypes();

    <T extends Component> Collection<T> getComponentsByType(Class<T> componentType);

    int getComponentsCount();

    void clear();
}
