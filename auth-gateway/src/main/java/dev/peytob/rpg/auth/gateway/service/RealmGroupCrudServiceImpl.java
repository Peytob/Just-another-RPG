package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.GroupDto;
import dev.peytob.rpg.auth.gateway.entity.Group;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.exception.NotFoundException;
import dev.peytob.rpg.auth.gateway.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealmGroupCrudServiceImpl implements RealmGroupCrudService {

    private final GroupRepository groupRepository;

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
            .orElseThrow(() -> new NotFoundException("Group with id " + groupId + " not found in realm {}"));
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

        groupRepository.delete(group);
    }

    @Override
    @Transactional
    public Group createGroup(String name, Realm realm) {
        Group group = Group.builder()
            .name(name)
            .realm(realm)
            .build();

        return saveGroup(group, realm);
    }

    @Override
    @Transactional
    public Group updateGroup(Group group, GroupDto groupDto, Realm realm) {
        if (checkGroupRealm(group, realm)) {
            throw buildIllegalRealmException(group, realm);
        }

        group.setName(groupDto.name());

        return saveGroup(group, realm);
    }

    private RuntimeException buildIllegalRealmException(Group group, Realm realm) {
        String groupRealmName = group.getRealm().getName();
        return new IllegalArgumentException("Group realm " + groupRealmName + " is not equal to given realm " + realm.getName());
    }

    private boolean checkGroupRealm(Group group, Realm realm) {
        Realm groupRealm = group.getRealm();
        return realm.equals(groupRealm);
    }
}
