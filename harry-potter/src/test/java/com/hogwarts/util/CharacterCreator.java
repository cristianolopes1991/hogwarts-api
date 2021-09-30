package com.hogwarts.util;

import com.hogwarts.model.Character;

public class CharacterCreator {

    public static Character createCharacterToBeSaved(){
        return Character.builder()
                .houseId("1760529f-6d51-4cb1-bcb1-25087fce5bde")
                .name("Harry Potter")
                .patronus("stag")
                .role("Student")
                .school("Hogwarts School of Witchcraft and Wizardry")
                .build();
    }

    public static Character createValidCharacter(){
        return Character.builder()
                .id(1L)
                .houseId("1760529f-6d51-4cb1-bcb1-25087fce5bde")
                .name("Harry Potter")
                .patronus("stag")
                .role("Student")
                .school("Hogwarts School of Witchcraft and Wizardry")
                .build();
    }

    public static Character createValidUpdateCharacter(){
        return Character.builder()
                .id(1L)
                .houseId("df01bd60-e3ed-478c-b760-cdbd9afe51fc")
                .name("Draco Lucius Malfoy")
                .patronus("")
                .role("Student")
                .school("Hogwarts School of Witchcraft and Wizardry")
                .build();
    }
}
