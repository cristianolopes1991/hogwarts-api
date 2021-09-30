package com.hogwarts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DB_CHARACTER")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "character_name", nullable = false, length = 150)
    @NotEmpty(message = "The character name cannot be empty")
    private String name;

    @Column(name = "character_role", nullable = false, length = 60)
    @NotEmpty(message = "The role cannot be empty")
    private String role;

    @Column(name = "school", nullable = false, length = 60)
    @NotEmpty(message = "The school cannot be empty")
    private String school;

    @Column(name = "houseId", nullable = false, length = 36)
    @NotEmpty(message = "The house cannot be empty")
    private String houseId;

    @Column(name = "patronus", length = 50)
    private String patronus;
}
