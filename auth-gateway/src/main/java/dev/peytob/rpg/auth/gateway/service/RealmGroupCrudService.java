package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.group.GroupCreateDto;
import dev.peytob.rpg.auth.gateway.dto.group.GroupUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Group;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

/**
 * Group service, that adds a Realm check when performing crud operations, which allows them to be
 * carried out exclusively within specified Realm.
 */
public interface RealmGroupCrudService {

    Collection<Group> getGroupsByIds(Collection<String> groupsIds, Realm realm);

    Group saveGroup(Group group, Realm realm);

    Optional<Group> findGroupById(String groupId, Realm realm);

    Group getGroupById(String groupId, Realm realm);

    Page<Group> getGroupsPage(Realm realm, Pageable pageable);

    void deleteGroup(Group group, Realm realm);

    Group createGroup(GroupCreateDto groupCreateDto, Realm realm);

    Group updateGroup(Group group, GroupUpdateDto groupUpdateDto, Realm realm);
}
