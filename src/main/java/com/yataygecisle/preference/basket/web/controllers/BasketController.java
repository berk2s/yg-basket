package com.yataygecisle.preference.basket.web.controllers;

import com.yataygecisle.preference.basket.services.BasketService;
import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import com.yataygecisle.preference.basket.web.models.DeleteBasketDto;
import com.yataygecisle.preference.basket.web.models.UpdateBasketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(BasketController.ENDPOINT)
@RestController
public class BasketController {

    public static final String ENDPOINT = "/basket";

    private final BasketService basketService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketDto> createBasket(@Valid @RequestBody CreateBasketDto createBasketDto) {
        return new ResponseEntity<>(basketService.create(createBasketDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/owner/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BasketDto>> getUsersBasket(@PathVariable UUID ownerId) {
        return new ResponseEntity<>(basketService.getUsersBaskets(ownerId), HttpStatus.OK);
    }

    @GetMapping(path = "/{basketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketDto> getBasketById(@PathVariable UUID basketId) {
        return new ResponseEntity<>(basketService.getBasketById(basketId), HttpStatus.OK);
    }

    @PutMapping(path = "/{basketId}")
    public ResponseEntity updateBasketById(@PathVariable UUID basketId,
                                           @Valid @RequestBody UpdateBasketDto updateBasket) {
        basketService.updateBasket(basketId, updateBasket);

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, ENDPOINT + "/" + basketId.toString())
                .build();
    }

    @DeleteMapping(path = "/{ownerId}/{basketId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBasketById(@PathVariable UUID ownerId,
                                 @PathVariable UUID basketId) {
        DeleteBasketDto deleteBasket = new DeleteBasketDto();
        deleteBasket.setOwnerId(ownerId.toString());
        deleteBasket.setBasketId(basketId.toString());

        basketService.deleteBasket(deleteBasket);
    }

}
