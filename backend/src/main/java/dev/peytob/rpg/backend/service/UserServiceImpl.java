package dev.peytob.rpg.backend.service;

import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.entity.UserRole;
import dev.peytob.rpg.backend.exception.EntityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserCrudService userCrudService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findUserByCredentials(String username, String password) {
        Optional<UserEntity> user = userCrudService.findUserByUsername(username);

        if (user.isEmpty()) {
            log.debug("User with username '{}' not found", username);
            return Optional.empty();
        }

        boolean isPasswordMatches = passwordEncoder.matches(password, user.get().getEncodedPassword());

        if (!isPasswordMatches) {
            log.debug("User with username '{}' trying to login with wrong password!", username);
            return Optional.empty();
        }

        return user;
    }

    @Override
    @Transactional
    public UserEntity createPlayerUser(String username, String password, String email) {
        return createUser(username, password, email, List.of(UserRole.PLAYER, UserRole.READER));
    }

    private UserEntity createUser(String username, String password, String email, Collection<UserRole> roles) {
        if (userCrudService.isUserExistsByUsername(username)) {
            throw new EntityAlreadyExistsException("User with username '" + username + "' is already exists");
        }

        if (userCrudService.isUserExistsByEmail(username)) {
            throw new EntityAlreadyExistsException("User with email '" + email + "' is already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        UserEntity user = UserEntity.builder()
            .email(email)
            .encodedPassword(encodedPassword)
            .isBlocked(false)
            .roles(roles)
            .lastLoginAt(Instant.now())
            .username(username)
            .build();

        return userCrudService.saveUser(user);
    }
}
