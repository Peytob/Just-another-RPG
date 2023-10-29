package dev.peytob.rpg.auth.gateway.service;

import dev.peytob.rpg.auth.gateway.dto.GroupDto;
import dev.peytob.rpg.auth.gateway.entity.Group;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Group service, that adds a Realm check when performing crud operations, which allows them to be
 * carried out exclusively within specified Realm.
 */
public interface RealmGroupCrudService {

    Group saveGroup(Group group, Realm realm);

    Optional<Group> findGroupById(String groupId, Realm realm);

    Group getGroupById(String groupId, Realm realm);

    Page<Group> getGroupsPage(Realm realm, Pageable pageable);

    void deleteGroup(Group group, Realm realm);

    Group createGroup(String name, Realm realm);

    Group updateGroup(Group group, GroupDto groupDto, Realm realm);
}
