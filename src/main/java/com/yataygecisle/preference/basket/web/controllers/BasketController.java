package com.yataygecisle.preference.basket.web.controllers;

import com.yataygecisle.preference.basket.services.BasketService;
import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(BasketController.ENDPOINT)
@RestController
public class BasketController {

    public static final String ENDPOINT = "/basket";

    private final BasketService basketService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketDto> createBasket(@RequestBody CreateBasketDto createBasketDto) {
        return new ResponseEntity<>(basketService.create(createBasketDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BasketDto>> getBasket(@PathVariable UUID ownerId) {
        return new ResponseEntity<>(basketService.getUserBaskets(ownerId), HttpStatus.OK);
    }

}
