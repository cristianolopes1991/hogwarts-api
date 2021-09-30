package com.hogwarts.repository;

import com.hogwarts.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CharacterRepository extends PagingAndSortingRepository<Character, Long> {

    Page<Character> findByHouseId(String houseId, Pageable pageable);
}
