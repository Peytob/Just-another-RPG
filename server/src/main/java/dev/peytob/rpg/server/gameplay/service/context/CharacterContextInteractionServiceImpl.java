package dev.peytob.rpg.server.gameplay.service.context;

import dev.peytob.rpg.core.gameplay.ecs.component.PositionComponent;
import dev.peytob.rpg.core.network.model.server.WorldState;
import dev.peytob.rpg.ecs.context.EcsContext;
import dev.peytob.rpg.ecs.entity.Entity;
import dev.peytob.rpg.math.vector.Vec2;
import dev.peytob.rpg.server.gameplay.repository.CharacterSessionRepository;
import dev.peytob.rpg.server.gameplay.resource.Character;
import dev.peytob.rpg.server.gameplay.resource.CharacterSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

import static dev.peytob.rpg.math.vector.Vectors.immutableVec2;
import static dev.peytob.rpg.server.gameplay.ecs.DefaultContextEntitiesId.createCharacterEntityId;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterContextInteractionServiceImpl implements CharacterContextInteractionService {

    private final WorldContextService worldContextService;

    private final CharacterSessionRepository characterSessionRepository;

    @Override
    public CharacterSession enterToContext(Character character) {
        WorldContextRunner characterWorldContext = getCharacterWorldContext(character);
        return enterToContext(character, characterWorldContext);
    }

    @Override
    public CharacterSession exitFromContext(Character character) {
        WorldContextRunner characterWorldContext = getCharacterWorldContext(character);
        return exitFromContext(character, characterWorldContext);
    }

    private CharacterSession exitFromContext(Character character, WorldContextRunner characterWorldContext) {
        log.info("Character {} exit from ecs context", character.name());

        if (!characterSessionRepository.containsCharacterSession(character)) {
            log.error("Character {} is not connected to context runner", character.name());
            throw new IllegalStateException("Character not found in context");
        }

        characterWorldContext.executeBeforeFrame(ecsContext -> {
            log.info("Removing character {} entities from context", character.name());

            Optional<Entity> characterEntityOptional = ecsContext.getEntityById(createCharacterEntityId(character));

            if (characterEntityOptional.isEmpty()) {
                log.warn("Character {} context entity not found in context {}", character.name(), characterWorldContext.getContextName());
                return;
            }

            ecsContext.removeEntity(characterEntityOptional.get());
        });

        CharacterSession characterSession = characterSessionRepository.getCharacterSession(character);
        characterSessionRepository.remove(characterSession);
        return characterSession;
    }

    private CharacterSession enterToContext(Character character, WorldContextRunner characterWorldContext) {
        log.info("Character {} entering to context {}", character.name(), characterWorldContext.getContextName());

        if (characterSessionRepository.containsCharacterSession(character)) {
            log.error("Character {} is already connected to context runner", character.name());
            throw new IllegalStateException("Character already in context");
        }

        characterWorldContext.executeBeforeFrame(ecsContext -> createNewCharacter(ecsContext, character));

        CharacterSession characterSession = new CharacterSession(character.id(), Instant.now(), character, characterWorldContext.getContextName());
        characterSessionRepository.append(characterSession);
        return characterSession;
    }

    @Override
    public WorldState getAwailableWorldState(Character character) {
        WorldContextRunner characterWorldContext = getCharacterWorldContext(character);
        EcsContext rawContext = characterWorldContext.getRawContext();
        String worldId = characterWorldContext.getWorld().id();

        WorldState.WorldStateBuilder worldStateBuilder = WorldState.builder();
        worldStateBuilder.loadedWorld(WorldState.LoadedWorld.builder()
            .id(worldId)
            .build());

        // TODO Handle exception
        rawContext.getEntityById(createCharacterEntityId(character)).ifPresent(characterEntity -> {
            WorldState.CharacterInfo.CharacterInfoBuilder characterInfoBuilder = WorldState.CharacterInfo.builder();
            characterInfoBuilder
                .id(character.id())
                .name(character.name());

            PositionComponent positionComponent = characterEntity.getComponent(PositionComponent.class);

            if (positionComponent != null) {
                Vec2 position = positionComponent.getPosition();
                characterInfoBuilder.position(position);
            }

            worldStateBuilder.characterInfo(characterInfoBuilder.build());
        });

        return worldStateBuilder.build();
    }

    private void createNewCharacter(EcsContext ecsContext, Character character) {
        log.info("Creating character {} world context entities", character.name());

        Entity entity = ecsContext.createEntity(createCharacterEntityId(character));

        PositionComponent positionComponent = new PositionComponent(immutableVec2());
        entity.bindComponent(positionComponent);
    }

    private WorldContextRunner getCharacterWorldContext(Character character) {
        // TODO Search current character world...
        return worldContextService.getAllWorldContextRunners().stream()
            .findFirst()
            .orElseThrow();
    }
}
