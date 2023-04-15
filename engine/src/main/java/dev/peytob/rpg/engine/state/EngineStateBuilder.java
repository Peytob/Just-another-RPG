package dev.peytob.rpg.engine.state;

import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class EngineStateBuilder {

    private List<OrderedSystem> systems;

    private String name;

    public EngineStateBuilder() {
        systems = new ArrayList<>();
    }

    public EngineStateBuilder nextSystem(System system) {
        return addSystem(OrderedSystem.wrap(system, systems.size()));
    }

    public EngineStateBuilder addSystem(OrderedSystem orderedSystem) {
        systems.add(orderedSystem);
        return this;
    }

    public EngineStateBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EngineState build() {
        Objects.requireNonNull(name, "Name should be specified!");

        return new EngineState(name, systems);
    }
}
