package com.hogwarts.controller;

import com.hogwarts.dto.CharacterPostDTO;
import com.hogwarts.dto.CharacterPutDTO;
import com.hogwarts.model.Character;
import com.hogwarts.service.CharacterService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("api/v1/characters")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<Iterable<Character>> findByHouseId(@RequestParam String house, @ParameterObject Pageable pageable){
        return new ResponseEntity<>(characterService.findByHouseId(house, pageable), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Character>> list(@Parameter(hidden = true) Pageable pageable){
        return new ResponseEntity<>(characterService.list(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> findById(@PathVariable Long id){
        return ResponseEntity.ok(characterService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Character> save(@RequestBody @Valid CharacterPostDTO characterPostDTO) {
        return new ResponseEntity<>(characterService.save(characterPostDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        characterService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid CharacterPutDTO characterPutDTO){
        characterService.replace(characterPutDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

