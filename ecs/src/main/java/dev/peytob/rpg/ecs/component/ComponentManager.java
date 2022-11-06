package dev.peytob.rpg.ecs.component;

import java.util.Collection;

public interface ComponentManager {

    void register(Component component);

    boolean remove(Component component);

    Collection<Class<? extends Component>> getTypes();

    Collection<Component> getAllByType(Class<? extends Component> componentClass);

    void clear();
}
