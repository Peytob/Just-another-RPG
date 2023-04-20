package dev.peytob.rpg.ecs.system;

import java.util.Collection;

public interface SystemManager {

    Collection<System> getAllSystems();

    boolean registerSystem(System system, int order);

    boolean registerSystem(OrderedSystem system);

    boolean removeSystem(System system);

    boolean containsSystem(Class<? extends System> systemClass);

    boolean containsSystem(System system);

    int getSystemsCount();

    void clear();
}
