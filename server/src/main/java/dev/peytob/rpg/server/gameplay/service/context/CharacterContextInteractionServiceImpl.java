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

    private final WorldContextService worldContextService;

    private final CharacterSessionRepository characterSessionRepository;

    @Override
    public void enterToContext(Character character, String worldContextRunnerName) {
        log.info("Character {} entering to context {}", character.name(), worldContextRunnerName);

        if (characterSessionRepository.containsCharacterSession(character)) {
            log.error("Character {} is already connected to context runner", character.name());
            throw new IllegalStateException("Character already in context");
        }

        WorldContextRunner worldContextRunner = worldContextService.getWorldContextRunner(worldContextRunnerName);
        worldContextRunner.executeBeforeFrame(ecsContext -> createNewCharacter(ecsContext, character));
    }

    private void createNewCharacter(EcsContext ecsContext, Character character) {
        log.info("Creating character {} world context entities", character.name());

        ecsContext.createEntity("Character " + character.name() + " (" + character.id() + ")");
    }
}
