package dev.peytob.rpg.auth.gateway.controller;

import dev.peytob.rpg.auth.gateway.dto.group.GroupCreateDto;
import dev.peytob.rpg.auth.gateway.dto.group.GroupGetDto;
import dev.peytob.rpg.auth.gateway.dto.group.GroupUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Group;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.mapper.GroupMapper;
import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import dev.peytob.rpg.auth.gateway.service.RealmGroupCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realm/{realmId}/group")
@RequiredArgsConstructor
public class GroupController {

    private final RealmGroupCrudService realmGroupCrudService;

    private final RealmCrudService realmCrudService;

    private final GroupMapper groupMapper;

    @GetMapping("/")
    Page<GroupGetDto> getGroupsPage(String realmId, @ParameterObject Pageable pageable) {
        Realm realm = realmCrudService.getRealmById(realmId);
        return realmGroupCrudService.getGroupsPage(realm, pageable).map(groupMapper::toGroupDto);
    }

    @GetMapping("/{groupId}")
    GroupGetDto getGroup(@PathVariable String realmId, @PathVariable String groupId) {
        Realm realm = realmCrudService.getRealmById(realmId);
        Group group = realmGroupCrudService.getGroupById(groupId, realm);
        return groupMapper.toGroupDto(group);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    GroupGetDto createGroup(@PathVariable String realmId, @Valid @RequestBody GroupCreateDto groupCreateDto) {
        Realm realm = realmCrudService.getRealmById(realmId);
        Group group = realmGroupCrudService.createGroup(groupCreateDto, realm);
        return groupMapper.toGroupDto(group);
    }

    @PutMapping("/{groupId}")
    GroupGetDto updateGroup(@PathVariable String realmId, @PathVariable String groupId, @Valid @RequestBody GroupUpdateDto groupUpdateDto) {
        Realm realm = realmCrudService.getRealmById(realmId);
        Group group = realmGroupCrudService.getGroupById(groupId, realm);
        Group updatedGroup = realmGroupCrudService.updateGroup(group, groupUpdateDto, realm);
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
