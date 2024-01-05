package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.server.gameplay.repository.CharacterSessionRepository;
import dev.peytob.rpg.server.gameplay.resource.Character;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterContextInteractionServiceImpl implements CharacterContextInteractionService {

    private final EcsContextService ecsContextService;

    private final CharacterSessionRepository characterSessionRepository;

    @Override
    public void enterToContext(Character character, String contextRunnerName) {
        log.info("Character {} entering to context {}", character.name(), contextRunnerName);

        if (characterSessionRepository.containsCharacterSession(character)) {
            log.error("Character {} is already connected to context runner", character.name());
            throw new IllegalStateException("Character already in context");
        }

        EcsContextRunner ecsContextRunner = ecsContextService.getEcsContextRunner(contextRunnerName);
        ecsContextRunner.executeBeforeFrame(ecsContext -> createNewCharacter(ecsContext, character));
    }

    private void createNewCharacter(EcsContext ecsContext, Character character) {
        log.info("Creating character {} ecs context entities", character.name());

        ecsContext.createEntity("Character " + character.name() + " (" + character.id() + ")");
    }
}
