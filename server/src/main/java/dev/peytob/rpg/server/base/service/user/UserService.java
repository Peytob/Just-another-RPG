package dev.peytob.rpg.server.base.service.user;

import dev.peytob.rpg.server.base.resource.User;

public interface UserService {

    User getUserById(String textId);

    User getUserByToken(String token);

    boolean createUser(User user);

    boolean removeUser(User user);
}
