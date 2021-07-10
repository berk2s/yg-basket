package com.yataygecisle.preference.basket.services.impl;

import com.yataygecisle.commons.models.Exchanges;
import com.yataygecisle.commons.models.Queues;
import com.yataygecisle.commons.models.RoutingKeys;
import com.yataygecisle.preference.basket.domain.Basket;
import com.yataygecisle.preference.basket.services.RabbitMQSender;
import com.yataygecisle.preference.basket.web.models.BasketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMQSenderImpl implements RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    public void sendCreatedBasket(BasketDto basket) {
        rabbitTemplate.convertAndSend(Exchanges.CREATED_BASKET, RoutingKeys.CREATED_BASKET, basket);
        log.info("Created Basket has been sent to queue");
    }

}
