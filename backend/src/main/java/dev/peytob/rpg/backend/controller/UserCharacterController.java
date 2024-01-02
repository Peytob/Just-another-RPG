package dev.peytob.rpg.backend.controller;

import dev.peytob.rpg.backend.annotation.OwnerOrAdminReader;
import dev.peytob.rpg.backend.annotation.OwnerOrAdminWriter;
import dev.peytob.rpg.backend.dto.user.CharacterCreateDto;
import dev.peytob.rpg.backend.dto.user.CharacterGetDto;
import dev.peytob.rpg.backend.entity.CharacterEntity;
import dev.peytob.rpg.backend.entity.UserEntity;
import dev.peytob.rpg.backend.mapper.CharacterMapper;
import dev.peytob.rpg.backend.service.CharacterService;
import dev.peytob.rpg.backend.service.crud.CharacterCrudService;
import dev.peytob.rpg.backend.service.crud.UserCrudService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user/{userId}/character")
@RequiredArgsConstructor
public class UserCharacterController {

    private final CharacterCrudService characterCrudService;

    private final CharacterService characterService;

    private final UserCrudService userCrudService;

    private final CharacterMapper characterMapper;

    @Operation(description = "Get all user characters")
    @GetMapping
    @OwnerOrAdminReader
    ResponseEntity<Collection<CharacterGetDto>> getUserCharacters(@PathVariable String userId) {
        UserEntity user = userCrudService.getUserById(userId);
        Collection<CharacterEntity> characters = characterCrudService.getByUser(user);
        return ResponseEntity.ok(characterMapper.toCharacterDto(characters));
    }

    @Operation(description = "Create new user character")
    @PostMapping
    @OwnerOrAdminWriter
    ResponseEntity<CharacterGetDto> createCharacter(@RequestBody CharacterCreateDto characterCreateDto, @PathVariable String userId) {
        UserEntity user = userCrudService.getUserById(userId);
        CharacterEntity character = characterService.createCharacter(characterCreateDto.name(), user);
        return ResponseEntity.ok(characterMapper.toCharacterDto(character));
    }
}
