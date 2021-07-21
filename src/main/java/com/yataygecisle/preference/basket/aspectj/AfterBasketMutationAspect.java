package com.yataygecisle.preference.basket.aspectj;

import com.yataygecisle.commons.models.CreatedBasketItemQueue;
import com.yataygecisle.commons.models.CreatedBasketQueue;
import com.yataygecisle.preference.basket.services.RabbitMQSender;
import com.yataygecisle.preference.basket.web.models.BasketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Configuration
public class AfterBasketMutationAspect {

    private final RabbitMQSender rabbitMQSender;

    @AfterReturning(value = "@annotation(com.yataygecisle.preference.basket.annotations.CreatingBasket)", returning = "object")
    public void afterCreatingBasket(JoinPoint joinPoint, Object object) throws Throwable {
        String performedBy = SecurityContextHolder.getContext().getAuthentication().getName();

        BasketDto basket = (BasketDto) object;

        CreatedBasketQueue createdBasketQueue = new CreatedBasketQueue();
        createdBasketQueue.setUserID(basket.getOwnerId());
        createdBasketQueue.setBasketId(basket.getBasketId());
        createdBasketQueue.setBasketName(basket.getBasketName());
        createdBasketQueue.setPerformedBy(performedBy);

        basket.getBasketItems()
                .forEach(basketItem -> {
                    CreatedBasketItemQueue createdBasketItemQueue = new CreatedBasketItemQueue();
                    createdBasketItemQueue.setBasketId(basketItem.getBasketId());
                    createdBasketItemQueue.setBasketItemId(basketItem.getBasketItemId());
                    createdBasketItemQueue.setCollegeId(basketItem.getCollegeId());
                    createdBasketItemQueue.setFacultyId(basketItem.getFacultyId());
                    createdBasketItemQueue.setCourseId(basketItem.getCourseId());
                    createdBasketItemQueue.setCollegeName(basketItem.getCollegeName());
                    createdBasketItemQueue.setFacultyName(basketItem.getFacultyName());
                    createdBasketItemQueue.setCourseName(basketItem.getCourseName());

                    createdBasketQueue.getBasketItems().add(createdBasketItemQueue);
                });

        rabbitMQSender.sendCreatedBasket(createdBasketQueue);

        log.info("Created Basket Info has been sent to broker [basketDto: {}, performedBy: {}]", object, performedBy);
    }

}
