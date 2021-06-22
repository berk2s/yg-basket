package com.yataygecisle.preference.basket.services;

import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;

import java.util.List;
import java.util.UUID;

public interface BasketService {

    BasketDto create(CreateBasketDto createBasketDto);

    List<BasketDto> getUserBaskets(UUID ownerId);

}
