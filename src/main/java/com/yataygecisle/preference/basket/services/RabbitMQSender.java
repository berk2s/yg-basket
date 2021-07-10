package com.yataygecisle.preference.basket.services;

import com.yataygecisle.preference.basket.web.models.BasketDto;

public interface RabbitMQSender {
    void sendCreatedBasket(BasketDto basket);
}
