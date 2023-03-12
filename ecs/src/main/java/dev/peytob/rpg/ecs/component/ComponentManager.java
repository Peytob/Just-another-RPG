package dev.peytob.rpg.ecs.component;

import java.util.Collection;

public interface ComponentManager {

    void register(Component component);

    boolean remove(Component component);

    Collection<Class<? extends Component>> getTypes();

    <T extends Component> Collection<T> getAllByType(Class<T> componentClass);

    int getSize();

    void clear();
}
