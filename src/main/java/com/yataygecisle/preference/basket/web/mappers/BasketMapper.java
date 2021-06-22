package com.yataygecisle.preference.basket.web.mappers;

import com.yataygecisle.preference.basket.domain.Basket;
import com.yataygecisle.preference.basket.web.models.BasketDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, uses = {BasketItemMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BasketMapper {

    @Mappings({
            @Mapping(target = "ownerId", expression = "java( UUID.fromString(createBasketDto.getOwnerId()) )")
    })
    Basket createBasketDtoToBasket(CreateBasketDto createBasketDto);

    @Mappings({
            @Mapping(target = "basketId", expression = "java( basket.getId().toString() )"),
            @Mapping(target = "ownerId", expression = "java( basket.getOwnerId().toString() )"),
            @Mapping(target = "createdAt", expression = "java( basket.getCreatedAt() )"),
            @Mapping(target = "lastModifiedAt", expression = "java( basket.getLastModifiedAt() )"),
            @Mapping(source = "basketName", target = "basketName"),
    })
    BasketDto basketToBasketDto(Basket basket);

    @Mappings({
            @Mapping(target = "basketId", expression = "java( basket.getId().toString() )"),
            @Mapping(target = "ownerId", expression = "java( basket.getOwnerId().toString() )"),
            @Mapping(target = "createdAt", expression = "java( basket.getCreatedAt() )"),
            @Mapping(target = "lastModifiedAt", expression = "java( basket.getLastModifiedAt() )"),
            @Mapping(source = "basketName", target = "basketName"),
    })
    List<BasketDto> basketToBasketDto(List<Basket> basket);


}
