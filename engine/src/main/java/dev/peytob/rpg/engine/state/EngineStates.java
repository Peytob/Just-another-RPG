package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.engine.context.template.EcsContextTemplate;

public enum EngineStates {;

    public static EngineState create(String name, EcsContextTemplate ecsContextTemplate) {
        return new UnmodifiableEngineState(name, ecsContextTemplate);
    }
}
