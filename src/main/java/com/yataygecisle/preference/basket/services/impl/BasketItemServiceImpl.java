package com.yataygecisle.preference.basket.services.impl;

import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.repository.BasketItemRepository;
import com.yataygecisle.preference.basket.services.BasketItemService;
import com.yataygecisle.preference.basket.web.mappers.BasketItemMapper;
import com.yataygecisle.preference.basket.web.models.BasketItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BasketItemServiceImpl implements BasketItemService {

    private final BasketItemRepository basketItemRepository;
    private final BasketItemMapper basketItemMapper;

    @Override
    public List<BasketItemDto> listBasketItems() {
        List<BasketItem> basketItems = basketItemRepository.findAll();
        return basketItemMapper.basketItemDtoToBasketItem(basketItems);
    }
}
