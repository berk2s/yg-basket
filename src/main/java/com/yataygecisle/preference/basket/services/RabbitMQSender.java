package com.yataygecisle.preference.basket.services;

import com.yataygecisle.commons.models.CreatedBasketQueue;

public interface RabbitMQSender {
    void sendCreatedBasket(CreatedBasketQueue basket);
}
