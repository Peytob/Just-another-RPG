package dev.peytob.rpg.client.archetype.template.common;

import dev.peytob.rpg.client.archetype.componentFactory.common.PositionComponentFactory;
import dev.peytob.rpg.engine.module.archetype.loader.provider.classpath.ArchetypeTemplate;
import dev.peytob.rpg.engine.module.archetype.loader.provider.classpath.ClasspathArchetypeFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class PlayerArchetypeTemplateFactory implements ClasspathArchetypeFactory {

    private final PositionComponentFactory positionComponentFactory;

    public PlayerArchetypeTemplateFactory(PositionComponentFactory positionComponentFactory) {
        this.positionComponentFactory = positionComponentFactory;
    }

    @Override
    public ArchetypeTemplate create() {
        return new ArchetypeTemplate(
            "player",
            List.of(positionComponentFactory)
        );
    }
}
