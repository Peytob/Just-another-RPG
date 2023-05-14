package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.ecs.system.System;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;

class SimpleEcsContextBuilder implements EcsContextBuilder {

    private final Collection<OrderedSystem> systems;

    private final Collection<EntityInitializer> entityInitializers;

    public SimpleEcsContextBuilder() {
        this.systems = new ArrayList<>();
        this.entityInitializers = new ArrayList<>();
    }

    @Override
    public EcsContextBuilder addSystem(System system, Integer order) {
        return addSystem(OrderedSystem.wrap(system, order));
    }

    @Override
    public EcsContextBuilder addSystem(OrderedSystem orderedSystem) {
        Objects.requireNonNull(orderedSystem, "Ordered system should not be null");
        systems.add(orderedSystem);
        return this;
    }

    @Override
    public EcsContextBuilder initializeEntity(BiConsumer<Entity, EcsContext> initializer) {
        return initializeEntity(initializer, null);
    }

    @Override
    public EcsContextBuilder initializeEntity(BiConsumer<Entity, EcsContext> initializer, String customId) {
        Objects.requireNonNull(initializer, "Entity initializer should not be null");
        entityInitializers.add(new EntityInitializer(initializer, customId));
        return this;
    }

    @Override
    public EcsContext build() {
        EcsContext context = EcsContexts.mutable(systems);

        entityInitializers.forEach(initializer -> {
            Entity entity = context.createEntity(initializer.id());
            initializer.initializer().accept(entity, context);
        });

        return context;
    }

    private record EntityInitializer(
        BiConsumer<Entity, EcsContext> initializer,
        String id
    ) {}
}
