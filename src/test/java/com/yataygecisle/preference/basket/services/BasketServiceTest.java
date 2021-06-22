package com.yataygecisle.preference.basket.services;

import com.yataygecisle.preference.basket.domain.Basket;
import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.repository.BasketItemRepository;
import com.yataygecisle.preference.basket.repository.BasketRepository;
import com.yataygecisle.preference.basket.services.impl.BasketServiceImpl;
import com.yataygecisle.preference.basket.web.mappers.BasketItemMapper;
import com.yataygecisle.preference.basket.web.mappers.BasketItemMapperImpl;
import com.yataygecisle.preference.basket.web.mappers.BasketMapper;
import com.yataygecisle.preference.basket.web.mappers.BasketMapperImpl;
import com.yataygecisle.preference.basket.web.models.AddBasketItemDto;
import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.BasketItemDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    BasketRepository basketRepository;

    @Mock
    BasketItemRepository basketItemRepository;

    @Spy
    private final BasketItemMapper basketItemMapper = Mappers.getMapper(BasketItemMapper.class);

    @Spy
    private final BasketMapper basketMapper = new BasketMapperImpl(basketItemMapper);


    @InjectMocks
    BasketServiceImpl basketService;

    Basket basket;

    @BeforeEach
    void setUp() {
        basket = new Basket();
        basket.setId(UUID.randomUUID());
        basket.setBasketName("basketName");
        basket.setOwnerId(UUID.randomUUID());
    }

    @DisplayName("Test Create Basket Successfully")
    @Test
    void testCreateBasketSuccessfully() {

        AddBasketItemDto addBasketItem1 = AddBasketItemDto.builder()
                .basketItemId(UUID.randomUUID().toString())
                .build();


        CreateBasketDto createBasketDto = CreateBasketDto.builder()
                .basketName("basketName")
                .ownerId(UUID.randomUUID().toString())
                .basketItems(Set.of(addBasketItem1))
                .build();

        BasketItem basketItem = new BasketItem();
        basketItem.setId(UUID.randomUUID());
        basketItem.setCollegeId(UUID.randomUUID());
        basketItem.setDepartmentId(UUID.randomUUID());
        basketItem.setCollegeName("collegeName");
        basketItem.setDepartmentName("departmentName");

        BasketItemDto basketItemDto = new BasketItemDto();
        basketItemDto.setBasketId(UUID.randomUUID().toString());
        basketItemDto.setCollegeId(UUID.randomUUID().toString());
        basketItemDto.setDepartmentId(UUID.randomUUID().toString());
        basketItemDto.setCollegeName("collegeName");
        basketItemDto.setDepartmentName("collegeName");

        basket.addBasketItem(basketItem);

        when(basketItemRepository.findById(any())).thenReturn(Optional.of(basketItem));

        when(basketRepository.save(any())).thenReturn(basket);

        BasketDto basketDto = basketService.create(createBasketDto);

        assertThat(basketDto.getBasketId())
                .isEqualTo(basket.getId().toString());

        assertThat(basketDto.getBasketName())
                .isEqualTo(createBasketDto.getBasketName());

        assertThat(basketDto.getBasketItems().size())
                .isEqualTo(1);

        verify(basketRepository, times(1)).save(any());
        verify(basketItemRepository, times(1)).findById(any());
    }

    @DisplayName("Test Get User's Baskets Successfully")
    @Test
    void testGetUsersBasketsSuccessfully() {

        BasketItem basketItem = new BasketItem();
        basketItem.setId(UUID.randomUUID());
        basketItem.setCollegeId(UUID.randomUUID());
        basketItem.setDepartmentId(UUID.randomUUID());
        basketItem.setCollegeName("collegeName");
        basketItem.setDepartmentName("departmentName");

        Basket basket = new Basket();
        basket.setId(UUID.randomUUID());
        basket.setBasketName("basketName");
        basket.setOwnerId(UUID.randomUUID());
        basket.addBasketItem(basketItem);

        when(basketRepository.findByOwnerId(any())).thenReturn(Arrays.asList(basket));

        List<BasketDto> basketDtoList = basketService.getUserBaskets(basket.getOwnerId());

        assertThat(basketDtoList.size())
                .isEqualTo(1);

        assertThat(basketDtoList.get(0).getBasketId())
                .isEqualTo(basket.getId().toString());


        assertThat(basketDtoList.get(0).getBasketName())
                .isEqualTo(basket.getBasketName());


        assertThat(basketDtoList.get(0).getOwnerId())
                .isEqualTo(basket.getOwnerId().toString());


        assertThat(basketDtoList.get(0).getBasketItems().size())
                .isEqualTo(1);

        assertThat(basketDtoList.get(0).getBasketItems().get(0).getBasketItemId())
                .isEqualTo(basketItem.getId().toString());


        assertThat(basketDtoList.get(0).getBasketItems().get(0).getCollegeId())
                .isEqualTo(basketItem.getCollegeId().toString());


        assertThat(basketDtoList.get(0).getBasketItems().get(0).getDepartmentId())
                .isEqualTo(basketItem.getDepartmentId().toString());


        assertThat(basketDtoList.get(0).getBasketItems().get(0).getCollegeName())
                .isEqualTo(basketItem.getCollegeName());

        assertThat(basketDtoList.get(0).getBasketItems().get(0).getDepartmentName())
                .isEqualTo(basketItem.getDepartmentName());

        verify(basketRepository, times(1)).findByOwnerId(any());
    }
}