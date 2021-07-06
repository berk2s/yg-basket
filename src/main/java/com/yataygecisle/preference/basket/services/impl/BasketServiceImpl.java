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
import com.yataygecisle.preference.basket.web.models.UpdateBasketDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final BasketMapper basketMapper;
    private final BasketItemMapper basketItemMapper;

    @PreAuthorize("#createBasketDto.getOwnerId() == authentication.principal.getSubject() and hasAuthority('WRITE_BASKET')")
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

    @PreAuthorize("#ownerId.toString() == authentication.principal.getSubject() and hasAuthority('READ_BASKET')")
    @Override
    public List<BasketDto> getUsersBaskets(UUID ownerId) {
        List<Basket> basket = basketRepository.findByOwnerId(ownerId);

        return basketMapper.basketToBasketDto(basket);
    }

    @PreAuthorize("hasAuthority('READ_BASKET')")
    @PostAuthorize("returnObject.getOwnerId() == authentication.principal.getSubject()")
    @Override
    public BasketDto getBasketById(UUID basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> {
                    log.warn("Cannot find basket by given basket id [basketId: {}]", basketId.toString());
                    throw new RuntimeException("err");
                });

        return basketMapper.basketToBasketDto(basket);
    }

    //@PreAuthorize("hasAuthority('UPDATE_BASKET')")
    @Override
    public void updateBasket(UUID basketId, UpdateBasketDto updateBasket) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> {
                    log.warn("Cannot find basket by given basket id [basketId: {}]", basketId.toString());
                    throw new RuntimeException("err");
                });

        basket.setBasketName(updateBasket.getBasketName());

        if (updateBasket.getNewBasketItems().size() > 0) {
            for (AddBasketItemDto addBasketItem : updateBasket.getNewBasketItems()) {
                BasketItem basketItem = basketItemRepository.findById(UUID.fromString(addBasketItem.getBasketItemId()))
                        .orElseThrow(() -> {
                            log.warn("Invalid basket item id [Basket Item Id: {} ]", addBasketItem.getBasketItemId());
                            throw new RuntimeException("Invalid basket item id"); // TODO
                        });

                basket.addBasketItem(basketItem);
            }
        }

        if(updateBasket.getRemovedBasketItems().size() > 0) {
            for (AddBasketItemDto addBasketItem : updateBasket.getRemovedBasketItems()) {
                BasketItem basketItem = basketItemRepository.findById(UUID.fromString(addBasketItem.getBasketItemId()))
                        .orElseThrow(() -> {
                            log.warn("Invalid basket item id [Basket Item Id: {} ]", addBasketItem.getBasketItemId());
                            throw new RuntimeException("Invalid basket item id"); // TODO
                        });

                basket.removeBasketItem(basketItem);
            }
        }

        basketRepository.save(basket);
    }

    @PreAuthorize("hasAuthority('DELETE_BASKET')")
    @Override
    public void deleteBasket(UUID basketId) {
        if(!basketRepository.existsById(basketId)){
            log.warn("Cannot find basket by given id for deleting basket [basketId: {}]",basketId);
            throw new RuntimeException("err");
        }

        basketRepository.deleteById(basketId);
    }

}
