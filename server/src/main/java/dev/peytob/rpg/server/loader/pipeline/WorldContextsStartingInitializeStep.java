package dev.peytob.rpg.server.loader.pipeline;

import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import dev.peytob.rpg.server.gameplay.service.context.EcsContextService;
import dev.peytob.rpg.server.loader.dto.WorldContextDto;
import dev.peytob.rpg.server.loader.service.loader.WorldContextsDataLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorldContextsStartingInitializeStep implements InitializingPipelineStep {

    private final WorldContextsDataLoader worldContextsDataLoader;

    private final EcsContextService ecsContextService;

    @Override
    public void execute() {
        // TODO Create additional worlds context sources (properties)
        // TODO Add world copies

        Collection<WorldContextDto> worldContexts = worldContextsDataLoader.loadResources();

        log.info("Starting {} loaded ECS context runners", worldContexts.size());

        worldContexts.forEach(worldContextDto ->
            ecsContextService.startEcsContextRunner(worldContextDto.world(), worldContextDto.world().id()));
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
