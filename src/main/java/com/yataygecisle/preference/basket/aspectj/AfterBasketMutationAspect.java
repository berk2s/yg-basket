package com.yataygecisle.preference.basket.aspectj;

import com.yataygecisle.preference.basket.services.RabbitMQSender;
import com.yataygecisle.preference.basket.web.models.BasketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Configuration
public class AfterBasketMutationAspect {

    private final RabbitMQSender rabbitMQSender;

    @AfterReturning(value = "@annotation(com.yataygecisle.preference.basket.annotations.CreatingBasket)", returning = "object")
    public void afterCreatingBasket(JoinPoint joinPoint, Object object) throws Throwable {
        rabbitMQSender.sendCreatedBasket((BasketDto) object);
        log.info("Created Basket Info has been sent to broker [basketDto: {}]", object);
    }

}
