package dev.peytob.rpg.server.gameplay.repository;

import dev.peytob.rpg.engine.repositry.BaseRepository;
import dev.peytob.rpg.server.gameplay.resource.Character;
import dev.peytob.rpg.server.gameplay.resource.CharacterSession;
import org.springframework.stereotype.Service;

@Service
public class CharacterSessionRepository extends BaseRepository<CharacterSession> {

    private final CharacterRepositoryIndex characterRepositoryIndex;

    private final WorldContextRunnerRepositoryIndex worldContextRunnerRepositoryIndex;

    private final UserIdRepositoryIndex userIdRepositoryIndex;

    public CharacterSessionRepository() {
        this.characterRepositoryIndex = new CharacterRepositoryIndex();
        registerIndex(this.characterRepositoryIndex);

        this.worldContextRunnerRepositoryIndex = new WorldContextRunnerRepositoryIndex();
        registerIndex(this.worldContextRunnerRepositoryIndex);

        this.userIdRepositoryIndex = new UserIdRepositoryIndex();
        registerIndex(this.userIdRepositoryIndex);
    }

    public boolean containsCharacterSession(Character character) {
        return characterRepositoryIndex.contains(character.id());
    }

    public boolean containsUserCharacterSession(String userId) {
        return userIdRepositoryIndex.contains(userId);
    }

    public CharacterSession getUserCharacterSession(String userId) {
        return userIdRepositoryIndex.getSingle(userId);
    }

    private final class CharacterRepositoryIndex extends RepositoryIndex<String> {
        @Override
        protected String extractKey(CharacterSession resource) {
            return resource.getCharacter().id();
        }
    }

    private final class UserIdRepositoryIndex extends RepositoryIndex<String> {
        @Override
        protected String extractKey(CharacterSession resource) {
            return resource.getCharacter().userId();
        }
    }

    private final class WorldContextRunnerRepositoryIndex extends RepositoryIndex<String> {
        @Override
        protected String extractKey(CharacterSession resource) {
            return resource.getWorldContextRunnerId();
        }
    }
}
