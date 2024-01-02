package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.server.gameplay.resource.Character;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterContextInteractionServiceImpl implements CharacterContextInteractionService {

    private final EcsContextService ecsContextService;

    @Override
    public void enterToContext(Character character, String testWorld) {
        log.info("Character {} entering to context {}", character.name(), testWorld);
        EcsContextRunner ecsContextRunner = ecsContextService.getEcsContextRunner(testWorld);
        ecsContextRunner.executeBeforeFrame(ecsContext -> createNewCharacter(ecsContext, character));
    }

    private void createNewCharacter(EcsContext ecsContext, Character character) {
        log.info("Creating character {} ecs context entities", character.name());
    }
}
