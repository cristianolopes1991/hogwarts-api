package com.hogwarts.integration;

import com.hogwarts.model.Character;
import com.hogwarts.repository.CharacterRepository;
import com.hogwarts.util.CharacterCreator;
import com.hogwarts.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CharacterControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CharacterRepository characterRepository;

    @Test
    @DisplayName("list returns list of character inside page object when sucessful")
    void list_returnsListOfCharacterInsidePageObject_WhenSuccessful(){
        Character characterSaved = characterRepository.save(CharacterCreator.createCharacterToBeSaved());
        Long expectedId = characterSaved.getId();
        Character character = testRestTemplate.getForObject("/characters/{id}", Character.class, expectedId);
        Assertions.assertThat(character).isNotNull();
    }



}
