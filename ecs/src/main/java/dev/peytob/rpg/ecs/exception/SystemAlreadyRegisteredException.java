package dev.peytob.rpg.ecs.exception;

import dev.peytob.rpg.ecs.system.System;

public class SystemAlreadyRegisteredException extends RuntimeException {

    private final System system;

    public SystemAlreadyRegisteredException(System system) {
        super("System " + system.getClass().getSimpleName() + " is already registered");
        this.system = system;
    }

    public System getSystem() {
        return system;
    }
}
