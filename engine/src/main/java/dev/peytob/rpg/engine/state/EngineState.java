package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.engine.context.template.EcsContextTemplate;

public interface EngineState {

    EcsContextTemplate getEcsContextTemplate();

    String getName();
}
