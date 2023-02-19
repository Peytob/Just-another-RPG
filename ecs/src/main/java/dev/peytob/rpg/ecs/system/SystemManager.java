package dev.peytob.rpg.ecs.system;

import java.util.Collection;

public interface SystemManager {

    Collection<System> getAll();

    boolean register(System system, int order);

    boolean register(OrderedSystem system);

    boolean remove(System system);

    boolean contains(Class<? extends System> systemClass);

    boolean contains(System system);

    void clear();

    int getSize();
}
