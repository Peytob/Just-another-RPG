package dev.peytob.rpg.auth.gateway.controller;

import dev.peytob.rpg.auth.gateway.dto.GroupDto;
import dev.peytob.rpg.auth.gateway.entity.Group;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.mapper.GroupMapper;
import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import dev.peytob.rpg.auth.gateway.service.RealmGroupCrudService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("realm/{realmId}/group")
@RequiredArgsConstructor
public class GroupController {

    private final RealmGroupCrudService realmGroupCrudService;

    private final RealmCrudService realmCrudService;

    private final GroupMapper groupMapper;

    @GetMapping("/")
    Page<GroupDto> getGroupsPage(String realmId, @ParameterObject Pageable pageable) {
        Realm realm = realmCrudService.getRealmById(realmId);
        return realmGroupCrudService.getGroupsPage(realm, pageable).map(groupMapper::toGroupDto);
    }

    @GetMapping("/{groupId}")
    GroupDto getGroup(@PathVariable String realmId, @PathVariable String groupId) {
        Realm realm = realmCrudService.getRealmById(realmId);
        Group group = realmGroupCrudService.getGroupById(groupId, realm);
        return groupMapper.toGroupDto(group);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    GroupDto createGroup(@PathVariable String realmId, GroupDto groupDto) {
        Realm realm = realmCrudService.getRealmById(realmId);
        Group group = realmGroupCrudService.createGroup(groupDto.name(), realm);
        return groupMapper.toGroupDto(group);
    }

    @PutMapping("/{groupId}")
    GroupDto updateGroup(@PathVariable String realmId, @PathVariable String groupId, GroupDto groupDto) {
        Realm realm = realmCrudService.getRealmById(realmId);
        Group group = realmGroupCrudService.getGroupById(groupId, realm);
        Group updatedGroup = realmGroupCrudService.updateGroup(group, groupDto, realm);
        return groupMapper.toGroupDto(updatedGroup);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGroup(@PathVariable String realmId, @PathVariable String groupId) {
        Realm realm = realmCrudService.getRealmById(realmId);
        Group group = realmGroupCrudService.getGroupById(groupId, realm);
        realmGroupCrudService.deleteGroup(group, realm);
    }
}
