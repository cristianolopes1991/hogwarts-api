package com.hogwarts.service;

import com.hogwarts.client.HouseClient;
import com.hogwarts.dto.HogwartsDTO;
import com.hogwarts.exception.BadRequestException;
import com.hogwarts.model.Character;
import com.hogwarts.repository.CharacterRepository;
import com.hogwarts.util.CharacterCreator;
import com.hogwarts.util.CharacterPostRequestBodyCreator;
import com.hogwarts.util.CharacterPutRequestBodyCreator;
import com.hogwarts.util.HouseCreator;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class CharacterServiceTest {

    @InjectMocks
    private CharacterService characterService;

    @Mock
    private CharacterRepository characterRepositoryMock;

    @Mock
    private HouseClient houseClient;

    @BeforeEach
    void setUp(){
        PageImpl<Character> characterPage = new PageImpl<>(List.of(CharacterCreator.createValidCharacter()));
        Character characterToBeSaved = CharacterCreator.createCharacterToBeSaved();
        BDDMockito.when(characterRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(characterPage);
        BDDMockito.when(characterRepositoryMock.findByHouseId(ArgumentMatchers.any(), ArgumentMatchers.any(PageRequest.class))).thenReturn(characterPage);
        BDDMockito.when(characterRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(CharacterCreator.createValidCharacter()));
        BDDMockito.when(characterRepositoryMock.save(ArgumentMatchers.any(Character.class))).thenReturn(characterToBeSaved);
        BDDMockito.doNothing().when(characterRepositoryMock).deleteById(ArgumentMatchers.anyLong());
        BDDMockito.when(houseClient.getAllHouses(ArgumentMatchers.any())).thenReturn(HouseCreator.createHouse());
    }

    @Test
    @DisplayName("List returns list of character inside page object when successful")
    void list_ReturnsListOfCharactersInsidePagedObject_WhenSuccessful(){
        String expectedName = CharacterCreator.createCharacterToBeSaved().getName();
        Iterable<Character> characterPage = characterService.list(Pageable.ofSize(1));
        Assertions.assertThat(characterPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(characterPage.iterator().next().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindByHouse id returns list of character inside page object when successful")
    void findByHouseId_ReturnsListOfCharactersInsidePagedObject_WhenSuccessful(){
        String expectedName = CharacterCreator.createCharacterToBeSaved().getName();
        Iterable<Character> characterPage = characterService.findByHouseId("1760529f-6d51-4cb1-bcb1-25087fce5bde", Pageable.ofSize(1));
        Assertions.assertThat(characterPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(characterPage.iterator().next().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("FindById id returns character object when successful")
    void findById_ReturnsCharacterObject_WhenSuccessful(){
        String expectedName = CharacterCreator.createValidCharacter().getName();
        Character character = characterService.findById(1L);
        Assertions.assertThat(character).isNotNull();
        Assertions.assertThat(character.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save returns character when successful")
    void save_ReturnsCharacterObject_WhenSuccessful(){
        Character character = characterService.save(CharacterPostRequestBodyCreator.createCharacterPostDTO());
        Assertions.assertThat(character).isEqualTo(CharacterCreator.createCharacterToBeSaved());
    }

    @Test
    @DisplayName("save throws BadRequestException when house id is not found")
    void save_ThrowsBadRequestException_WhenHouseIdIsNotFound(){
        BDDMockito.when(houseClient.getAllHouses(ArgumentMatchers.any())).thenReturn(new HogwartsDTO());
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> characterService.save(CharacterPostRequestBodyCreator.createCharacterPostDTO()));
    }

    @Test
    @DisplayName("FindByHouse throws BadRequestException when house id is not found")
    void findByHouseId_ThrowsBadRequestException_WhenHouseIdIsNotFound(){
        BDDMockito.when(characterRepositoryMock.findByHouseId(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> characterService.findByHouseId(null, Pageable.ofSize(1)));
    }

    @Test
    @DisplayName("FindById throws BadRequestException when character id is not found")
    void findById_ThrowsBadRequestException_WhenCharacterIdIsNotFound(){
        BDDMockito.when(characterRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> characterService.findById(1L));
    }

    @Test
    @DisplayName("DeleteById throws BadRequestException when character id is not found")
    void deleteById_ThrowsBadRequestException_WhenCharacterIdIsNotFound(){
        BDDMockito.when(characterRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> characterService.deleteById(1L));
    }

    @Test
    @DisplayName("replace updates character when successful")
    void replace_UpdateCharacterObject_WhenSuccessful(){
        Assertions.assertThatCode(() -> characterService.replace(CharacterPutRequestBodyCreator.createCharacterPutDTO())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes character when successful")
    void delete_RemoveCharacterObject_WhenSuccessful(){
        Assertions.assertThatCode(() -> characterService.deleteById(1L)).doesNotThrowAnyException();
    }
}