package dev.peytob.rpg.server.base.service.user;

import dev.peytob.rpg.server.base.resource.User;

public interface UserService {

    User getUserById(int id);

    User getUserById(String textId);

    boolean createUser(User user);

    boolean removeUser(User user);
}
