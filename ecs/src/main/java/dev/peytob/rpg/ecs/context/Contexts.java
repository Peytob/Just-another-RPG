package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.component.ComponentManagers;
import dev.peytob.rpg.ecs.entity.EntityManagers;
import dev.peytob.rpg.ecs.entity.filer.EntityManagerFilteredDecorator;
import dev.peytob.rpg.ecs.entity.filer.FilterManagers;
import dev.peytob.rpg.ecs.event.EventManagers;
import dev.peytob.rpg.ecs.system.SystemManagers;

public enum Contexts {;

    public static EcsContext mutable() {
        EntityManagerFilteredDecorator filteredEntityManager = FilterManagers.decorate(EntityManagers.mutable());

        return new MutableEcsContext(
                ComponentManagers.mutable(),
                filteredEntityManager,
                filteredEntityManager,
                SystemManagers.mutable(),
                EventManagers.mutable()
        );
    }
}
