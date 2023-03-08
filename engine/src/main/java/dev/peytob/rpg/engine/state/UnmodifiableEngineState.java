package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.engine.context.template.EcsContextTemplate;

final class UnmodifiableEngineState implements EngineState {

    private final String name;

    private final EcsContextTemplate ecsContextTemplate;

    public UnmodifiableEngineState(String name, EcsContextTemplate ecsContextTemplate) {
        this.ecsContextTemplate = ecsContextTemplate;
        this.name = name;
    }

    @Override
    public EcsContextTemplate getEcsContextTemplate() {
        return ecsContextTemplate;
    }

    @Override
    public String getName() {
        return name;
    }
}
