package com.yataygecisle.preference.basket.web.controllers;

import com.yataygecisle.preference.basket.services.BasketItemService;
import com.yataygecisle.preference.basket.web.models.BasketItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(BasketItemController.ENDPOINT)
@RestController
public class BasketItemController {

    public static final String ENDPOINT = "/basketitem";

    private final BasketItemService basketItemService;

    @GetMapping
    public ResponseEntity<List<BasketItemDto>> listBasketItems() {
        return new ResponseEntity<>(basketItemService.listBasketItems(), HttpStatus.OK);
    }

}
