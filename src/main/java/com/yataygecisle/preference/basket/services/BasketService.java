package com.yataygecisle.preference.basket.services;

import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;

public interface BasketService {

    BasketDto create(CreateBasketDto createBasketDto);

}
