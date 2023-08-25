package dev.peytob.rpg.server.base.service.control;

import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.server.base.resource.world.entity.Entity;

public interface EntityMovingService {

    void moveEntity(Entity entity, Vec2 direction);

    void moveEntity(Entity entity, Vec2 direction, float speed);
}
