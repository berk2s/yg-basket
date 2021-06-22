package com.yataygecisle.preference.basket.web.controllers;

import com.yataygecisle.preference.basket.services.BasketService;
import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(BasketController.ENDPOINT)
@RestController
public class BasketController {

    public static final String ENDPOINT = "/";

    private final BasketService basketService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketDto> createBasket(@RequestBody CreateBasketDto createBasketDto) {
        return new ResponseEntity<>(basketService.create(createBasketDto), HttpStatus.CREATED);
    }

}
