package com.hogwarts.service;

import com.hogwarts.client.HouseClient;
import com.hogwarts.dto.CharacterPostDTO;
import com.hogwarts.dto.CharacterPutDTO;
import com.hogwarts.dto.HogwartsDTO;
import com.hogwarts.exception.BadRequestException;
import com.hogwarts.model.Character;
import com.hogwarts.repository.CharacterRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CharacterService {

    @Value("${apikey}")
    private String env;

    private final HouseClient houseClient;
    private final CharacterRepository characterRepository;

    public Iterable<Character> list(Pageable pageable){
        log.info("Listing all Characters...");
        return characterRepository.findAll(pageable);
    }

    public Character findById(Long id){
        log.info("Wait, searching the character by id " + id + ".");
        return characterRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("The character is not found."));
    }

    public Character save(CharacterPostDTO characterPostDTO) {
        log.info("Saving the character...");
        isHomeIdValid(houseClient.getAllHouses(env), characterPostDTO.getHouseId());
        return characterRepository.save(characterPostDTO.toCharacter(characterPostDTO));
    }

    public void deleteById(Long id) {
        log.info("Deleting Character...");
        characterRepository.delete(findById(id));
    }

    public void replace(CharacterPutDTO characterPutDTO) {
        log.info("Updating Character...");
        findById(Long.parseLong(characterPutDTO.getId()));
        isHomeIdValid(houseClient.getAllHouses(env), characterPutDTO.getHouseId());
        characterRepository.save(characterPutDTO.toCharacter(characterPutDTO));
    }

    public Page<Character> findByHouseId(String houseId, Pageable pageable) {
        log.info("searching characters by house id: " + houseId);
        isHomeIdValid(houseClient.getAllHouses(env), houseId);
        return characterRepository.findByHouseId(houseId, pageable);
    }

    private void isHomeIdValid(HogwartsDTO hogwartsDTO, String houseId){
        if(hogwartsDTO.getHouses().stream().noneMatch(h -> h.getId().equals(houseId))){
            throw new BadRequestException("Failed to query potterApi's houses, check if the house id is correct.");
        }
    }
}
