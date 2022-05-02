package com.qwee.character.controller;

import com.qwee.character.entity.character.CharacterEntity;
import com.qwee.character.model.dto.request.NewCharacterRequestDto;
import com.qwee.character.model.dto.response.CharacterResponseDto;
import com.qwee.character.service.CharacterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
public class CharacterCRUDController {
    private final CharacterServiceImpl characterService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CharacterEntity>> getAll() {
        return ResponseEntity.ok(characterService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CharacterEntity> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(characterService.findById(id));
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CharacterEntity> getByName(@Param("name") String name) {
        return ResponseEntity.ok(characterService.findByName(name));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CharacterResponseDto> createCharacter(@RequestBody NewCharacterRequestDto newCharacterRequestDto) {
        CharacterEntity character = characterService.createCharacter(newCharacterRequestDto);

        CharacterResponseDto characterToResponse = new CharacterResponseDto();

        characterToResponse.setId(character.getId());
        characterToResponse.setName(character.getName());
        characterToResponse.setLevel(character.getLevel());
        characterToResponse.setAttributes(character.getAttributes());
        characterToResponse.setCharacterType(character.getType());
        characterToResponse.setHasGuild(character.isHasGuild());

        return ResponseEntity.ok(characterToResponse);
    }

    @DeleteMapping(value = "/del/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Integer id) {
        characterService.deleteById(id);
        return ResponseEntity.ok(String.format("Character with id: %d deleted", id));
    }

    @DeleteMapping(value = "/del/all")
    public ResponseEntity<String> deleteAllCharacters(){
        characterService.deleteAll();
        return ResponseEntity.ok("All characters was deleted");
    }

    @PatchMapping(value = "/change/name/{id}")
    public ResponseEntity<String> changeCharacterName(@PathVariable Integer id, @RequestBody String newName) {
        characterService.changeNameById(id, newName);
        return ResponseEntity.ok(String.format("Name changed to %s", newName));
    }
}
