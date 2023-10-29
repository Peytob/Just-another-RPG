package dev.peytob.rpg.auth.gateway.controller;

import dev.peytob.rpg.auth.gateway.dto.RealmDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.mapper.RealmMapper;
import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realm")
@RequiredArgsConstructor
public class RealmController {

    private final RealmCrudService realmCrudService;

    private final RealmMapper realmMapper;

    @GetMapping("/")
    Page<RealmDto> getRealmsPage(@ParameterObject Pageable pageable) {
        return realmCrudService.getRealmsPage(pageable).map(realmMapper::toRealmDto);
    }

    @GetMapping("/{realmId}")
    RealmDto getRealm(@PathVariable String realmId) {
        Realm realm = realmCrudService.getRealmById(realmId);
        return realmMapper.toRealmDto(realm);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    RealmDto createRealm(RealmDto realmDto) {
        Realm realm = realmCrudService.createRealm(realmDto);
        return realmMapper.toRealmDto(realm);
    }

    @PutMapping("/{realmId}")
    RealmDto updateRealm(@PathVariable String realmId, RealmDto realmDto) {
        Realm realm = realmCrudService.getRealmById(realmId);
        Realm updatedRealm = realmCrudService.updateRealm(realm, realmDto);
        return realmMapper.toRealmDto(updatedRealm);
    }

    @DeleteMapping("/{realmId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRealm(@PathVariable String realmId) {
        Realm realm = realmCrudService.getRealmById(realmId);
        realmCrudService.deleteRealm(realm);
    }
}
