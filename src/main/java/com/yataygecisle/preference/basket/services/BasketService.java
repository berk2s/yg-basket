package com.yataygecisle.preference.basket.services;

import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import com.yataygecisle.preference.basket.web.models.DeleteBasketDto;
import com.yataygecisle.preference.basket.web.models.UpdateBasketDto;

import java.util.List;
import java.util.UUID;

public interface BasketService {

    BasketDto create(CreateBasketDto createBasketDto);

    List<BasketDto> getUsersBaskets(UUID ownerId);

    BasketDto getBasketById(UUID basketId);

    void updateBasket(UUID basketId, UpdateBasketDto updateBasket);

    void deleteBasket(DeleteBasketDto deleteBasketDto);

}
