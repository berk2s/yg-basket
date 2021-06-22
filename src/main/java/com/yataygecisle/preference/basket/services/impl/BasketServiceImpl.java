package com.yataygecisle.preference.basket.services.impl;

import com.yataygecisle.preference.basket.domain.Basket;
import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.repository.BasketItemRepository;
import com.yataygecisle.preference.basket.repository.BasketRepository;
import com.yataygecisle.preference.basket.services.BasketService;
import com.yataygecisle.preference.basket.web.mappers.BasketItemMapper;
import com.yataygecisle.preference.basket.web.mappers.BasketMapper;
import com.yataygecisle.preference.basket.web.models.AddBasketItemDto;
import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final BasketMapper basketMapper;
    private final BasketItemMapper basketItemMapper;

    @Override
    public BasketDto create(CreateBasketDto createBasketDto) {
        Basket basket = new Basket();
        basket.setBasketName(createBasketDto.getBasketName());
        basket.setOwnerId(UUID.fromString(createBasketDto.getOwnerId()));

        for (AddBasketItemDto addBasketItem : createBasketDto.getBasketItems()) {
            BasketItem basketItem = basketItemRepository.findById(UUID.fromString(addBasketItem.getBasketItemId()))
                    .orElseThrow(() -> {
                        log.warn("Invalid basket item id [Basket Item Id: {} ]", addBasketItem.getBasketItemId());
                        throw new RuntimeException("Invalid basket item id"); // TODO
                    });

            basket.addBasketItem(basketItem);
        }

        BasketDto basketDto = basketMapper.basketToBasketDto(basketRepository.save(basket));
        basketDto.setBasketItems(basketItemMapper.basketItemDtoToBasketItem(basket.getBasketItems()));

        return basketDto;
    }

}