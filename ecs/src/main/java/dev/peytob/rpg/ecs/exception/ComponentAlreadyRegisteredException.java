package dev.peytob.rpg.ecs.exception;

import dev.peytob.rpg.ecs.component.Component;

public final class ComponentAlreadyRegisteredException extends RuntimeException {

    private final Component component;

    public ComponentAlreadyRegisteredException(String message, Component component) {
        super(message);
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
