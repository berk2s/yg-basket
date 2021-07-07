package com.yataygecisle.preference.basket.services;

import com.yataygecisle.preference.basket.domain.Basket;
import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.repository.BasketItemRepository;
import com.yataygecisle.preference.basket.repository.BasketRepository;
import com.yataygecisle.preference.basket.services.impl.BasketServiceImpl;
import com.yataygecisle.preference.basket.web.mappers.BasketItemMapper;
import com.yataygecisle.preference.basket.web.mappers.BasketMapper;
import com.yataygecisle.preference.basket.web.mappers.BasketMapperImpl;
import com.yataygecisle.preference.basket.web.models.*;
import org.hibernate.sql.Delete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
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
        basketItem.setFacultyId(UUID.randomUUID());
        basketItem.setCourseId(UUID.randomUUID());
        basketItem.setCollegeName("collegeName");
        basketItem.setFacultyName("departmentName");
        basketItem.setCourseName("courseName");

        BasketItemDto basketItemDto = new BasketItemDto();
        basketItemDto.setBasketId(UUID.randomUUID().toString());
        basketItemDto.setCollegeId(UUID.randomUUID().toString());
        basketItemDto.setFacultyId(UUID.randomUUID().toString());
        basketItemDto.setCourseId(UUID.randomUUID().toString());
        basketItemDto.setCollegeName("collegeName");
        basketItemDto.setFacultyName("collegeName");
        basketItemDto.setCourseName("courseName");

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
        basketItem.setFacultyId(UUID.randomUUID());
        basketItem.setCourseId(UUID.randomUUID());
        basketItem.setCollegeName("collegeName");
        basketItem.setFacultyName("departmentName");
        basketItem.setCourseName("departmentName");

        Basket basket = new Basket();
        basket.setId(UUID.randomUUID());
        basket.setBasketName("basketName");
        basket.setOwnerId(UUID.randomUUID());
        basket.addBasketItem(basketItem);

        when(basketRepository.findByOwnerId(any())).thenReturn(Arrays.asList(basket));

        List<BasketDto> basketDtoList = basketService.getUsersBaskets(basket.getOwnerId());

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


        assertThat(basketDtoList.get(0).getBasketItems().get(0).getFacultyId())
                .isEqualTo(basketItem.getFacultyId().toString());

        assertThat(basketDtoList.get(0).getBasketItems().get(0).getCourseId())
                .isEqualTo(basketItem.getCourseId().toString());

        assertThat(basketDtoList.get(0).getBasketItems().get(0).getCollegeName())
                .isEqualTo(basketItem.getCollegeName());

        assertThat(basketDtoList.get(0).getBasketItems().get(0).getFacultyName())
                .isEqualTo(basketItem.getFacultyName());

        assertThat(basketDtoList.get(0).getBasketItems().get(0).getCourseName())
                .isEqualTo(basketItem.getCourseName());



        verify(basketRepository, times(1)).findByOwnerId(any());
    }

    @DisplayName("Test Get Basket By Basket Id Successfully")
    @Test
    void testGetBasketByBasketIdSuccessfully() {

        BasketItem basketItem = new BasketItem();
        basketItem.setId(UUID.randomUUID());
        basketItem.setCollegeId(UUID.randomUUID());
        basketItem.setFacultyId(UUID.randomUUID());
        basketItem.setCourseId(UUID.randomUUID());
        basketItem.setCollegeName("collegeName");
        basketItem.setFacultyName("departmentName");
        basketItem.setCollegeName("courseName");

        Basket basket = new Basket();
        basket.setId(UUID.randomUUID());
        basket.setBasketName("basketName");
        basket.setOwnerId(UUID.randomUUID());
        basket.addBasketItem(basketItem);

        when(basketRepository.findById(any())).thenReturn(Optional.of(basket));

        BasketDto basketDto = basketService.getBasketById(basket.getOwnerId());


        assertThat(basketDto.getBasketId())
                .isEqualTo(basket.getId().toString());


        assertThat(basketDto.getBasketName())
                .isEqualTo(basket.getBasketName());


        assertThat(basketDto.getOwnerId())
                .isEqualTo(basket.getOwnerId().toString());


        assertThat(basketDto.getBasketItems().size())
                .isEqualTo(1);

        assertThat(basketDto.getBasketItems().get(0).getBasketItemId())
                .isEqualTo(basketItem.getId().toString());


        assertThat(basketDto.getBasketItems().get(0).getCollegeId())
                .isEqualTo(basketItem.getCollegeId().toString());

        assertThat(basketDto.getBasketItems().get(0).getFacultyId())
                .isEqualTo(basketItem.getFacultyId().toString());

        assertThat(basketDto.getBasketItems().get(0).getCourseId())
                .isEqualTo(basketItem.getCourseId().toString());


        assertThat(basketDto.getBasketItems().get(0).getCollegeName())
                .isEqualTo(basketItem.getCollegeName());

        assertThat(basketDto.getBasketItems().get(0).getFacultyName())
                .isEqualTo(basketItem.getFacultyName());

        assertThat(basketDto.getBasketItems().get(0).getCourseName())
                .isEqualTo(basketItem.getCourseName());

        verify(basketRepository, times(1)).findById(any());
    }

    @DisplayName("Test Delete Basket By Basket Id Successfully")
    @Test
    void testDeleteBasketByBasketIdSuccessfully() {
        when(basketRepository.existsByIdAndOwnerId(any(), any())).thenReturn(true);

        DeleteBasketDto deleteBasketDto = DeleteBasketDto.builder()
                .basketId(UUID.randomUUID().toString())
                .ownerId(UUID.randomUUID().toString())
                .build();

        basketService.deleteBasket(deleteBasketDto);

        verify(basketRepository, times(1)).deleteById(any());
        verify(basketRepository, times(1)).existsByIdAndOwnerId(any(), any());
    }

    @DisplayName("Update Basket Successfully")
    @Test
    void testUpdateBasketSuccessfully() {
        BasketItem basketItem = new BasketItem();
        basketItem.setId(UUID.randomUUID());

        Basket basket = new Basket();
        basket.setId(UUID.randomUUID());
        basket.setBasketName("basketName");
        basket.setOwnerId(UUID.randomUUID());
        basket.addBasketItem(basketItem);

        when(basketRepository.findByIdAndOwnerId(any(), any())).thenReturn(Optional.of(basket));
        when(basketItemRepository.findById(any())).thenReturn(Optional.of(basketItem));

        UpdateBasketDto updateBasket = new UpdateBasketDto();
        updateBasket.setBasketName("newName");
        updateBasket.setOwnerId(UUID.randomUUID().toString());
        updateBasket.setRemovedBasketItems(Set.of(
                AddBasketItemDto.builder()
                        .basketItemId(basketItem.getId().toString())
                        .build()
        ));

        basketService.updateBasket(basket.getId(), updateBasket);

        verify(basketRepository, times(1)).save(any());
        verify(basketRepository, times(1)).findByIdAndOwnerId(any(), any());
        verify(basketItemRepository, times(1)).findById(any());
    }
}