package dev.peytob.rpg.auth.gateway.controller;

import dev.peytob.rpg.auth.gateway.dto.realm.RealmCreateDto;
import dev.peytob.rpg.auth.gateway.dto.realm.RealmGetDto;
import dev.peytob.rpg.auth.gateway.dto.realm.RealmUpdateDto;
import dev.peytob.rpg.auth.gateway.entity.Realm;
import dev.peytob.rpg.auth.gateway.mapper.RealmMapper;
import dev.peytob.rpg.auth.gateway.service.RealmCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realm")
@RequiredArgsConstructor
@Slf4j
public class RealmController {

    private final RealmCrudService realmCrudService;

    private final RealmMapper realmMapper;

    @GetMapping("/")
    Page<RealmGetDto> getRealmsPage(@ParameterObject Pageable pageable) {
        return realmCrudService.getRealmsPage(pageable).map(realmMapper::toRealmDto);
    }

    @GetMapping("/{realmId}")
    RealmGetDto getRealm(@PathVariable String realmId) {
        Realm realm = realmCrudService.getRealmById(realmId);
        return realmMapper.toRealmDto(realm);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    RealmGetDto createRealm(@Valid @RequestBody RealmCreateDto realmCreateDto) {
        log.info("Creating new realm with name '{}'", realmCreateDto.name());
        Realm realm = realmCrudService.createRealm(realmCreateDto);
        return realmMapper.toRealmDto(realm);
    }

    @PutMapping("/{realmId}")
    RealmGetDto updateRealm(@PathVariable String realmId, @Valid @RequestBody RealmUpdateDto realmUpdateDto) {
        log.info("Updating realm with id '{}'", realmId);
        Realm realm = realmCrudService.getRealmById(realmId);
        Realm updatedRealm = realmCrudService.updateRealm(realm, realmUpdateDto);
        return realmMapper.toRealmDto(updatedRealm);
    }

    @DeleteMapping("/{realmId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRealm(@PathVariable String realmId) {
        log.info("Deleting realm with id '{}'", realmId);
        Realm realm = realmCrudService.getRealmById(realmId);
        realmCrudService.deleteRealm(realm);
    }
}
