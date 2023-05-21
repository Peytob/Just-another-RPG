package dev.peytob.rpg.ecs.system;

import dev.peytob.rpg.ecs.exception.SystemAlreadyRegisteredException;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

final class SimpleSystemManager implements SystemManager {

    // TODO Make new List implementation - SortedList
    private final List<OrderedSystem> sortedSystems;

    private final Collection<System> systems;

    private final AtomicBoolean isSystemsSorted;

    SimpleSystemManager() {
        this.sortedSystems = Collections.synchronizedList(new ArrayList<>());
        this.systems = Collections.synchronizedList(new ArrayList<>());
        this.isSystemsSorted = new AtomicBoolean(false);
    }

    @Override
    public Collection<System> getAllSystems() {
        if (!isSystemsSorted.get()) {
            sortedSystems.sort(Comparator.comparingInt(OrderedSystem::getOrder));
            systems.clear();
            sortedSystems.forEach(orderedSystem -> systems.add(orderedSystem.getSystem()));
            isSystemsSorted.set(true);
        }

        return Collections.unmodifiableCollection(systems);
    }

    @Override
    public boolean registerSystem(System system, int order) {
        return registerSystem(OrderedSystem.wrap(system, order));
    }

    @Override
    public boolean registerSystem(OrderedSystem system) {
        if (containsSystem(system.getSystem())) {
            throw new SystemAlreadyRegisteredException(system.getSystem());
        }

        sortedSystems.add(system);
        systems.add(system.getSystem());
        isSystemsSorted.set(false);

        return true;
    }

    @Override
    public boolean removeSystem(System system) {
        if (!containsSystem(system)) {
            throw new SystemAlreadyRegisteredException(system);
        }

        systems.remove(system);
        OrderedSystem ordered = sortedSystems
                .stream()
                .filter(orderedSystem -> orderedSystem.getSystem().equals(system))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        sortedSystems.remove(ordered);

        isSystemsSorted.set(false);

        return true;
    }

    @Override
    public boolean containsSystem(Class<? extends System> systemClass) {
        return systems
                .stream()
                .anyMatch(system -> system.getClass().equals(systemClass));
    }

    @Override
    public boolean containsSystem(System system) {
        return systems.contains(system) || containsSystem(system.getClass());
    }

    @Override
    public void clear() {
        systems.clear();
        sortedSystems.clear();
    }

    @Override
    public int getSystemsCount() {
        return systems.size();
    }
}
