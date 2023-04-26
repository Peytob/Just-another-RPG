package dev.peytob.rpg.client.state;

import dev.peytob.rpg.client.context.component.relation.EntitiesPositionsSyncComponent;
import dev.peytob.rpg.client.context.system.control.PlayerMovingSystem;
import dev.peytob.rpg.client.context.system.control.WindowCloseButtonHandlingSystem;
import dev.peytob.rpg.client.context.system.graphic.rendering.FramebufferClearSystem;
import dev.peytob.rpg.client.context.system.graphic.rendering.MeshRenderingSystem;
import dev.peytob.rpg.client.context.system.graphic.window.WindowClosingHandlingSystem;
import dev.peytob.rpg.client.context.system.graphic.window.WindowEventPoolingSystem;
import dev.peytob.rpg.client.context.system.graphic.window.WindowSwappingBuffersSystem;
import dev.peytob.rpg.client.context.system.relationship.PositionsSyncSystem;
import dev.peytob.rpg.core.model.location.tilemap.Tilemaps;
import dev.peytob.rpg.client.context.component.level.TilemapComponent;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.ecs.system.OrderedSystem;
import dev.peytob.rpg.engine.context.system.SystemFactory;
import dev.peytob.rpg.engine.loader.archetype.componentFactory.ComponentFactory;
import dev.peytob.rpg.engine.repositry.Repository;
import dev.peytob.rpg.engine.resource.Archetype;
import dev.peytob.rpg.engine.state.EngineState;
import dev.peytob.rpg.math.vector.Vectors;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public final class InGameEngineState implements EngineState {

    private final SystemFactory systemFactory;

    private final Repository<Archetype> archetypeRepository;

    public InGameEngineState(SystemFactory systemFactory, Repository<Archetype> archetypeRepository) {
        this.systemFactory = systemFactory;
        this.archetypeRepository = archetypeRepository;
    }

    @Override
    public String getName() {
        return "InGame";
    }

    @Override
    public Collection<OrderedSystem> getSystems() {
        // TODO Make builder or something else

        int position = 0;

        return List.of(
            OrderedSystem.wrap(systemFactory.getSystem(WindowEventPoolingSystem.class), position++),

            OrderedSystem.wrap(systemFactory.getSystem(WindowCloseButtonHandlingSystem.class), position++),
            OrderedSystem.wrap(systemFactory.getSystem(PlayerMovingSystem.class), position++),

            OrderedSystem.wrap(systemFactory.getSystem(PositionsSyncSystem.class), position++),

            OrderedSystem.wrap(systemFactory.getSystem(FramebufferClearSystem.class), position++),
            OrderedSystem.wrap(systemFactory.getSystem(MeshRenderingSystem.class), position++),
            OrderedSystem.wrap(systemFactory.getSystem(WindowSwappingBuffersSystem.class), position++),

            OrderedSystem.wrap(systemFactory.getSystem(WindowClosingHandlingSystem.class), position++)
        );
    }

    @Override
    public void onSetUp(EcsContext ecsContext) {
        Entity camera = createEntity(ecsContext, "camera");
        Entity player = createEntity(ecsContext, "player");
        Entity tilemap = ecsContext.createEntity("tilemap");
        tilemap.bindComponent(new TilemapComponent(Tilemaps.mutable(Vectors.immutableVec2i(32, 32))));

        Entity cameraPlayerSync = ecsContext.createEntity("camera_player_sync");
        cameraPlayerSync.bindComponent(new EntitiesPositionsSyncComponent(player, camera));
    }

    @Override
    public void onTearDown(EcsContext ecsContext) {
        // Nothing
    }

    private Entity createEntity(EcsContext ecsContext, String archetypeId) {
        Archetype archetype = archetypeRepository.getById(archetypeId);
        Entity entity = ecsContext.createEntity(archetypeId);

        archetype.componentFactories().stream()
            .map(ComponentFactory::create)
            .forEach(entity::bindComponent);

        return entity;
    }
}
