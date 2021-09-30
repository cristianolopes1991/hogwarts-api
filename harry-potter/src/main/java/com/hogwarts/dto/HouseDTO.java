package com.hogwarts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class HouseDTO {

    private String id;
    private String houseGhost;
    private String mascot;
    private String founder;
    private List<String> values;
    private List<String> colors;
    private String name;
    private String school;
    private String headOfHouse;
}

