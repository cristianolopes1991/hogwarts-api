package com.hogwarts.util;

import com.hogwarts.dto.CharacterPutDTO;
import com.hogwarts.dto.HogwartsDTO;
import com.hogwarts.dto.HouseDTO;

import java.util.List;

public class HouseCreator {
    public static HogwartsDTO createHouse(){
        return HogwartsDTO.builder()
                .houses(List.of(HouseDTO.builder()
                        .houseGhost("Nearly Headless Nick")
                        .founder("Goderic Gryffindor")
                        .headOfHouse("Minerva McGonagall")
                        .mascot("lion")
                        .id("1760529f-6d51-4cb1-bcb1-25087fce5bde")
                        .school("Hogwarts School of Witchcraft and Wizardry")
                        .name("Gryffindor")
                        .values(List.of("courage", "bravery", "nerve", "chivalry"))
                        .colors(List.of("scarlet", "gold"))
                        .build()))
                .build();
    }

}
