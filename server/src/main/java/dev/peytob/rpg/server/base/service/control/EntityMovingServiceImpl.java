package dev.peytob.rpg.server.base.service.control;

import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.server.base.resource.world.entity.Entity;
import org.springframework.stereotype.Service;

@Service
public class EntityMovingServiceImpl implements EntityMovingService {

    private static final float ENTITY_SPEED = 3.0f;

    @Override
    public void moveEntity(Entity entity, Vec2 direction) {
        // TODO Make entity speed calculations
        moveEntity(entity, direction, ENTITY_SPEED);
    }

    @Override
    public void moveEntity(Entity entity, Vec2 direction, float speed) {
        Vec2 position = entity.getPosition();

        Vec2 delta = direction.multiply(speed);
        Vec2 newPosition = position.plus(delta);
        entity.setPosition(newPosition);
    }
}
