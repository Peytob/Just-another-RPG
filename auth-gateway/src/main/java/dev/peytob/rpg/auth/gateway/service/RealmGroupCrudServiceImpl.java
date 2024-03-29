package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.group.GroupCreateDto;
import dev.peytob.rpg.auth.gateway.dto.group.GroupUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Group;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.exception.EntityAlreadyExistsException;
import dev.peytob.rpg.auth.gateway.exception.NotFoundException;
import dev.peytob.rpg.auth.gateway.exception.UnresolvedReferencesConflictException;
import dev.peytob.rpg.auth.gateway.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealmGroupCrudServiceImpl implements RealmGroupCrudService {

    private final GroupRepository groupRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Group> getGroupsByIds(Collection<String> groupsIds, Realm realm) {
        Collection<Group> groups = groupRepository.findAllByIdAndRealm(groupsIds, realm);

        if (groups.size() != groupsIds.size()) {
            throw new EntityNotFoundException();
        }

        return groups;
    }

    @Override
    @Transactional
    public Group saveGroup(Group group, Realm realm) {
        if (checkGroupRealm(group, realm)) {
            throw buildIllegalRealmException(group, realm);
        }

        return groupRepository.save(group);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Group> findGroupById(String groupId, Realm realm) {
        return groupRepository.findByIdAndRealm(groupId, realm);
    }

    @Override
    @Transactional(readOnly = true)
    public Group getGroupById(String groupId, Realm realm) {
        return findGroupById(groupId, realm)
            .orElseThrow(() -> new NotFoundException("Group with id " + groupId + " not found in realm " + realm.getName()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Group> getGroupsPage(Realm realm, Pageable pageable) {
        return groupRepository.findAllByRealm(realm, pageable);
    }

    @Override
    @Transactional
    public void deleteGroup(Group group, Realm realm) {
        if (checkGroupRealm(group, realm)) {
            throw buildIllegalRealmException(group, realm);
        }

        try {
            groupRepository.delete(group);
        } catch (ConstraintViolationException e) {
            throw new UnresolvedReferencesConflictException();
        }
    }

    @Override
    @Transactional
    public Group createGroup(GroupCreateDto groupCreateDto, Realm realm) {
        if (checkGroupExists(groupCreateDto.name(), realm)) {
            throw buildGroupExistsException(groupCreateDto.name(), realm);
        }

        Group group = Group.builder()
            .name(groupCreateDto.name())
            .realm(realm)
            .build();

        return saveGroup(group, realm);
    }

    @Override
    @Transactional
    public Group updateGroup(Group group, GroupUpdateDto groupUpdateDto, Realm realm) {
        if (checkGroupExists(groupUpdateDto.name(), realm)) {
            throw buildGroupExistsException(groupUpdateDto.name(), realm);
        }

        if (checkGroupRealm(group, realm)) {
            throw buildIllegalRealmException(group, realm);
        }

        group.setName(groupUpdateDto.name());

        return saveGroup(group, realm);
    }

    private RuntimeException buildIllegalRealmException(Group group, Realm realm) {
        String groupRealmName = group.getRealm().getName();
        return new IllegalArgumentException("Group realm " + groupRealmName + " is not equal to given realm " + realm.getName());
    }

    private boolean checkGroupRealm(Group group, Realm realm) {
        Realm groupRealm = group.getRealm();
        return !realm.equals(groupRealm);
    }

    private boolean checkGroupExists(String name, Realm realm) {
        return groupRepository.existsByNameAndRealm(name, realm);
    }

    private RuntimeException buildGroupExistsException(String name, Realm realm) {
        return new EntityAlreadyExistsException("Group with name '" + name + "' already exists in realm with name " + realm.getName());
    }
}
