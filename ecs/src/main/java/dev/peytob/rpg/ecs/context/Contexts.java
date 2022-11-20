package dev.peytob.rpg.ecs.context;

import dev.peytob.rpg.ecs.component.ComponentManagers;
import dev.peytob.rpg.ecs.entity.EntityManagers;
import dev.peytob.rpg.ecs.system.SystemManagers;

public enum Contexts {;

    public static EcsContext mutable() {
        return new MutableEcsContext(
                ComponentManagers.mutable(),
                EntityManagers.mutable(),
                SystemManagers.mutable()
        );
    }
}
