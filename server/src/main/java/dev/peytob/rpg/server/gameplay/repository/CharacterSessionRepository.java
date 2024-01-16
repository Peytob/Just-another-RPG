package dev.peytob.rpg.server.gameplay.repository;

import dev.peytob.rpg.engine.repositry.BaseRepository;
import dev.peytob.rpg.server.gameplay.resource.Character;
import dev.peytob.rpg.server.gameplay.resource.CharacterSession;
import org.springframework.stereotype.Service;

@Service
public class CharacterSessionRepository extends BaseRepository<CharacterSession> {

    private final CharacterRepositoryIndex characterRepositoryIndex;

    private final WorldContextRunnerRepositoryIndex worldContextRunnerRepositoryIndex;

    public CharacterSessionRepository() {
        this.characterRepositoryIndex = new CharacterRepositoryIndex();
        registerIndex(this.characterRepositoryIndex);

        this.worldContextRunnerRepositoryIndex = new WorldContextRunnerRepositoryIndex();
        registerIndex(this.worldContextRunnerRepositoryIndex);
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

    private final class WorldContextRunnerRepositoryIndex extends RepositoryIndex<String> {
        @Override
        protected String extractKey(CharacterSession resource) {
            return resource.getWorldContextRunnerId();
        }
    }
}
