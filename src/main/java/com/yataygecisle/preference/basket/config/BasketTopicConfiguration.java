package com.yataygecisle.preference.basket.config;

import com.yataygecisle.commons.models.Exchanges;
import com.yataygecisle.commons.models.Queues;
import com.yataygecisle.commons.models.RoutingKeys;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasketTopicConfiguration {

    @Bean
    Queue createdBasketQueue() {
        return new Queue(Queues.CREATED_BASKET, true);
    }

    @Bean
    TopicExchange basketTopicExchange() {
        return new TopicExchange(Exchanges.CREATED_BASKET);
    }

    @Bean
    Binding createdBasketBinding(Queue createdBasketQueue, TopicExchange basketTopicExchange) {
        return BindingBuilder
                .bind(createdBasketQueue)
                .to(basketTopicExchange)
                .with(RoutingKeys.CREATED_BASKET);
    }

}
