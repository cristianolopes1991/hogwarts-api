package com.hogwarts.repository;

import com.hogwarts.model.Character;
import com.hogwarts.util.CharacterCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Character Repository")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class CharacterRepositoryTest {
    @Autowired
    private CharacterRepository characterRepository;

    @Test
    @DisplayName("Save persists Character when Successful")
    void save_PersistCharacter_WhenSuccessful(){
        Character characterBeSaved = CharacterCreator.createCharacterToBeSaved();
        Character characterSaved = this.characterRepository.save(characterBeSaved);
        Assertions.assertThat(characterSaved).isNotNull();
        Assertions.assertThat(characterSaved.getId()).isNotNull();
        Assertions.assertThat(characterSaved.getName()).isEqualTo(characterBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates Character when Successful")
    void save_UpdateCharacter_WhenSuccessful(){
        Character characterBeSaved = CharacterCreator.createCharacterToBeSaved();
        Character characterSaved = this.characterRepository.save(characterBeSaved);
        characterSaved.setName("Hermione Granger");
        characterSaved.setPatronus("otter");
        Character characterUpdated = this.characterRepository.save(characterSaved);
        Assertions.assertThat(characterUpdated).isNotNull();
        Assertions.assertThat(characterUpdated.getId()).isNotNull();
        Assertions.assertThat(characterUpdated.getName()).isEqualTo(characterSaved.getName());
    }

    @Test
    @DisplayName("Delete By id removes Character when Successful")
    void deleteById_RemovesCharacter_WhenSuccessful(){
        Character characterBeSaved = CharacterCreator.createCharacterToBeSaved();
        Character characterSaved = this.characterRepository.save(characterBeSaved);
        this.characterRepository.deleteById(characterSaved.getId());
        Optional<Character> characterOptional = this.characterRepository.findById(characterSaved.getId());
        Assertions.assertThat(characterOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By House id returns list of Character when Successful")
    void findByHouseId_ReturnsListOfCharacter_WhenSuccessful(){
        Character characterBeSaved = CharacterCreator.createCharacterToBeSaved();
        Character characterSaved = this.characterRepository.save(characterBeSaved);
        String houseId = characterSaved.getHouseId();
        Page<Character> pagedHouseId = this.characterRepository.findByHouseId(houseId, Pageable.unpaged());
        Assertions.assertThat(pagedHouseId).isNotEmpty().contains(characterSaved);
    }

    @Test
    @DisplayName("Find By house id returns empty list when no Character is found")
    void findByHouseId_ReturnsEmptyList_WhenCharacterIsNotFound(){
        Page<Character> pagedHouseId = this.characterRepository.findByHouseId("houseId", Pageable.unpaged());
        Assertions.assertThat(pagedHouseId).isEmpty();
    }

    @Test
    @DisplayName("Find By id returns Character when Successful")
    void findById_ReturnsEmptyCharacter_WhenCharacterIsNotFound(){
        Optional<Character> characterFound = this.characterRepository.findById(50L);
        Assertions.assertThat(characterFound).isEmpty();
    }

    @Test
    @DisplayName("Find By id returns empty object when no Character is found")
    void findById_ReturnsCharacter_WhenSuccessful(){
        Character characterBeSaved = CharacterCreator.createCharacterToBeSaved();
        Character characterSaved = this.characterRepository.save(characterBeSaved);
        Optional<Character> characterFound = this.characterRepository.findById(characterSaved.getId());
        Assertions.assertThat(characterFound.get().getId()).isEqualTo(characterSaved.getId());
    }

    @Test
    @DisplayName("Returns list of Character when Successful")
    void findAll_ReturnsListOfCharacter_WhenSuccessful(){
        Character characterBeSaved = CharacterCreator.createCharacterToBeSaved();
        Character characterSaved = this.characterRepository.save(characterBeSaved);
        Iterable<Character> characters = this.characterRepository.findAll();
        Assertions.assertThat(characters).isNotEmpty().contains(characterSaved);
    }

    @Test
    @DisplayName("Save throw ContraintValidationException when name is empty")
    void save_ThrowsConstraintValidationException_WhenNameIsEmpty(){
        Character character = new Character();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.characterRepository.save(character))
                .withMessageContaining("The character name cannot be empty");
    }

    @Test
    @DisplayName("Save throw ContraintValidationException when role is empty")
    void save_ThrowsConstraintValidationException_WhenRoleIsEmpty(){
        Character character = new Character();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.characterRepository.save(character))
                .withMessageContaining("The role cannot be empty");
    }

    @Test
    @DisplayName("Save throw ContraintValidationException when school is empty")
    void save_ThrowsConstraintValidationException_WhenSchoolIsEmpty(){
        Character character = new Character();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.characterRepository.save(character))
                .withMessageContaining("The school cannot be empty");
    }

    @Test
    @DisplayName("Save throw ContraintValidationException when house id is empty")
    void save_ThrowsConstraintValidationException_WhenHouseIdIsEmpty(){
        Character character = new Character();
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.characterRepository.save(character))
                .withMessageContaining("The house cannot be empty");
    }
}