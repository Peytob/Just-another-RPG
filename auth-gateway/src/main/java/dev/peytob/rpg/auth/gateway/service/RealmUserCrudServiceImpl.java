package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.user.UserCreateDto;
import dev.peytob.rpg.auth.gateway.dto.user.UserUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;
import dev.peytob.rpg.auth.gateway.exception.EntityAlreadyExistsException;
import dev.peytob.rpg.auth.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealmUserCrudServiceImpl implements RealmUserCrudService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByCredentials(String username, String passwordHash, Realm realm) {
        return userRepository.findByUsernameAndPasswordHashAndRealm(username, passwordHash, realm);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsersPage(Pageable pageable, Realm realm) {
        return userRepository.findAllByRealm(realm, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(String userId, Realm realm) {
        return userRepository.findByIdAndRealm(userId, realm);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(String userId, Realm realm) {
        return findUserById(userId, realm)
            .orElseThrow(() -> new EntityAlreadyExistsException("User with id '" + userId + "' not found in realm " + realm.getName()));
    }

    @Override
    public User createUser(UserCreateDto userCreateDto, Realm realm) {
        throw new NotImplementedException();
    }

    @Override
    public User updateUser(User user, UserUpdateDto userUpdateDto, Realm realm) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteUser(User user, Realm realm) {
        throw new NotImplementedException();
    }
}
