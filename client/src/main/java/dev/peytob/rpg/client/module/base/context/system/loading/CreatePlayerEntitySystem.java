package dev.peytob.rpg.client.module.base.context.system.loading;

import dev.peytob.rpg.client.context.component.CopyEntityOnChangeStateFlag;
import dev.peytob.rpg.client.fsm.annotation.IncludeInState;
import dev.peytob.rpg.client.fsm.state.instance.InGameLoadingState;
import dev.peytob.rpg.core.module.base.context.component.position.PositionComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.System;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static dev.peytob.rpg.client.module.base.context.constant.DefaultContextEntitiesIds.PLAYER_ENTITY_ID;
import static dev.peytob.rpg.ecs.model.SystemDefaultOrder.BEFORE_ALL;
import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;

@Component
@IncludeInState(state = InGameLoadingState.class, order = BEFORE_ALL)
public class CreatePlayerEntitySystem implements System {

    @Override
    public void execute(EcsContext context) {
        Optional<Entity> optionalPlayerEntity = context.getEntityById(PLAYER_ENTITY_ID);

        if (optionalPlayerEntity.isEmpty()) {
            Entity playerEntity = context.createEntity(PLAYER_ENTITY_ID);
            playerEntity.bindComponent(new PositionComponent(immutableVec2()));
            playerEntity.bindComponent(new CopyEntityOnChangeStateFlag());
        }
    }
}
