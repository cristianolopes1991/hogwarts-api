package com.hogwarts.client;

import com.hogwarts.dto.HogwartsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "houses", url = "http://us-central1-rh-challenges.cloudfunctions.net/potterApi")
public interface HouseClient {

    @GetMapping(value = "/houses")
    HogwartsDTO getAllHouses(@RequestHeader("apikey") String apiKey);
}
