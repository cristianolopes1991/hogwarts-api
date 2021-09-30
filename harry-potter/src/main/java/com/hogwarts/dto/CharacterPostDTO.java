package com.hogwarts.dto;

import com.hogwarts.model.Character;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@Builder
public class CharacterPostDTO {

    @NotNull
    @NotEmpty(message = "The character name cannot be empty")
    @Schema(description = "This is the character's name", example = "Harry Potter")
    private String name;

    @NotNull
    @NotEmpty(message = "The character role cannot be empty")
    @Schema(description = "This is the role", example = "student")
    private String role;

    @NotNull
    @NotEmpty(message = "The character school cannot be empty")
    @Schema(description = "This is the school", example = "Hogwarts School of Witchcraft and Wizardry")
    private String school;

    @NotNull
    @NotEmpty(message = "The character house cannot be empty")
    @Schema(description = "This is house id", example = "1760529f-6d51-4cb1-bcb1-25087fce5bde")
    private String houseId;

    @Schema(description = "This is patronus", example = "stag")
    private String patronus;

    public Character toCharacter(CharacterPostDTO characterPostDTO){
        return Character.builder()
                .name(characterPostDTO.getName())
                .role(characterPostDTO.getRole())
                .school(characterPostDTO.getSchool())
                .houseId(characterPostDTO.getHouseId())
                .patronus(Objects.nonNull(characterPostDTO.getPatronus()) ? characterPostDTO.getPatronus() : "")
                .build();
    }
}
