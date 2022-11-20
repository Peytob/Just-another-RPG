package dev.peytob.rpg.ecs.system;

import java.util.Collection;

final class UnmodifiableSystemManager implements SystemManager {

    private final SystemManager systemManager;

    UnmodifiableSystemManager(SystemManager systemManager) {
        this.systemManager = systemManager;
    }

    @Override
    public Collection<System> getAll() {
        return systemManager.getAll();
    }

    @Override
    public boolean register(System system, int order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(System system) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Class<? extends System> systemClass) {
        return systemManager.contains(systemClass);
    }

    @Override
    public boolean contains(System system) {
        return systemManager.contains(system);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getSize() {
        return systemManager.getSize();
    }
}
