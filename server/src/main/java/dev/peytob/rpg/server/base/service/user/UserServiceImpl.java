package dev.peytob.rpg.server.base.service.user;

import dev.peytob.rpg.server.base.repository.UserRepository;
import dev.peytob.rpg.server.base.resource.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User getUserById(String textId) {
        return userRepository.getById(textId);
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.getUserByToken(token);
    }

    @Override
    public boolean createUser(User user) {
        log.info("Creating new user with id {} and text id {}", user.id(), user.textId());

        if (userRepository.contains(user.textId())) {
            log.warn("User with text id {} is already exists!", user.textId());
            return false;
        }

        if (userRepository.contains(user.id())) {
            log.warn("User with id {} is already exists!", user.textId());
            return false;
        }

        return userRepository.append(user);
    }

    @Override
    public boolean removeUser(User user) {
        boolean isUserRemoved = userRepository.remove(user);

        if (!isUserRemoved) {
            log.warn("Removing not exists user {} ({})", user.id(), user.textId());
        }

        return isUserRemoved;
    }
}
