package dev.peytob.rpg.ecs.entity.filer;

import dev.peytob.rpg.ecs.entity.Entity;

public interface Filter {

    boolean test(Entity entity);
}
