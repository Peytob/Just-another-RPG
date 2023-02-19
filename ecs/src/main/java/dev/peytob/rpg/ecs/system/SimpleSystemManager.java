package dev.peytob.rpg.ecs.system;

import dev.peytob.rpg.ecs.exception.SystemAlreadyRegisteredException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final class SimpleSystemManager implements SystemManager {

    // TODO Make new List implementation - SortedList
    private final List<OrderedSystem> sortedSystems;

    private final Collection<System> systems;

    SimpleSystemManager() {
        this.sortedSystems = Collections.synchronizedList(new ArrayList<>());
        this.systems = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public Collection<System> getAll() {
        return Collections.unmodifiableCollection(systems);
    }

    @Override
    public boolean register(System system, int order) {
        return register(OrderedSystem.wrap(system, order));
    }

    @Override
    public boolean register(OrderedSystem system) {
        if (contains(system.getSystem())) {
            throw new SystemAlreadyRegisteredException(system.getSystem());
        }

        sortedSystems.add(system);
        systems.add(system.getSystem());

        return true;
    }

    @Override
    public boolean remove(System system) {
        if (!contains(system)) {
            throw new SystemAlreadyRegisteredException(system);
        }

        systems.remove(system);
        OrderedSystem ordered = sortedSystems
                .stream()
                .filter(orderedSystem -> orderedSystem.getSystem().equals(system))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        sortedSystems.remove(ordered);

        return true;
    }

    @Override
    public boolean contains(Class<? extends System> systemClass) {
        return systems
                .stream()
                .anyMatch(system -> system.getClass().equals(systemClass));
    }

    @Override
    public boolean contains(System system) {
        return systems.contains(system) || contains(system.getClass());
    }

    @Override
    public void clear() {
        systems.clear();
        sortedSystems.clear();
    }

    @Override
    public int getSize() {
        return systems.size();
    }
}
