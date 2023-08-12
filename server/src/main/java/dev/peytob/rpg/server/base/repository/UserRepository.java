package dev.peytob.rpg.server.base.repository;

import dev.peytob.rpg.engine.repositry.BaseRepository;
import dev.peytob.rpg.server.base.resource.User;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends BaseRepository<User> {

    private final BaseRepository<User>.RepositoryIndex<String> tokenIndex = new RepositoryIndex<String>() {
        @Override
        protected String extractKey(User resource) {
        return resource.textId();
        }
    };

    public UserRepository() {
        registerIndex(tokenIndex);
    }

    public User getUserByToken(String token) {
        return tokenIndex.get(token);
    }
}
