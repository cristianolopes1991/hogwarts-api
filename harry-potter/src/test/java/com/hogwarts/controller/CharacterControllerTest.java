package com.hogwarts.controller;

import com.hogwarts.dto.CharacterPostDTO;
import com.hogwarts.dto.CharacterPutDTO;
import com.hogwarts.model.Character;
import com.hogwarts.service.CharacterService;
import com.hogwarts.util.CharacterCreator;
import com.hogwarts.util.CharacterPostRequestBodyCreator;
import com.hogwarts.util.CharacterPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class CharacterControllerTest {

    @InjectMocks
    private CharacterController characterController;

    @Mock
    private CharacterService characterServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Character> characterPage = new PageImpl<>(List.of(CharacterCreator.createValidCharacter()));
        Character characterToBeSaved = CharacterCreator.createCharacterToBeSaved();
        BDDMockito.when(characterServiceMock.list(ArgumentMatchers.any())).thenReturn(characterPage);
        BDDMockito.when(characterServiceMock.findByHouseId(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(characterPage);
        BDDMockito.when(characterServiceMock.findById(ArgumentMatchers.anyLong())).thenReturn(characterToBeSaved);
        BDDMockito.when(characterServiceMock.save(ArgumentMatchers.any(CharacterPostDTO.class))).thenReturn(characterToBeSaved);
        BDDMockito.doNothing().when(characterServiceMock).replace(ArgumentMatchers.any(CharacterPutDTO.class));
        BDDMockito.doNothing().when(characterServiceMock).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("List returns list of character inside page object when successful")
    void list_ReturnsListOfCharactersInsidePagedObject_WhenSuccessful(){
        String expectedName = CharacterCreator.createCharacterToBeSaved().getName();
        Iterable<Character> characterPage = characterController.list(null).getBody();
        Assertions.assertThat(characterPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(characterPage.iterator().next().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindByHouse id returns list of character inside page object when successful")
    void findByHouseId_ReturnsListOfCharactersInsidePagedObject_WhenSuccessful(){
        String expectedName = CharacterCreator.createCharacterToBeSaved().getName();
        Iterable<Character> characterPage = characterController.findByHouseId("1760529f-6d51-4cb1-bcb1-25087fce5bde", null).getBody();
        Assertions.assertThat(characterPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(characterPage.iterator().next().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindById id returns character object when successful")
    void findById_ReturnsCharacterObject_WhenSuccessful(){
        String expectedName = CharacterCreator.createValidCharacter().getName();
        Character character = characterController.findById(1L).getBody();
        Assertions.assertThat(character).isNotNull();
        Assertions.assertThat(character.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save returns character when successful")
    void save_ReturnsCharacterObject_WhenSuccessful(){
        Character character = characterController.save(CharacterPostRequestBodyCreator.createCharacterPostDTO()).getBody();
        Assertions.assertThat(character).isEqualTo(CharacterCreator.createCharacterToBeSaved());
    }

    @Test
    @DisplayName("replace updates character when successful")
    void replace_UpdateCharacterObject_WhenSuccessful(){
        Assertions.assertThatCode(() -> characterController.replace(CharacterPutRequestBodyCreator.createCharacterPutDTO()).getBody()).doesNotThrowAnyException();
        ResponseEntity<Void> entity = characterController.replace(CharacterPutRequestBodyCreator.createCharacterPutDTO());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes character when successful")
    void delete_RemoveCharacterObject_WhenSuccessful(){
        Assertions.assertThatCode(() -> characterController.delete(1L).getBody()).doesNotThrowAnyException();
        ResponseEntity<Void> entity = characterController.replace(CharacterPutRequestBodyCreator.createCharacterPutDTO());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}