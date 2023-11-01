package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.entity.User;
import dev.peytob.rpg.auth.gateway.exception.EntityAlreadyExistsException;
import dev.peytob.rpg.auth.gateway.exception.UnresolvedReferencesConflictException;
import dev.peytob.rpg.auth.gateway.repository.TokenRepository;
import dev.peytob.rpg.auth.gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealmUserCrudServiceImpl implements RealmUserCrudService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

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
    public void deleteUser(User user, Realm realm) {
        if (checkUserRealm(user, realm)) {
            throw buildIllegalRealmException(user, realm);
        }

        try {
            tokenRepository.deleteAllByUser(user);
            userRepository.delete(user);
        } catch (ConstraintViolationException e) {
            throw new UnresolvedReferencesConflictException();
        }
    }

    @Override
    public User saveUser(User user, Realm realm) {
        if (checkUserRealm(user, realm)) {
            throw buildIllegalRealmException(user, realm);
        }

        return userRepository.save(user);
    }

    private RuntimeException buildIllegalRealmException(User user, Realm realm) {
        String userRealmName = user.getRealm().getName();
        return new IllegalArgumentException("User realm " + userRealmName + " is not equal to given realm " + realm.getName());
    }

    private boolean checkUserRealm(User user, Realm realm) {
        Realm userRealm = user.getRealm();
        return !realm.equals(userRealm);
    }
}
