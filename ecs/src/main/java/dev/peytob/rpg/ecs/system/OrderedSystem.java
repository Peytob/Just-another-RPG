package dev.peytob.rpg.ecs.system;

import dev.peytob.rpg.ecs.context.EcsContext;

import java.util.Objects;

public final class OrderedSystem implements System {

    public static OrderedSystem wrap(System system, Integer order) {
        Objects.requireNonNull(system, "System should be not null!");
        Objects.requireNonNull(system, "Order should be not null!");

        if (system instanceof OrderedSystem orderedSystem) {
            if (orderedSystem.getOrder().equals(order)) {
                return orderedSystem;
            }

            return new OrderedSystem(orderedSystem.getSystem(), order);
        }

        return new OrderedSystem(system, order);
    }

    private final System system;

    private final Integer order;

    private OrderedSystem(System system, Integer order) {
        this.system = system;
        this.order = order;
    }

    @Override
    public void execute(EcsContext context) {
        system.execute(context);
    }

    public System getSystem() {
        return system;
    }

    public Integer getOrder() {
        return order;
    }

}
