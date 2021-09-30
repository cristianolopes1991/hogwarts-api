package com.hogwarts.util;

import com.hogwarts.dto.CharacterPostDTO;

public class CharacterPostRequestBodyCreator {

    public static CharacterPostDTO createCharacterPostDTO(){
        return CharacterPostDTO.builder()
                .houseId(CharacterCreator.createCharacterToBeSaved().getHouseId())
                .name(CharacterCreator.createCharacterToBeSaved().getName())
                .patronus(CharacterCreator.createCharacterToBeSaved().getPatronus())
                .role(CharacterCreator.createCharacterToBeSaved().getRole())
                .school(CharacterCreator.createCharacterToBeSaved().getSchool())
                .build();
    }
}
