package dev.peytob.rpg.backend.controller;

import dev.peytob.rpg.backend.dto.user.CharacterCreateDto;
import dev.peytob.rpg.backend.dto.user.CharacterGetDto;
import dev.peytob.rpg.backend.entity.CharacterEntity;
import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.entity.UserRole;
import dev.peytob.rpg.backend.mapper.CharacterMapper;
import dev.peytob.rpg.backend.service.CharacterService;
import dev.peytob.rpg.backend.service.crud.CharacterCrudService;
import dev.peytob.rpg.backend.service.crud.UserCrudService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static dev.peytob.rpg.backend.entity.RoleStringConstant.ROLE_ADMIN_READER;
import static dev.peytob.rpg.backend.entity.RoleStringConstant.ROLE_ADMIN_WRITER;

@RestController
@RequestMapping("/user/{userId}/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterCrudService characterCrudService;

    private final CharacterService characterService;

    private final UserCrudService userCrudService;

    private final CharacterMapper characterMapper;

    @Operation(description = "Get all user characters")
    @GetMapping
    @Secured(ROLE_ADMIN_READER)
    ResponseEntity<Collection<CharacterGetDto>> getUserCharacters(@PathVariable String userId) {
        UserEntity user = userCrudService.getUserById(userId);
        Collection<CharacterEntity> characters = characterCrudService.getByUser(user);
        return ResponseEntity.ok(characterMapper.toCharacterDto(characters));
    }

    @Operation(description = "Create new user character")
    @PostMapping
    @Secured(ROLE_ADMIN_WRITER)
    ResponseEntity<CharacterGetDto> createCharacter(@RequestBody CharacterCreateDto characterCreateDto, @PathVariable String userId) {
        UserEntity user = userCrudService.getUserById(userId);
        CharacterEntity character = characterService.createCharacter(characterCreateDto.name(), user);
        return ResponseEntity.ok(characterMapper.toCharacterDto(character));
    }
}
