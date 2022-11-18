package dev.peytob.rpg.ecs.system;

import dev.peytob.rpg.ecs.context.EcsContext;

final class OrderedSystem implements System {

    static OrderedSystem wrap(System system, Integer order) {
        if (system instanceof OrderedSystem orderedSystem) {
            if (orderedSystem.getSystem().equals(system)) {
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
