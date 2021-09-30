package com.hogwarts.util;

import com.hogwarts.dto.CharacterPutDTO;

public class CharacterPutRequestBodyCreator {

    public static CharacterPutDTO createCharacterPutDTO(){
        return CharacterPutDTO.builder()
                .id("1")
                .houseId(CharacterCreator.createCharacterToBeSaved().getHouseId())
                .name(CharacterCreator.createCharacterToBeSaved().getName())
                .patronus(CharacterCreator.createCharacterToBeSaved().getPatronus())
                .role(CharacterCreator.createCharacterToBeSaved().getRole())
                .school(CharacterCreator.createCharacterToBeSaved().getSchool())
                .build();
    }
}
