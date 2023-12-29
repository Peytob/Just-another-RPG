package dev.peytob.rpg.server.loader.pipeline;

import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import dev.peytob.rpg.server.loader.dto.WorldContextDto;
import dev.peytob.rpg.server.loader.service.loader.WorldContextsDataLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class WorldContextsStartingInitializeStep implements InitializingPipelineStep {

    private final WorldContextsDataLoader worldContextsDataLoader;

    @Override
    public void execute() {
        // TODO Create additional worlds context sources (properties)

        Collection<WorldContextDto> worldContexts = worldContextsDataLoader.loadResources();

        worldContexts.forEach(worldContextDto -> {

        });
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
