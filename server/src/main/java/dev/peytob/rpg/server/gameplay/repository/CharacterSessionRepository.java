package dev.peytob.rpg.server.gameplay.repository;

import dev.peytob.rpg.engine.repositry.BaseRepository;
import dev.peytob.rpg.server.gameplay.resource.Character;
import dev.peytob.rpg.server.gameplay.resource.CharacterSession;
import org.springframework.stereotype.Service;

@Service
public class CharacterSessionRepository extends BaseRepository<CharacterSession> {

    private final CharacterRepositoryIndex characterRepositoryIndex;

    private final EcsContextRunnerRepositoryIndex ecsContextRunnerRepositoryIndex;

    public CharacterSessionRepository() {
        this.characterRepositoryIndex = new CharacterRepositoryIndex();
        registerIndex(this.characterRepositoryIndex);

        this.ecsContextRunnerRepositoryIndex = new EcsContextRunnerRepositoryIndex();
        registerIndex(this.ecsContextRunnerRepositoryIndex);
    }

    public boolean containsCharacterSession(Character character) {
        return characterRepositoryIndex.contains(character.id());
    }

    private final class CharacterRepositoryIndex extends RepositoryIndex<String> {
        @Override
        protected String extractKey(CharacterSession resource) {
            return resource.getCharacter().id();
        }
    }

    private final class EcsContextRunnerRepositoryIndex extends RepositoryIndex<String> {
        @Override
        protected String extractKey(CharacterSession resource) {
            return resource.getEcsContextRunnerId();
        }
    }
}
