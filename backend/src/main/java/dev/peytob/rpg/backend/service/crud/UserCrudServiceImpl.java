package dev.peytob.rpg.backend.service.crud;

import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.exception.EntityNotFoundException;
import dev.peytob.rpg.backend.exception.UnresolvedReferencesConflictException;
import dev.peytob.rpg.backend.repository.TokenRepository;
import dev.peytob.rpg.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCrudServiceImpl implements UserCrudService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserById(String userId) {
        return findUserById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User with id '" + userId + "' not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> getUsersPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteUser(UserEntity user) {
        try {
            tokenRepository.deleteAllByUser(user);
            userRepository.delete(user);
        } catch (ConstraintViolationException exception) {
            throw new UnresolvedReferencesConflictException("Some references to user with id '" + user.getId() + "' still exists", exception);
        }
    }

    @Override
    @Transactional
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
