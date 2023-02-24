package dev.peytob.rpg.engine.context.system;

import dev.peytob.rpg.ecs.system.System;
import dev.peytob.rpg.engine.exception.system.SystemNotFound;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * facade that allows access to all available systems for easier construction of engine states, scripts ant other
 */
@Component
public final class SystemFactory {

    private final Map<Class<? extends System>, System> systems;

    public SystemFactory(Map<Class<? extends System>, System> systems) {
        this.systems = systems;
    }

    @SuppressWarnings("unchecked")
    public <T extends System> T getSystem(Class<T> systemClass) {
        T system = (T) systems.get(systemClass);

        if (null == system) {
            throw new SystemNotFound("System with class " + systemClass.getSimpleName() + " not found in context");
        }

        return system;
    }

    public Integer getSystemsCount() {
        return systems.size();
    }
}
