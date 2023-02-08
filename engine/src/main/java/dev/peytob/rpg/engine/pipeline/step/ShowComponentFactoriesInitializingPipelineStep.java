package dev.peytob.rpg.engine.pipeline.step;

import dev.peytob.rpg.engine.loader.archetype.componentFactory.ComponentAbstractFactory;
import dev.peytob.rpg.engine.pipeline.DefaultInitializingPipelineOrders;
import dev.peytob.rpg.engine.pipeline.InitializingPipelineStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Profile("debug")
public final class ShowComponentFactoriesInitializingPipelineStep implements InitializingPipelineStep {

    private final static Logger logger = LoggerFactory.getLogger(ShowComponentFactoriesInitializingPipelineStep.class);

    private final List<ComponentAbstractFactory<?, ?>> abstractFactories;

    public ShowComponentFactoriesInitializingPipelineStep(List<ComponentAbstractFactory<?, ?>> abstractFactories) {
        this.abstractFactories = List.copyOf(abstractFactories);
    }

    @Override
    public void execute() {
        if (abstractFactories.isEmpty()) {
            logger.debug("Component abstract factories not found");
            return;
        }

        String factoriesListString = abstractFactories.stream()
                .map(factory -> factory.getSerializedName() + " -> " + factory.getPatternClass().getSimpleName())
                .collect(Collectors.joining("; "));

        logger.debug("Loaded component abstract factories (serial name -> class): {}", factoriesListString);
    }

    @Override
    public int getOrder() {
        return DefaultInitializingPipelineOrders.BEFORE_ALL;
    }
}
