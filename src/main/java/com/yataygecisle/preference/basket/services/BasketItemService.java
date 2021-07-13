package com.yataygecisle.preference.basket.services;

import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.web.models.BasketItemDto;

import java.util.List;

public interface BasketItemService {

    List<BasketItemDto> listBasketItems();

}
