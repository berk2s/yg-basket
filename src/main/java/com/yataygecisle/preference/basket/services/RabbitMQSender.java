package com.yataygecisle.preference.basket.services;

import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.CreatedBasketQueue;

public interface RabbitMQSender {
    void sendCreatedBasket(CreatedBasketQueue basket);
}
