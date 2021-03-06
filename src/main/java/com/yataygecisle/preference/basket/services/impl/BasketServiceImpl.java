package com.yataygecisle.preference.basket.services.impl;

import com.yataygecisle.preference.basket.annotations.CreatingBasket;
import com.yataygecisle.preference.basket.domain.Basket;
import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.repository.BasketItemRepository;
import com.yataygecisle.preference.basket.repository.BasketRepository;
import com.yataygecisle.preference.basket.services.BasketService;
import com.yataygecisle.preference.basket.web.exceptions.BasketItemNotFoundException;
import com.yataygecisle.preference.basket.web.exceptions.BasketNotFoundException;
import com.yataygecisle.preference.basket.web.mappers.BasketItemMapper;
import com.yataygecisle.preference.basket.web.mappers.BasketMapper;
import com.yataygecisle.preference.basket.web.models.*;
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

    @CreatingBasket
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
                        throw new BasketItemNotFoundException(ErrorDescription.BASKET_ITEM_NOT_FOUND.getErrorDesc());
                    });

            basket.addBasketItem(basketItem);
        }

        BasketDto basketDto = basketMapper.basketToBasketDto(basketRepository.save(basket));
        basketDto.setBasketItems(basketItemMapper.basketItemDtoToBasketItem(basket.getBasketItems()));

        log.info("Basket has been created successfully [basket: {}]", basketDto);

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
                    throw new BasketNotFoundException(ErrorDescription.BASKET_NOT_FOUND.getErrorDesc());
                });

        return basketMapper.basketToBasketDto(basket);
    }

    @PreAuthorize("hasAuthority('UPDATE_BASKET') and #updateBasket.getOwnerId() == authentication.principal.getSubject() or hasRole('ROLE_ADMIN')")
    @Override
    public void updateBasket(UUID basketId, UpdateBasketDto updateBasket) {
        UUID ownerId = UUID.fromString(updateBasket.getOwnerId());
        Basket basket = basketRepository.findByIdAndOwnerId(basketId, ownerId)
                .orElseThrow(() -> {
                    log.warn("Cannot find basket by given basket id [basketId: {}]", basketId.toString());
                    throw new BasketNotFoundException(ErrorDescription.BASKET_NOT_FOUND.getErrorDesc());
                });

        basket.setBasketName(updateBasket.getBasketName());

        if (updateBasket.getNewBasketItems() != null && updateBasket.getNewBasketItems().size() > 0) {
            for (AddBasketItemDto addBasketItem : updateBasket.getNewBasketItems()) {
                BasketItem basketItem = basketItemRepository.findById(UUID.fromString(addBasketItem.getBasketItemId()))
                        .orElseThrow(() -> {
                            log.warn("Invalid basket item id [Basket Item Id: {} ]", addBasketItem.getBasketItemId());
                            throw new BasketItemNotFoundException(ErrorDescription.BASKET_ITEM_NOT_FOUND.getErrorDesc());
                        });

                basket.addBasketItem(basketItem);
            }
        }


        if(updateBasket.getRemovedBasketItems() != null && updateBasket.getRemovedBasketItems().size() > 0) {
            for (AddBasketItemDto addBasketItem : updateBasket.getRemovedBasketItems()) {
                BasketItem basketItem = basketItemRepository.findById(UUID.fromString(addBasketItem.getBasketItemId()))
                        .orElseThrow(() -> {
                            log.warn("Invalid basket item id [Basket Item Id: {} ]", addBasketItem.getBasketItemId());
                            throw new BasketItemNotFoundException(ErrorDescription.BASKET_ITEM_NOT_FOUND.getErrorDesc());
                        });

                basket.removeBasketItem(basketItem);
            }
        }

        log.info("Basket has been updated successfully [basket: {}]", basket);

        basketRepository.save(basket);
    }

    @PreAuthorize("hasAuthority('DELETE_BASKET') and #deleteBasket.getOwnerId() == authentication.principal.getSubject() or hasRole('ROLE_ADMIN')")
    @Override
    public void deleteBasket(DeleteBasketDto deleteBasket) {
        UUID basketId = UUID.fromString(deleteBasket.getBasketId());
        UUID ownerId = UUID.fromString(deleteBasket.getOwnerId());

        if(!basketRepository.existsByIdAndOwnerId(basketId, ownerId)){
            log.warn("Cannot find basket by given id for deleting basket [basketId: {}]",basketId);
            throw new BasketNotFoundException(ErrorDescription.BASKET_NOT_FOUND.getErrorDesc());
        }

        log.info("Basket has been deleted successfully [basketId: {}, ownerId: {}]", basketId, ownerId);

        basketRepository.deleteById(basketId);
    }

}
