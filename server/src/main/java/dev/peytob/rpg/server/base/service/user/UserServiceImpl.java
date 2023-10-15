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
    public User getUserById(String id) {
        return userRepository.getById(id);
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.getUserByToken(token);
    }

    @Override
    public boolean createUser(User user) {
        log.info("Creating new user with id {} and text id {}", user.id(), user.id());

        if (userRepository.contains(user.id())) {
            log.warn("User with id {} is already exists!", user.id());
            return false;
        }

        return userRepository.append(user);
    }

    @Override
    public boolean removeUser(User user) {
        boolean isUserRemoved = userRepository.remove(user);

        if (!isUserRemoved) {
            log.warn("Removing not exists user {} ({})", user.id(), user.id());
        }

        return isUserRemoved;
    }
}
